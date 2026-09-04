package course.learningservice.application.usecase;

import course.learningservice.application.dto.LessonProgressResponse;
import course.learningservice.application.port.LearningPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public record ListLessonProgressByCourseUseCase(LearningPersistencePort persistencePort) {

    @Transactional(readOnly = true)
    public List<LessonProgressResponse> execute(UUID learnerId, UUID courseId) {
        return persistencePort.findLessonProgressByCourse(learnerId, courseId);
    }
}
