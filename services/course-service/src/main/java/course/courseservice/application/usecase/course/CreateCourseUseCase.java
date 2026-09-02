package course.courseservice.application.usecase.course;

import course.courseservice.application.command.course.CreateCourseCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import org.springframework.stereotype.Service;

@Service
public record CreateCourseUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<CourseResponse> execute(CreateCourseCommand command) {
        return ApiResponse.success("Course created successfully", CourseUseCaseHandler.create(command));
    }
}
