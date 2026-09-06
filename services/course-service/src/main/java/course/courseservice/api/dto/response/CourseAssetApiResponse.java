package course.courseservice.api.dto.response;

import course.courseservice.application.dto.course.CourseAssetResponse;
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
public class CourseAssetApiResponse {
    private UUID id;
    private UUID courseId;
    private CourseAssetType assetType;
    private String assetUrl;
    private String fileName;
    private Long fileSize;
    private Instant createdAt;

    public static CourseAssetApiResponse from(CourseAssetResponse response) {
        return new CourseAssetApiResponse(
                response.getId(),
                response.getCourseId(),
                response.getAssetType(),
                response.getAssetUrl(),
                response.getFileName(),
                response.getFileSize(),
                response.getCreatedAt()
        );
    }
}
