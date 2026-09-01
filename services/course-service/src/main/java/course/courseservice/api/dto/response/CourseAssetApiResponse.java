package course.courseservice.api.dto.response;

import course.courseservice.application.dto.course.CourseAssetResponse;
import course.courseservice.domain.model.course.enums.CourseAssetType;

import java.time.Instant;
import java.util.UUID;

public record CourseAssetApiResponse(
        UUID id,
        UUID courseId,
        CourseAssetType assetType,
        String assetUrl,
        String fileName,
        Long fileSize,
        Instant createdAt
) {
    public static CourseAssetApiResponse from(CourseAssetResponse response) {
        return new CourseAssetApiResponse(
                response.id(),
                response.courseId(),
                response.assetType(),
                response.assetUrl(),
                response.fileName(),
                response.fileSize(),
                response.createdAt()
        );
    }
}
