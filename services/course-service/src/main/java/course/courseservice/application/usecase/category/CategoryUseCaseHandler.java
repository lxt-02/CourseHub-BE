package course.courseservice.application.usecase.category;

import course.courseservice.application.command.category.CreateCategoryCommand;
import course.courseservice.application.command.category.UpdateCategoryCommand;
import course.courseservice.application.dto.category.CategoryResponse;
import course.courseservice.application.exception.ApplicationConflictException;
import course.courseservice.application.exception.ApplicationNotFoundException;
import course.courseservice.domain.model.category.aggregate.Category;
import course.courseservice.domain.model.course.valueobject.Slug;
import course.courseservice.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryUseCaseHandler {

    private final CategoryRepository categoryRepository;

    public CategoryUseCaseHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryResponse create(CreateCategoryCommand command) {
        Slug slug = Slug.fromTitle(command.name());
        ensureSlugAvailable(slug, null);

        Category category = Category.create(command.name(), command.description());
        return CategoryResponse.from(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public CategoryResponse getById(UUID id) {
        return CategoryResponse.from(findCategory(id));
    }

    @Transactional(readOnly = true)
    public CategoryResponse getBySlug(String slug) {
        return categoryRepository.findBySlug(new Slug(slug))
                .map(CategoryResponse::from)
                .orElseThrow(() -> new ApplicationNotFoundException("Category not found by slug: " + slug));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .toList();
    }

    @Transactional
    public CategoryResponse update(UUID id, UpdateCategoryCommand command) {
        Category category = findCategory(id);
        Slug slug = command.slug() == null || command.slug().isBlank()
                ? Slug.fromTitle(command.name())
                : new Slug(command.slug());
        ensureSlugAvailable(slug, id);

        category.update(command.name(), slug, command.description());
        return CategoryResponse.from(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponse activate(UUID id) {
        Category category = findCategory(id);
        category.activate();
        return CategoryResponse.from(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponse deactivate(UUID id) {
        Category category = findCategory(id);
        category.deactivate();
        return CategoryResponse.from(categoryRepository.save(category));
    }

    @Transactional
    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }

    private Category findCategory(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Category not found: " + id));
    }

    private void ensureSlugAvailable(Slug slug, UUID currentCategoryId) {
        categoryRepository.findBySlug(slug)
                .filter(existing -> !existing.getId().equals(currentCategoryId))
                .ifPresent(existing -> {
                    throw new ApplicationConflictException("Category slug already exists: " + slug.value());
                });
    }
}
