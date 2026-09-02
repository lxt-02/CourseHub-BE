package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record DeleteCourseUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<Void> execute(UUID id) {
        CourseUseCaseHandler.delete(id);
        return ApiResponse.success("Course deleted successfully", null);
    }
}
