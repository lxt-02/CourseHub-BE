package course.learningservice.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CourseProgressResponse(
        UUID id,
        UUID learnerId,
        UUID courseId,
        int totalLessons,
        int completedLessons,
        BigDecimal progressPercent,
        String status,
        Instant startedAt,
        Instant completedAt,
        Instant createdAt,
        Instant updatedAt
) {
}
