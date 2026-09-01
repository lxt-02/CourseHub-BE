package course.courseservice.domain.repository;

import course.courseservice.domain.model.category.aggregate.Category;
import course.courseservice.domain.model.course.valueobject.Slug;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {

    Optional<Category> findById(UUID id);

    Optional<Category> findBySlug(Slug slug);

    Optional<Category> findByName(String name);

    List<Category> findAll();

    boolean existsBySlug(Slug slug);

    Category save(Category category);

    void deleteById(UUID id);
}
