package course.courseservice.application.usecase.course;

import course.courseservice.application.dto.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record RemoveCourseLessonUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<Void> execute(UUID courseId, UUID moduleId, UUID lessonId) {
        CourseUseCaseHandler.removeLesson(courseId, moduleId, lessonId);
        return ApiResponse.success("Course lesson removed successfully", null);
    }
}
