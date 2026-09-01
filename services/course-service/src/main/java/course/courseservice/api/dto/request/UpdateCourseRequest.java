package course.courseservice.api.dto.request;

import course.courseservice.domain.model.course.enums.CourseDifficultyLevel;

import java.math.BigDecimal;

public record UpdateCourseRequest(
        String title,
        String slug,
        String shortDescription,
        String description,
        String thumbnailUrl,
        BigDecimal price,
        CourseDifficultyLevel difficultyLevel
) {
}
