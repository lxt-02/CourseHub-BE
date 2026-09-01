package course.courseservice.application.command.course;

import course.courseservice.domain.model.course.enums.CourseDifficultyLevel;

import java.math.BigDecimal;

public record UpdateCourseCommand(
        String title,
        String slug,
        String shortDescription,
        String description,
        String thumbnailUrl,
        BigDecimal price,
        CourseDifficultyLevel difficultyLevel
) {
}
