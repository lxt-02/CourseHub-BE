package course.courseservice.application.usecase.course;

import course.courseservice.application.command.course.MoveLessonCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import course.courseservice.application.service.CourseApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record MoveCourseLessonUseCase(CourseApplicationService courseApplicationService) {

    public ApiResponse<CourseResponse> execute(UUID courseId, UUID moduleId, UUID lessonId, MoveLessonCommand command) {
        return ApiResponse.success("Course lesson moved successfully", courseApplicationService.moveLesson(courseId, moduleId, lessonId, command));
    }
}
