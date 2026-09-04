package course.learningservice.application.usecase;

import course.learningservice.application.dto.CourseProgressResponse;
import course.learningservice.application.port.LearningPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public record ListLearnerCourseProgressUseCase(LearningPersistencePort persistencePort) {

    @Transactional(readOnly = true)
    public List<CourseProgressResponse> execute(UUID learnerId) {
        return persistencePort.findCourseProgressByLearner(learnerId);
    }
}
