package course.courseservice.application.usecase.course;

import course.courseservice.application.command.course.UpdateCourseCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record UpdateCourseUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<CourseResponse> execute(UUID id, UpdateCourseCommand command) {
        return ApiResponse.success("Course updated successfully", CourseUseCaseHandler.update(id, command));
    }
}
