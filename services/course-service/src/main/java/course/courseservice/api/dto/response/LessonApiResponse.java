package course.courseservice.api.dto.response;

import course.courseservice.application.dto.course.LessonResponse;
import course.courseservice.domain.model.course.enums.LessonType;

import java.time.Instant;
import java.util.UUID;

public record LessonApiResponse(
        UUID id,
        UUID moduleId,
        String title,
        String description,
        LessonType lessonType,
        String content,
        String videoUrl,
        String documentUrl,
        Integer durationSeconds,
        int position,
        boolean preview,
        boolean required,
        Instant createdAt,
        Instant updatedAt
) {
    public static LessonApiResponse from(LessonResponse response) {
        return new LessonApiResponse(
                response.id(),
                response.moduleId(),
                response.title(),
                response.description(),
                response.lessonType(),
                response.content(),
                response.videoUrl(),
                response.documentUrl(),
                response.durationSeconds(),
                response.position(),
                response.preview(),
                response.required(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
