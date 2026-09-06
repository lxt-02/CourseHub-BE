package course.courseservice.application.dto.course;

import course.courseservice.domain.model.course.entity.CourseAsset;
import course.courseservice.domain.model.course.enums.CourseAssetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseAssetResponse {
    private UUID id;
    private UUID courseId;
    private CourseAssetType assetType;
    private String assetUrl;
    private String fileName;
    private Long fileSize;
    private Instant createdAt;

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
