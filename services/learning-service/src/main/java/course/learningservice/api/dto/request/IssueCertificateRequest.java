package course.learningservice.api.dto.request;

import java.time.Instant;
import java.util.UUID;

public record IssueCertificateRequest(
        UUID learnerId,
        UUID courseId,
        String certificateCode,
        String certificateUrl,
        Instant issuedAt
) {
}
