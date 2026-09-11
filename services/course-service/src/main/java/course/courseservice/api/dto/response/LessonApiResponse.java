package course.courseservice.api.dto.response;

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
public class LessonApiResponse {
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
}
