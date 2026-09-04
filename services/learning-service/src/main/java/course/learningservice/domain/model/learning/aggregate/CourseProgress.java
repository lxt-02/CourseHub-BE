package course.learningservice.domain.model.learning.aggregate;

import course.learningservice.domain.model.learning.enums.ProgressStatus;
import course.learningservice.domain.model.learning.exception.LearningDomainException;
import course.learningservice.domain.model.learning.valueobject.ProgressPercent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class CourseProgress {

    private final UUID id;
    private final UUID learnerId;
    private final UUID courseId;
    private final int totalLessons;
    private final int completedLessons;
    private final ProgressPercent progressPercent;
    private final ProgressStatus status;
    private final Instant startedAt;
    private final Instant completedAt;
    private final Instant createdAt;
    private final Instant updatedAt;

    private CourseProgress(UUID id, UUID learnerId, UUID courseId, int totalLessons, int completedLessons,
                           ProgressPercent progressPercent, ProgressStatus status, Instant startedAt,
                           Instant completedAt, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.learnerId = Objects.requireNonNull(learnerId, "Learner id must not be null");
        this.courseId = Objects.requireNonNull(courseId, "Course id must not be null");
        if (totalLessons < 0 || completedLessons < 0) {
            throw new LearningDomainException("Lesson counts must not be negative");
        }
        if (completedLessons > totalLessons) {
            throw new LearningDomainException("Completed lessons must not exceed total lessons");
        }
        this.totalLessons = totalLessons;
        this.completedLessons = completedLessons;
        this.progressPercent = progressPercent == null ? ProgressPercent.zero() : progressPercent;
        this.status = status == null ? inferStatus(this.progressPercent) : status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CourseProgress create(UUID learnerId, UUID courseId, int totalLessons, int completedLessons,
                                        ProgressPercent progressPercent, ProgressStatus status,
                                        Instant startedAt, Instant completedAt) {
        return new CourseProgress(null, learnerId, courseId, totalLessons, completedLessons, progressPercent,
                status, startedAt, completedAt, null, null);
    }

    public static CourseProgress restore(UUID id, UUID learnerId, UUID courseId, int totalLessons,
                                         int completedLessons, ProgressPercent progressPercent,
                                         ProgressStatus status, Instant startedAt, Instant completedAt,
                                         Instant createdAt, Instant updatedAt) {
        return new CourseProgress(id, learnerId, courseId, totalLessons, completedLessons, progressPercent,
                status, startedAt, completedAt, createdAt, updatedAt);
    }

    private static ProgressStatus inferStatus(ProgressPercent progressPercent) {
        if (progressPercent.value().compareTo(java.math.BigDecimal.valueOf(100)) == 0) {
            return ProgressStatus.COMPLETED;
        }
        if (progressPercent.value().compareTo(java.math.BigDecimal.ZERO) > 0) {
            return ProgressStatus.IN_PROGRESS;
        }
        return ProgressStatus.NOT_STARTED;
    }

    public UUID getId() { return id; }
    public UUID getLearnerId() { return learnerId; }
    public UUID getCourseId() { return courseId; }
    public int getTotalLessons() { return totalLessons; }
    public int getCompletedLessons() { return completedLessons; }
    public ProgressPercent getProgressPercent() { return progressPercent; }
    public ProgressStatus getStatus() { return status; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getCompletedAt() { return completedAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
