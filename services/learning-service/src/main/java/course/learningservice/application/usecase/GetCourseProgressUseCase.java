package course.learningservice.application.usecase;

import course.learningservice.application.dto.CourseProgressResponse;
import course.learningservice.application.port.LearningPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public record GetCourseProgressUseCase(LearningPersistencePort persistencePort) {

    @Transactional(readOnly = true)
    public Optional<CourseProgressResponse> execute(UUID learnerId, UUID courseId) {
        return persistencePort.findCourseProgress(learnerId, courseId);
    }
}
