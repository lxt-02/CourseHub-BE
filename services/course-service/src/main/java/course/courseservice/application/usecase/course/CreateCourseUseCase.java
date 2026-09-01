package course.courseservice.application.usecase.course;

import course.courseservice.application.command.course.CreateCourseCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import course.courseservice.application.service.CourseApplicationService;
import org.springframework.stereotype.Service;

@Service
public record CreateCourseUseCase(CourseApplicationService courseApplicationService) {

    public ApiResponse<CourseResponse> execute(CreateCourseCommand command) {
        return ApiResponse.success("Course created successfully", courseApplicationService.create(command));
    }
}
