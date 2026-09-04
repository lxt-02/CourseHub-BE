package course.learningservice.api.dto.response;

import course.learningservice.application.dto.CertificateResponse;

import java.time.Instant;
import java.util.UUID;

public record CertificateApiResponse(
        UUID id,
        UUID learnerId,
        UUID courseId,
        String certificateCode,
        String certificateUrl,
        Instant issuedAt,
        Instant createdAt
) {
    public static CertificateApiResponse from(CertificateResponse response) {
        return new CertificateApiResponse(
                response.id(),
                response.learnerId(),
                response.courseId(),
                response.certificateCode(),
                response.certificateUrl(),
                response.issuedAt(),
                response.createdAt()
        );
    }
}
