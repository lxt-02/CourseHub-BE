package course.courseservice.infrastructure.mapper;

import course.courseservice.domain.model.course.aggregate.Course;
import course.courseservice.domain.model.course.entity.CourseAsset;
import course.courseservice.domain.model.course.entity.CourseModule;
import course.courseservice.domain.model.course.entity.Lesson;
import course.courseservice.domain.model.course.valueobject.Money;
import course.courseservice.domain.model.course.valueobject.Slug;
import course.courseservice.infrastructure.persistence.entity.CourseAssetInfraEntity;
import course.courseservice.infrastructure.persistence.entity.CourseCategoryInfraEntity;
import course.courseservice.infrastructure.persistence.entity.CourseInfraEntity;
import course.courseservice.infrastructure.persistence.entity.LessonInfraEntity;
import course.courseservice.infrastructure.persistence.entity.ModuleInfraEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class CoursePersistenceMapper {

    public Course toDomain(CourseInfraEntity courseEntity,
                           List<ModuleInfraEntity> moduleEntities,
                           Map<UUID, List<LessonInfraEntity>> lessonsByModuleId,
                           List<CourseAssetInfraEntity> assetEntities,
                           List<CourseCategoryInfraEntity> categoryEntities) {
        if (courseEntity == null) {
            return null;
        }

        List<CourseModule> modules = safeList(moduleEntities).stream()
                .map(module -> toDomain(module, lessonsByModuleId.getOrDefault(module.getId(), List.of())))
                .toList();
        List<CourseAsset> assets = safeList(assetEntities).stream()
                .map(this::toDomain)
                .toList();
        Set<UUID> categoryIds = new LinkedHashSet<>(safeList(categoryEntities).stream()
                .map(CourseCategoryInfraEntity::getCategoryId)
                .toList());

        return Course.restore(
                courseEntity.getId(),
                courseEntity.getManagerId(),
                courseEntity.getTitle(),
                new Slug(courseEntity.getSlug()),
                courseEntity.getShortDescription(),
                courseEntity.getDescription(),
                courseEntity.getThumbnailUrl(),
                Money.of(courseEntity.getPrice()),
                toDomain(courseEntity.getDifficultyLevel()),
                toDomain(courseEntity.getStatus()),
                courseEntity.getPublishedAt(),
                categoryIds,
                modules,
                assets,
                courseEntity.getCreatedAt(),
                courseEntity.getUpdatedAt()
        );
    }

    public CourseInfraEntity toCourseEntity(Course course) {
        if (course == null) {
            return null;
        }

        return CourseInfraEntity.builder()
                .id(course.getId())
                .managerId(course.getManagerId())
                .title(course.getTitle())
                .slug(course.getSlug().value())
                .shortDescription(course.getShortDescription())
                .description(course.getDescription())
                .thumbnailUrl(course.getThumbnailUrl())
                .price(course.getPrice().amount())
                .difficultyLevel(toEntity(course.getDifficultyLevel()))
                .status(toEntity(course.getStatus()))
                .publishedAt(course.getPublishedAt())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }

    public ModuleInfraEntity toModuleEntity(CourseModule module) {
        if (module == null) {
            return null;
        }

        return ModuleInfraEntity.builder()
                .id(module.getId())
                .courseId(module.getCourseId())
                .title(module.getTitle())
                .description(module.getDescription())
                .position(module.getPosition())
                .createdAt(module.getCreatedAt())
                .updatedAt(module.getUpdatedAt())
                .build();
    }

    public LessonInfraEntity toLessonEntity(Lesson lesson) {
        if (lesson == null) {
            return null;
        }

        return LessonInfraEntity.builder()
                .id(lesson.getId())
                .moduleId(lesson.getModuleId())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .lessonType(toEntity(lesson.getLessonType()))
                .content(lesson.getContent())
                .videoUrl(lesson.getVideoUrl())
                .documentUrl(lesson.getDocumentUrl())
                .durationSeconds(lesson.getDurationSeconds())
                .position(lesson.getPosition())
                .preview(lesson.isPreview())
                .required(lesson.isRequired())
                .createdAt(lesson.getCreatedAt())
                .updatedAt(lesson.getUpdatedAt())
                .build();
    }

    public CourseAssetInfraEntity toAssetEntity(CourseAsset asset) {
        if (asset == null) {
            return null;
        }

        return CourseAssetInfraEntity.builder()
                .id(asset.getId())
                .courseId(asset.getCourseId())
                .assetType(toEntity(asset.getAssetType()))
                .assetUrl(asset.getAssetUrl())
                .fileName(asset.getFileName())
                .fileSize(asset.getFileSize())
                .createdAt(asset.getCreatedAt())
                .build();
    }

    public CourseCategoryInfraEntity toCourseCategoryEntity(UUID courseId, UUID categoryId, Instant createdAt) {
        return CourseCategoryInfraEntity.builder()
                .courseId(courseId)
                .categoryId(categoryId)
                .createdAt(createdAt)
                .build();
    }

    private CourseModule toDomain(ModuleInfraEntity moduleEntity, List<LessonInfraEntity> lessonEntities) {
        return CourseModule.restore(
                moduleEntity.getId(),
                moduleEntity.getCourseId(),
                moduleEntity.getTitle(),
                moduleEntity.getDescription(),
                moduleEntity.getPosition(),
                safeList(lessonEntities).stream().map(this::toDomain).toList(),
                moduleEntity.getCreatedAt(),
                moduleEntity.getUpdatedAt()
        );
    }

    private Lesson toDomain(LessonInfraEntity lessonEntity) {
        return Lesson.restore(
                lessonEntity.getId(),
                lessonEntity.getModuleId(),
                lessonEntity.getTitle(),
                lessonEntity.getDescription(),
                toDomain(lessonEntity.getLessonType()),
                lessonEntity.getContent(),
                lessonEntity.getVideoUrl(),
                lessonEntity.getDocumentUrl(),
                lessonEntity.getDurationSeconds(),
                lessonEntity.getPosition(),
                lessonEntity.isPreview(),
                lessonEntity.isRequired(),
                lessonEntity.getCreatedAt(),
                lessonEntity.getUpdatedAt()
        );
    }

    private CourseAsset toDomain(CourseAssetInfraEntity assetEntity) {
        return CourseAsset.restore(
                assetEntity.getId(),
                assetEntity.getCourseId(),
                toDomain(assetEntity.getAssetType()),
                assetEntity.getAssetUrl(),
                assetEntity.getFileName(),
                assetEntity.getFileSize(),
                assetEntity.getCreatedAt()
        );
    }

    private static <T> List<T> safeList(List<T> values) {
        return values == null ? List.of() : values;
    }

    private static course.courseservice.domain.model.course.enums.CourseStatus toDomain(
            course.courseservice.infrastructure.persistence.entity.enums.CourseStatus status) {
        return status == null ? null : course.courseservice.domain.model.course.enums.CourseStatus.valueOf(status.name());
    }

    private static course.courseservice.infrastructure.persistence.entity.enums.CourseStatus toEntity(
            course.courseservice.domain.model.course.enums.CourseStatus status) {
        return status == null ? null : course.courseservice.infrastructure.persistence.entity.enums.CourseStatus.valueOf(status.name());
    }

    private static course.courseservice.domain.model.course.enums.CourseDifficultyLevel toDomain(
            course.courseservice.infrastructure.persistence.entity.enums.CourseDifficultyLevel difficultyLevel) {
        return difficultyLevel == null ? null : course.courseservice.domain.model.course.enums.CourseDifficultyLevel.valueOf(difficultyLevel.name());
    }

    private static course.courseservice.infrastructure.persistence.entity.enums.CourseDifficultyLevel toEntity(
            course.courseservice.domain.model.course.enums.CourseDifficultyLevel difficultyLevel) {
        return difficultyLevel == null ? null : course.courseservice.infrastructure.persistence.entity.enums.CourseDifficultyLevel.valueOf(difficultyLevel.name());
    }

    private static course.courseservice.domain.model.course.enums.LessonType toDomain(
            course.courseservice.infrastructure.persistence.entity.enums.LessonType lessonType) {
        return lessonType == null ? null : course.courseservice.domain.model.course.enums.LessonType.valueOf(lessonType.name());
    }

    private static course.courseservice.infrastructure.persistence.entity.enums.LessonType toEntity(
            course.courseservice.domain.model.course.enums.LessonType lessonType) {
        return lessonType == null ? null : course.courseservice.infrastructure.persistence.entity.enums.LessonType.valueOf(lessonType.name());
    }

    private static course.courseservice.domain.model.course.enums.CourseAssetType toDomain(
            course.courseservice.infrastructure.persistence.entity.enums.CourseAssetType assetType) {
        return assetType == null ? null : course.courseservice.domain.model.course.enums.CourseAssetType.valueOf(assetType.name());
    }

    private static course.courseservice.infrastructure.persistence.entity.enums.CourseAssetType toEntity(
            course.courseservice.domain.model.course.enums.CourseAssetType assetType) {
        return assetType == null ? null : course.courseservice.infrastructure.persistence.entity.enums.CourseAssetType.valueOf(assetType.name());
    }
}
