package course.learningservice.application.dto;

import java.time.Instant;
import java.util.UUID;

public record LearningActivityResponse(
        UUID id,
        UUID learnerId,
        UUID courseId,
        UUID lessonId,
        String activityType,
        String metadata,
        Instant occurredAt,
        Instant createdAt
) {
}
