package course.learningservice.application.command;

import java.time.Instant;
import java.util.UUID;

public record RecordLearningActivityCommand(
        UUID learnerId,
        UUID courseId,
        UUID lessonId,
        String activityType,
        String metadata,
        Instant occurredAt
) {
}
