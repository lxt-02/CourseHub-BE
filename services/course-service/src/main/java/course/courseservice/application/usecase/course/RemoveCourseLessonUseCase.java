package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.service.CourseApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record RemoveCourseLessonUseCase(CourseApplicationService courseApplicationService) {

    public ApiResponse<Void> execute(UUID courseId, UUID moduleId, UUID lessonId) {
        courseApplicationService.removeLesson(courseId, moduleId, lessonId);
        return ApiResponse.success("Course lesson removed successfully", null);
    }
}
