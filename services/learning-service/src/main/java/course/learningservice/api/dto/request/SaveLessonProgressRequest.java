package course.learningservice.api.dto.request;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record SaveLessonProgressRequest(
        UUID id,
        UUID learnerId,
        UUID courseId,
        UUID lessonId,
        int watchedSeconds,
        BigDecimal progressPercent,
        String status,
        Instant startedAt,
        Instant lastAccessedAt,
        Instant completedAt
) {
}
