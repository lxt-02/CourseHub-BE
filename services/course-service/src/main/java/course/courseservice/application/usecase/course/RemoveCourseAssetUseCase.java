package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record RemoveCourseAssetUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<Void> execute(UUID courseId, UUID assetId) {
        CourseUseCaseHandler.removeAsset(courseId, assetId);
        return ApiResponse.success("Course asset removed successfully", null);
    }
}
