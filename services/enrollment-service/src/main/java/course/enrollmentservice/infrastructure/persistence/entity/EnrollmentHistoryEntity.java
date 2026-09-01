package course.enrollmentservice.infrastructure.persistence.entity;

import course.enrollmentservice.infrastructure.persistence.entity.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public class EnrollmentHistoryEntity {

    private UUID id;
    private UUID enrollmentId;
    private EnrollmentStatus oldStatus;
    private EnrollmentStatus newStatus;
    private String reason;
    private Instant changedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(UUID enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public EnrollmentStatus getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(EnrollmentStatus oldStatus) {
        this.oldStatus = oldStatus;
    }

    public EnrollmentStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(EnrollmentStatus newStatus) {
        this.newStatus = newStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Instant changedAt) {
        this.changedAt = changedAt;
    }
}
