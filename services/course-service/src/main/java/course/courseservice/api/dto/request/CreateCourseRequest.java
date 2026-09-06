package course.courseservice.api.dto.request;

import course.courseservice.domain.model.course.enums.CourseDifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseRequest {
    private UUID managerId;
    private String title;
    private String shortDescription;
    private String description;
    private BigDecimal price;
    private CourseDifficultyLevel difficultyLevel;
}
