package course.courseservice.application.dto.course;

import course.courseservice.domain.model.course.entity.CourseAsset;
import course.courseservice.domain.model.course.enums.CourseAssetType;

import java.time.Instant;
import java.util.UUID;

public record CourseAssetResponse(
        UUID id,
        UUID courseId,
        CourseAssetType assetType,
        String assetUrl,
        String fileName,
        Long fileSize,
        Instant createdAt
) {
    public static CourseAssetResponse from(CourseAsset asset) {
        return new CourseAssetResponse(
                asset.getId(),
                asset.getCourseId(),
                asset.getAssetType(),
                asset.getAssetUrl(),
                asset.getFileName(),
                asset.getFileSize(),
                asset.getCreatedAt()
        );
    }
}
