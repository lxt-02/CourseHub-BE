package course.courseservice.application.command.course;

import course.courseservice.domain.model.course.enums.CourseDifficultyLevel;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateCourseCommand(
        UUID managerId,
        String title,
        String shortDescription,
        String description,
        BigDecimal price,
        CourseDifficultyLevel difficultyLevel
) {
}
