package course.courseservice.infrastructure.mapper;

import course.courseservice.domain.model.category.aggregate.Category;
import course.courseservice.domain.model.course.valueobject.Slug;
import course.courseservice.infrastructure.persistence.entity.CategoryInfraEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryPersistenceMapper {

    public Category toDomain(CategoryInfraEntity entity) {
        if (entity == null) {
            return null;
        }

        return Category.restore(
                entity.getId(),
                entity.getName(),
                new Slug(entity.getSlug()),
                entity.getDescription(),
                toDomain(entity.getStatus()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public CategoryInfraEntity toEntity(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryInfraEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug().value())
                .description(category.getDescription())
                .status(toEntity(category.getStatus()))
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    private static course.courseservice.domain.model.category.enums.CategoryStatus toDomain(
            course.courseservice.infrastructure.persistence.entity.enums.CategoryStatus status) {
        return status == null ? null : course.courseservice.domain.model.category.enums.CategoryStatus.valueOf(status.name());
    }

    private static course.courseservice.infrastructure.persistence.entity.enums.CategoryStatus toEntity(
            course.courseservice.domain.model.category.enums.CategoryStatus status) {
        return status == null ? null : course.courseservice.infrastructure.persistence.entity.enums.CategoryStatus.valueOf(status.name());
    }
}
