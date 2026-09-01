package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.service.CourseApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record RemoveCourseModuleUseCase(CourseApplicationService courseApplicationService) {

    public ApiResponse<Void> execute(UUID courseId, UUID moduleId) {
        courseApplicationService.removeModule(courseId, moduleId);
        return ApiResponse.success("Course module removed successfully", null);
    }
}
