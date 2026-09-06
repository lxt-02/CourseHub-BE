package course.courseservice.api.dto.request;

import course.courseservice.domain.model.course.enums.CourseAssetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCourseAssetRequest {
    private CourseAssetType assetType;
    private String assetUrl;
    private String fileName;
    private Long fileSize;
}
