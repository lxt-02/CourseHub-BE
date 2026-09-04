package course.learningservice.application.command;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record SaveCourseProgressCommand(
        UUID id,
        UUID learnerId,
        UUID courseId,
        int totalLessons,
        int completedLessons,
        BigDecimal progressPercent,
        String status,
        Instant startedAt,
        Instant completedAt
) {
}
