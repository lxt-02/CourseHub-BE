package course.courseservice.application.usecase.course;

import course.courseservice.application.command.course.AddLessonCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record AddCourseLessonUseCase(CourseUseCaseHandler CourseUseCaseHandler) {

    public ApiResponse<CourseResponse> execute(UUID courseId, UUID moduleId, AddLessonCommand command) {
        return ApiResponse.success("Course lesson added successfully", CourseUseCaseHandler.addLesson(courseId, moduleId, command));
    }
}
