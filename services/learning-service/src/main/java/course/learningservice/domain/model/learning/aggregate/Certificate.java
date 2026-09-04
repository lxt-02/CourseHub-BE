package course.learningservice.domain.model.learning.aggregate;

import course.learningservice.domain.model.learning.exception.LearningDomainException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Certificate {

    private final UUID id;
    private final UUID learnerId;
    private final UUID courseId;
    private final String certificateCode;
    private final String certificateUrl;
    private final Instant issuedAt;
    private final Instant createdAt;

    private Certificate(UUID id, UUID learnerId, UUID courseId, String certificateCode,
                        String certificateUrl, Instant issuedAt, Instant createdAt) {
        this.id = id;
        this.learnerId = Objects.requireNonNull(learnerId, "Learner id must not be null");
        this.courseId = Objects.requireNonNull(courseId, "Course id must not be null");
        this.certificateCode = validateCertificateCode(certificateCode);
        this.certificateUrl = normalizeNullable(certificateUrl);
        this.issuedAt = issuedAt == null ? Instant.now() : issuedAt;
        this.createdAt = createdAt;
    }

    public static Certificate issue(UUID learnerId, UUID courseId, String certificateCode,
                                    String certificateUrl, Instant issuedAt) {
        return new Certificate(null, learnerId, courseId, certificateCode, certificateUrl, issuedAt, null);
    }

    public static Certificate restore(UUID id, UUID learnerId, UUID courseId, String certificateCode,
                                      String certificateUrl, Instant issuedAt, Instant createdAt) {
        return new Certificate(id, learnerId, courseId, certificateCode, certificateUrl, issuedAt, createdAt);
    }

    private static String validateCertificateCode(String certificateCode) {
        if (certificateCode == null || certificateCode.isBlank()) {
            throw new LearningDomainException("Certificate code must not be blank");
        }
        String normalized = certificateCode.trim();
        if (normalized.length() > 100) {
            throw new LearningDomainException("Certificate code must not exceed 100 characters");
        }
        return normalized;
    }

    private static String normalizeNullable(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    public UUID getId() { return id; }
    public UUID getLearnerId() { return learnerId; }
    public UUID getCourseId() { return courseId; }
    public String getCertificateCode() { return certificateCode; }
    public String getCertificateUrl() { return certificateUrl; }
    public Instant getIssuedAt() { return issuedAt; }
    public Instant getCreatedAt() { return createdAt; }
}
