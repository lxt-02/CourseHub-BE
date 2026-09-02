package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import org.springframework.stereotype.Service;

@Service
public record GetCourseBySlugUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<CourseResponse> execute(String slug) {
        return ApiResponse.success("Course fetched successfully", CourseUseCaseHandler.getBySlug(slug));
    }
}
