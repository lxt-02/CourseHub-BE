package course.courseservice.application.usecase.course;

import course.courseservice.application.command.course.AddModuleCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record AddCourseModuleUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<CourseResponse> execute(UUID id, AddModuleCommand command) {
        return ApiResponse.success("Course module added successfully", CourseUseCaseHandler.addModule(id, command));
    }
}
