package course.courseservice.api.dto.response;

import course.courseservice.application.dto.course.CourseModuleResponse;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CourseModuleApiResponse(
        UUID id,
        UUID courseId,
        String title,
        String description,
        int position,
        List<LessonApiResponse> lessons,
        Instant createdAt,
        Instant updatedAt
) {
    public static CourseModuleApiResponse from(CourseModuleResponse response) {
        return new CourseModuleApiResponse(
                response.id(),
                response.courseId(),
                response.title(),
                response.description(),
                response.position(),
                response.lessons().stream().map(LessonApiResponse::from).toList(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
