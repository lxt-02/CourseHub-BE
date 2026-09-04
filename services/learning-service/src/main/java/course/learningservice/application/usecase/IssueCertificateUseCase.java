package course.learningservice.application.usecase;

import course.learningservice.application.command.IssueCertificateCommand;
import course.learningservice.application.dto.CertificateResponse;
import course.learningservice.application.port.LearningPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public record IssueCertificateUseCase(LearningPersistencePort persistencePort) {

    @Transactional
    public CertificateResponse execute(IssueCertificateCommand command) {
        return persistencePort.issueCertificate(command);
    }
}
