package course.searchservice.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseEventPayload {
    private String eventType; // EVENT_CREATED, EVENT_UPDATED, EVENT_DELETED
    private String id;
    private String managerId;
    private String title;
    private String slug;
    private String shortDescription;
    private String description;
    private String thumbnailUrl;
    private BigDecimal price;
    private String difficultyLevel;
    private String status;
    private List<String> categoryIds;
    private Instant publishedAt;
    private Instant createdAt;
    private Instant updatedAt;
}
