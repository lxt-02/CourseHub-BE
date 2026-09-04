package course.learningservice.application.usecase;

import course.learningservice.application.dto.LessonProgressResponse;
import course.learningservice.application.port.LearningPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public record GetLessonProgressUseCase(LearningPersistencePort persistencePort) {

    @Transactional(readOnly = true)
    public Optional<LessonProgressResponse> execute(UUID learnerId, UUID lessonId) {
        return persistencePort.findLessonProgress(learnerId, lessonId);
    }
}
