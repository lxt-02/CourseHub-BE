package course.courseservice.application.usecase.course;

import course.courseservice.application.command.course.AssignCourseCategoriesCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record AssignCourseCategoriesUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<CourseResponse> execute(UUID id, AssignCourseCategoriesCommand command) {
        return ApiResponse.success("Course categories assigned successfully", CourseUseCaseHandler.assignCategories(id, command));
    }
}
