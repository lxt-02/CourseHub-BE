package course.learningservice.application.usecase;

import course.learningservice.application.command.SaveLessonProgressCommand;
import course.learningservice.application.dto.LessonProgressResponse;
import course.learningservice.application.port.LearningPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public record SaveLessonProgressUseCase(LearningPersistencePort persistencePort) {

    @Transactional
    public LessonProgressResponse execute(SaveLessonProgressCommand command) {
        return persistencePort.saveLessonProgress(command);
    }
}
