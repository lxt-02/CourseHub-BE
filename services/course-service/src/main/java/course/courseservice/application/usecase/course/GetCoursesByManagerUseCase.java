package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record GetCoursesByManagerUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<List<CourseResponse>> execute(UUID managerId) {
        return ApiResponse.success("Courses fetched successfully", CourseUseCaseHandler.getByManagerId(managerId));
    }
}
