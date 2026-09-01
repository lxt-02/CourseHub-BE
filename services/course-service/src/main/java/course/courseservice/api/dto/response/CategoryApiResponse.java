package course.courseservice.api.dto.response;

import course.courseservice.application.dto.category.CategoryResponse;
import course.courseservice.domain.model.category.enums.CategoryStatus;

import java.time.Instant;
import java.util.UUID;

public record CategoryApiResponse(
        UUID id,
        String name,
        String slug,
        String description,
        CategoryStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static CategoryApiResponse from(CategoryResponse response) {
        return new CategoryApiResponse(
                response.id(),
                response.name(),
                response.slug(),
                response.description(),
                response.status(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
