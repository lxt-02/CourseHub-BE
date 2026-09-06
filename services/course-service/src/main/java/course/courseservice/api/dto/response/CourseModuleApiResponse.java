package course.courseservice.api.dto.response;

import course.courseservice.application.dto.course.CourseModuleResponse;
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
public class CourseModuleApiResponse {
    private UUID id;
    private UUID courseId;
    private String title;
    private String description;
    private int position;
    private List<LessonApiResponse> lessons;
    private Instant createdAt;
    private Instant updatedAt;

    public static CourseModuleApiResponse from(CourseModuleResponse response) {
        return new CourseModuleApiResponse(
                response.getId(),
                response.getCourseId(),
                response.getTitle(),
                response.getDescription(),
                response.getPosition(),
                response.getLessons() != null ? response.getLessons().stream().map(LessonApiResponse::from).toList() : List.of(),
                response.getCreatedAt(),
                response.getUpdatedAt()
        );
    }
}
