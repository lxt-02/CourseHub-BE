package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.service.CourseApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record DeleteCourseUseCase(CourseApplicationService courseApplicationService) {

    public ApiResponse<Void> execute(UUID id) {
        courseApplicationService.delete(id);
        return ApiResponse.success("Course deleted successfully", null);
    }
}
