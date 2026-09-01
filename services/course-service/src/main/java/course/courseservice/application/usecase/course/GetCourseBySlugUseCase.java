package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import course.courseservice.application.service.CourseApplicationService;
import org.springframework.stereotype.Service;

@Service
public record GetCourseBySlugUseCase(CourseApplicationService courseApplicationService) {

    public ApiResponse<CourseResponse> execute(String slug) {
        return ApiResponse.success("Course fetched successfully", courseApplicationService.getBySlug(slug));
    }
}
