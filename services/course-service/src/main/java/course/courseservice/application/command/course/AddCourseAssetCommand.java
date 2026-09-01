package course.courseservice.application.command.course;

import course.courseservice.domain.model.course.enums.CourseAssetType;

public record AddCourseAssetCommand(
        CourseAssetType assetType,
        String assetUrl,
        String fileName,
        Long fileSize
) {
}
