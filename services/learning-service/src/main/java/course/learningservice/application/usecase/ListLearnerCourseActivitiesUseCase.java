package course.learningservice.application.usecase;

import course.learningservice.application.dto.LearningActivityResponse;
import course.learningservice.application.port.LearningPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public record ListLearnerCourseActivitiesUseCase(LearningPersistencePort persistencePort) {

    @Transactional(readOnly = true)
    public List<LearningActivityResponse> execute(UUID learnerId, UUID courseId) {
        return persistencePort.findActivities(learnerId, courseId);
    }
}
