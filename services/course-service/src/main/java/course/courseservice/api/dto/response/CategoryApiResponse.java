package course.courseservice.api.dto.response;

import course.courseservice.domain.model.category.enums.CategoryStatus;
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
public class CategoryApiResponse {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private CategoryStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
