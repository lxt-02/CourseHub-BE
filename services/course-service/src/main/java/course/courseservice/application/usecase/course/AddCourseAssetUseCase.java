package course.courseservice.application.usecase.course;

import course.courseservice.application.command.course.AddCourseAssetCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record AddCourseAssetUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<CourseResponse> execute(UUID id, AddCourseAssetCommand command) {
        return ApiResponse.success("Course asset added successfully", CourseUseCaseHandler.addAsset(id, command));
    }
}
