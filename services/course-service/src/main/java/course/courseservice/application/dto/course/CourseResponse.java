package course.courseservice.application.dto.course;

import course.courseservice.domain.model.course.aggregate.Course;
import course.courseservice.domain.model.course.enums.CourseDifficultyLevel;
import course.courseservice.domain.model.course.enums.CourseStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record CourseResponse(
        UUID id,
        UUID managerId,
        String title,
        String slug,
        String shortDescription,
        String description,
        String thumbnailUrl,
        BigDecimal price,
        CourseDifficultyLevel difficultyLevel,
        CourseStatus status,
        Instant publishedAt,
        Set<UUID> categoryIds,
        List<CourseModuleResponse> modules,
        List<CourseAssetResponse> assets,
        Instant createdAt,
        Instant updatedAt
) {
    public static CourseResponse from(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getManagerId(),
                course.getTitle(),
                course.getSlug().value(),
                course.getShortDescription(),
                course.getDescription(),
                course.getThumbnailUrl(),
                course.getPrice().amount(),
                course.getDifficultyLevel(),
                course.getStatus(),
                course.getPublishedAt(),
                course.getCategoryIds(),
                course.getModules().stream().map(CourseModuleResponse::from).toList(),
                course.getAssets().stream().map(CourseAssetResponse::from).toList(),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }
}
