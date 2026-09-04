package course.learningservice.application.usecase;

import course.learningservice.application.dto.CertificateResponse;
import course.learningservice.application.port.LearningPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public record GetCertificateUseCase(LearningPersistencePort persistencePort) {

    @Transactional(readOnly = true)
    public Optional<CertificateResponse> execute(UUID learnerId, UUID courseId) {
        return persistencePort.findCertificate(learnerId, courseId);
    }
}
