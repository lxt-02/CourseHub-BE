package course.courseservice.application.usecase.course;

import course.courseservice.application.command.course.MoveLessonCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record MoveCourseLessonUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<CourseResponse> execute(UUID courseId, UUID moduleId, UUID lessonId, MoveLessonCommand command) {
        return ApiResponse.success("Course lesson moved successfully", CourseUseCaseHandler.moveLesson(courseId, moduleId, lessonId, command));
    }
}
