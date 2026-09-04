package course.learningservice.api.dto.request;

import java.time.Instant;
import java.util.UUID;

public record RecordLearningActivityRequest(
        UUID learnerId,
        UUID courseId,
        UUID lessonId,
        String activityType,
        String metadata,
        Instant occurredAt
) {
}
