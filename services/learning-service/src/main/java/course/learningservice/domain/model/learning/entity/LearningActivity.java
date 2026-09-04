package course.learningservice.domain.model.learning.entity;

import course.learningservice.domain.model.learning.exception.LearningDomainException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class LearningActivity {

    private final UUID id;
    private final UUID learnerId;
    private final UUID courseId;
    private final UUID lessonId;
    private final String activityType;
    private final String metadata;
    private final Instant occurredAt;
    private final Instant createdAt;

    private LearningActivity(UUID id, UUID learnerId, UUID courseId, UUID lessonId, String activityType,
                             String metadata, Instant occurredAt, Instant createdAt) {
        this.id = id;
        this.learnerId = Objects.requireNonNull(learnerId, "Learner id must not be null");
        this.courseId = Objects.requireNonNull(courseId, "Course id must not be null");
        this.lessonId = lessonId;
        this.activityType = validateActivityType(activityType);
        this.metadata = metadata;
        this.occurredAt = occurredAt == null ? Instant.now() : occurredAt;
        this.createdAt = createdAt;
    }

    public static LearningActivity record(UUID learnerId, UUID courseId, UUID lessonId, String activityType,
                                          String metadata, Instant occurredAt) {
        return new LearningActivity(null, learnerId, courseId, lessonId, activityType, metadata, occurredAt, null);
    }

    public static LearningActivity restore(UUID id, UUID learnerId, UUID courseId, UUID lessonId,
                                           String activityType, String metadata, Instant occurredAt, Instant createdAt) {
        return new LearningActivity(id, learnerId, courseId, lessonId, activityType, metadata, occurredAt, createdAt);
    }

    private static String validateActivityType(String activityType) {
        if (activityType == null || activityType.isBlank()) {
            throw new LearningDomainException("Activity type must not be blank");
        }
        String normalized = activityType.trim();
        if (normalized.length() > 50) {
            throw new LearningDomainException("Activity type must not exceed 50 characters");
        }
        return normalized;
    }

    public UUID getId() { return id; }
    public UUID getLearnerId() { return learnerId; }
    public UUID getCourseId() { return courseId; }
    public UUID getLessonId() { return lessonId; }
    public String getActivityType() { return activityType; }
    public String getMetadata() { return metadata; }
    public Instant getOccurredAt() { return occurredAt; }
    public Instant getCreatedAt() { return createdAt; }
}
