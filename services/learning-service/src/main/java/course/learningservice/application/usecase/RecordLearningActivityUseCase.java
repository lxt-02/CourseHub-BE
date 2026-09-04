package course.learningservice.application.usecase;

import course.learningservice.application.command.RecordLearningActivityCommand;
import course.learningservice.application.dto.LearningActivityResponse;
import course.learningservice.application.port.LearningPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public record RecordLearningActivityUseCase(LearningPersistencePort persistencePort) {

    @Transactional
    public LearningActivityResponse execute(RecordLearningActivityCommand command) {
        return persistencePort.recordActivity(command);
    }
}
