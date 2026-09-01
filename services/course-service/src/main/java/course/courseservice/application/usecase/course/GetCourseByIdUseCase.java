package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import course.courseservice.application.service.CourseApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record GetCourseByIdUseCase(CourseApplicationService courseApplicationService) {

    public ApiResponse<CourseResponse> execute(UUID id) {
        return ApiResponse.success("Course fetched successfully", courseApplicationService.getById(id));
    }
}
