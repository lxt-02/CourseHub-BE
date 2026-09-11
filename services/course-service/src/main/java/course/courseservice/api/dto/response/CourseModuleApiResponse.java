package course.courseservice.api.dto.response;

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
}
