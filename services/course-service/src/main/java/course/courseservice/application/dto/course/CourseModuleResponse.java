package course.courseservice.application.dto.course;

import course.courseservice.domain.model.course.entity.CourseModule;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CourseModuleResponse(
        UUID id,
        UUID courseId,
        String title,
        String description,
        int position,
        List<LessonResponse> lessons,
        Instant createdAt,
        Instant updatedAt
) {
    public static CourseModuleResponse from(CourseModule module) {
        return new CourseModuleResponse(
                module.getId(),
                module.getCourseId(),
                module.getTitle(),
                module.getDescription(),
                module.getPosition(),
                module.getLessons().stream().map(LessonResponse::from).toList(),
                module.getCreatedAt(),
                module.getUpdatedAt()
        );
    }
}
