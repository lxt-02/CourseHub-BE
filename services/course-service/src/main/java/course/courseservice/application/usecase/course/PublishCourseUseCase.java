package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record PublishCourseUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<CourseResponse> execute(UUID id) {
        return ApiResponse.success("Course published successfully", CourseUseCaseHandler.publish(id));
    }
}
