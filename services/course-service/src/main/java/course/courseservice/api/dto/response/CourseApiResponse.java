package course.courseservice.api.dto.response;

import course.courseservice.application.dto.course.CourseResponse;
import course.courseservice.domain.model.course.enums.CourseDifficultyLevel;
import course.courseservice.domain.model.course.enums.CourseStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record CourseApiResponse(
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
        List<CourseModuleApiResponse> modules,
        List<CourseAssetApiResponse> assets,
        Instant createdAt,
        Instant updatedAt
) {
    public static CourseApiResponse from(CourseResponse response) {
        return new CourseApiResponse(
                response.id(),
                response.managerId(),
                response.title(),
                response.slug(),
                response.shortDescription(),
                response.description(),
                response.thumbnailUrl(),
                response.price(),
                response.difficultyLevel(),
                response.status(),
                response.publishedAt(),
                response.categoryIds(),
                response.modules().stream().map(CourseModuleApiResponse::from).toList(),
                response.assets().stream().map(CourseAssetApiResponse::from).toList(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
