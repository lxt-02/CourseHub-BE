package course.courseservice.api.dto.request;

import course.courseservice.domain.model.course.enums.CourseAssetType;

public record AddCourseAssetRequest(
        CourseAssetType assetType,
        String assetUrl,
        String fileName,
        Long fileSize
) {
}
