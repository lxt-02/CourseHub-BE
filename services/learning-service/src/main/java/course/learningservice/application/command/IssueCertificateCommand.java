package course.learningservice.application.command;

import java.time.Instant;
import java.util.UUID;

public record IssueCertificateCommand(
        UUID learnerId,
        UUID courseId,
        String certificateCode,
        String certificateUrl,
        Instant issuedAt
) {
}
