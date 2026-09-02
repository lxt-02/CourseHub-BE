package course.courseservice.infrastructure.persistence.entity;

import course.courseservice.infrastructure.persistence.entity.enums.LessonType;
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
public class LessonInfraEntity {

    private UUID id;
    private UUID moduleId;
    private String title;
    private String description;
    private LessonType lessonType;
    private String content;
    private String videoUrl;
    private String documentUrl;
    private Integer durationSeconds;
    private Integer position;
    private boolean preview;

    @Builder.Default
    private boolean required = true;

    private Instant createdAt;
    private Instant updatedAt;
}
