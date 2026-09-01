package course.courseservice.application.usecase.course;

import course.courseservice.application.command.course.UpdateCourseCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import course.courseservice.application.service.CourseApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record UpdateCourseUseCase(CourseApplicationService courseApplicationService) {

    public ApiResponse<CourseResponse> execute(UUID id, UpdateCourseCommand command) {
        return ApiResponse.success("Course updated successfully", courseApplicationService.update(id, command));
    }
}
