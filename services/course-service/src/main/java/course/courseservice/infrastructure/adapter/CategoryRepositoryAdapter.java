package course.courseservice.infrastructure.adapter;

import course.courseservice.domain.model.category.aggregate.Category;
import course.courseservice.domain.model.course.valueobject.Slug;
import course.courseservice.domain.repository.CategoryRepository;
import course.courseservice.infrastructure.mapper.CategoryPersistenceMapper;
import course.courseservice.infrastructure.persistence.entity.CategoryInfraEntity;
import course.courseservice.infrastructure.persistence.repository.CategoryMybatisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final CategoryMybatisRepository repository;
    private final CategoryPersistenceMapper mapper;

    @Override
    public Optional<Category> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Category> findBySlug(Slug slug) {
        return repository.findBySlug(slug.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return repository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsBySlug(Slug slug) {
        return repository.existsBySlug(slug.value());
    }

    @Override
    public Category save(Category category) {
        CategoryInfraEntity entity = mapper.toEntity(category);
        if (repository.existsById(category.getId())) {
            repository.update(entity);
        } else {
            repository.insert(entity);
        }
        return findById(category.getId()).orElseThrow();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
