package course.learningservice.domain.model.learning.aggregate;

import course.learningservice.domain.model.learning.enums.ProgressStatus;
import course.learningservice.domain.model.learning.exception.LearningDomainException;
import course.learningservice.domain.model.learning.valueobject.ProgressPercent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class LessonProgress {

    private final UUID id;
    private final UUID learnerId;
    private final UUID courseId;
    private final UUID lessonId;
    private final int watchedSeconds;
    private final ProgressPercent progressPercent;
    private final ProgressStatus status;
    private final Instant startedAt;
    private final Instant lastAccessedAt;
    private final Instant completedAt;
    private final Instant createdAt;
    private final Instant updatedAt;

    private LessonProgress(UUID id, UUID learnerId, UUID courseId, UUID lessonId, int watchedSeconds,
                           ProgressPercent progressPercent, ProgressStatus status, Instant startedAt,
                           Instant lastAccessedAt, Instant completedAt, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.learnerId = Objects.requireNonNull(learnerId, "Learner id must not be null");
        this.courseId = Objects.requireNonNull(courseId, "Course id must not be null");
        this.lessonId = Objects.requireNonNull(lessonId, "Lesson id must not be null");
        if (watchedSeconds < 0) {
            throw new LearningDomainException("Watched seconds must not be negative");
        }
        this.watchedSeconds = watchedSeconds;
        this.progressPercent = progressPercent == null ? ProgressPercent.zero() : progressPercent;
        this.status = status == null ? inferStatus(this.progressPercent) : status;
        this.startedAt = startedAt;
        this.lastAccessedAt = lastAccessedAt;
        this.completedAt = completedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static LessonProgress create(UUID learnerId, UUID courseId, UUID lessonId, int watchedSeconds,
                                        ProgressPercent progressPercent, ProgressStatus status,
                                        Instant startedAt, Instant lastAccessedAt, Instant completedAt) {
        return new LessonProgress(null, learnerId, courseId, lessonId, watchedSeconds, progressPercent, status,
                startedAt, lastAccessedAt, completedAt, null, null);
    }

    public static LessonProgress restore(UUID id, UUID learnerId, UUID courseId, UUID lessonId, int watchedSeconds,
                                         ProgressPercent progressPercent, ProgressStatus status, Instant startedAt,
                                         Instant lastAccessedAt, Instant completedAt, Instant createdAt, Instant updatedAt) {
        return new LessonProgress(id, learnerId, courseId, lessonId, watchedSeconds, progressPercent, status,
                startedAt, lastAccessedAt, completedAt, createdAt, updatedAt);
    }

    private static ProgressStatus inferStatus(ProgressPercent progressPercent) {
        if (progressPercent.value().compareTo(BigDecimalConstants.ONE_HUNDRED) == 0) {
            return ProgressStatus.COMPLETED;
        }
        if (progressPercent.value().compareTo(BigDecimalConstants.ZERO) > 0) {
            return ProgressStatus.IN_PROGRESS;
        }
        return ProgressStatus.NOT_STARTED;
    }

    private static final class BigDecimalConstants {
        private static final java.math.BigDecimal ZERO = java.math.BigDecimal.ZERO;
        private static final java.math.BigDecimal ONE_HUNDRED = java.math.BigDecimal.valueOf(100);
    }

    public UUID getId() { return id; }
    public UUID getLearnerId() { return learnerId; }
    public UUID getCourseId() { return courseId; }
    public UUID getLessonId() { return lessonId; }
    public int getWatchedSeconds() { return watchedSeconds; }
    public ProgressPercent getProgressPercent() { return progressPercent; }
    public ProgressStatus getStatus() { return status; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getLastAccessedAt() { return lastAccessedAt; }
    public Instant getCompletedAt() { return completedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
