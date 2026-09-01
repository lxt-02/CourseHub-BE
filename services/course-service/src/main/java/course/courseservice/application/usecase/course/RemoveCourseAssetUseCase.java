package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.service.CourseApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record RemoveCourseAssetUseCase(CourseApplicationService courseApplicationService) {

    public ApiResponse<Void> execute(UUID courseId, UUID assetId) {
        courseApplicationService.removeAsset(courseId, assetId);
        return ApiResponse.success("Course asset removed successfully", null);
    }
}
