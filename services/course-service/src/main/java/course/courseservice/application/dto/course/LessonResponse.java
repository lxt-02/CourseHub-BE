package course.courseservice.application.dto.course;

import course.courseservice.domain.model.course.entity.Lesson;
import course.courseservice.domain.model.course.enums.LessonType;

import java.time.Instant;
import java.util.UUID;

public record LessonResponse(
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
    public static LessonResponse from(Lesson lesson) {
        return new LessonResponse(
                lesson.getId(),
                lesson.getModuleId(),
                lesson.getTitle(),
                lesson.getDescription(),
                lesson.getLessonType(),
                lesson.getContent(),
                lesson.getVideoUrl(),
                lesson.getDocumentUrl(),
                lesson.getDurationSeconds(),
                lesson.getPosition(),
                lesson.isPreview(),
                lesson.isRequired(),
                lesson.getCreatedAt(),
                lesson.getUpdatedAt()
        );
    }
}
