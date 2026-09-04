package course.learningservice.api.dto.response;

import course.learningservice.application.dto.LearningActivityResponse;

import java.time.Instant;
import java.util.UUID;

public record LearningActivityApiResponse(
        UUID id,
        UUID learnerId,
        UUID courseId,
        UUID lessonId,
        String activityType,
        String metadata,
        Instant occurredAt,
        Instant createdAt
) {
    public static LearningActivityApiResponse from(LearningActivityResponse response) {
        return new LearningActivityApiResponse(
                response.id(),
                response.learnerId(),
                response.courseId(),
                response.lessonId(),
                response.activityType(),
                response.metadata(),
                response.occurredAt(),
                response.createdAt()
        );
    }
}
