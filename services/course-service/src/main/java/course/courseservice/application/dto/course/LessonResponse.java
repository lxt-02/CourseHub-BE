package course.courseservice.application.dto.course;

import course.courseservice.domain.model.course.entity.Lesson;
import course.courseservice.domain.model.course.enums.LessonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {
    private UUID id;
    private UUID moduleId;
    private String title;
    private String description;
    private LessonType lessonType;
    private String content;
    private String videoUrl;
    private String documentUrl;
    private Integer durationSeconds;
    private int position;
    private boolean preview;
    private boolean required;
    private Instant createdAt;
    private Instant updatedAt;

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
