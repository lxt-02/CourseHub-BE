package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import course.courseservice.application.service.CourseApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record GetCoursesByManagerUseCase(CourseApplicationService courseApplicationService) {

    public ApiResponse<List<CourseResponse>> execute(UUID managerId) {
        return ApiResponse.success("Courses fetched successfully", courseApplicationService.getByManagerId(managerId));
    }
}
