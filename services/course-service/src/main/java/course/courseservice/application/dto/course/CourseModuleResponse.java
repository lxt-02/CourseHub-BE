package course.courseservice.application.dto.course;

import course.courseservice.domain.model.course.entity.CourseModule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseModuleResponse {
    private UUID id;
    private UUID courseId;
    private String title;
    private String description;
    private int position;
    private List<LessonResponse> lessons;
    private Instant createdAt;
    private Instant updatedAt;

    public static CourseModuleResponse from(CourseModule module) {
        return new CourseModuleResponse(
                module.getId(),
                module.getCourseId(),
                module.getTitle(),
                module.getDescription(),
                module.getPosition(),
                module.getLessons() != null ? module.getLessons().stream().map(LessonResponse::from).toList() : List.of(),
                module.getCreatedAt(),
                module.getUpdatedAt()
        );
    }
}
