package course.learningservice.application.dto;

import java.time.Instant;
import java.util.UUID;

public record CertificateResponse(
        UUID id,
        UUID learnerId,
        UUID courseId,
        String certificateCode,
        String certificateUrl,
        Instant issuedAt,
        Instant createdAt
) {
}
