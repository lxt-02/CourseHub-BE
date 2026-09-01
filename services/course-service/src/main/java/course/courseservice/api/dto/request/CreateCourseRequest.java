package course.courseservice.api.dto.request;

import course.courseservice.domain.model.course.enums.CourseDifficultyLevel;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateCourseRequest(
        UUID managerId,
        String title,
        String shortDescription,
        String description,
        BigDecimal price,
        CourseDifficultyLevel difficultyLevel
) {
}
