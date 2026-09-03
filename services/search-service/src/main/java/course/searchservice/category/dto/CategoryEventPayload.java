package course.searchservice.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEventPayload {
    private String eventType; // EVENT_CREATED, EVENT_UPDATED, EVENT_DELETED
    private String id;
    private String name;
    private String slug;
    private String description;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
