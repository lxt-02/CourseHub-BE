package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record RemoveCourseModuleUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<Void> execute(UUID courseId, UUID moduleId) {
        CourseUseCaseHandler.removeModule(courseId, moduleId);
        return ApiResponse.success("Course module removed successfully", null);
    }
}
