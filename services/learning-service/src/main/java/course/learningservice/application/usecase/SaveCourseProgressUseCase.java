package course.learningservice.application.usecase;

import course.learningservice.application.command.SaveCourseProgressCommand;
import course.learningservice.application.dto.CourseProgressResponse;
import course.learningservice.application.port.LearningPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public record SaveCourseProgressUseCase(LearningPersistencePort persistencePort) {

    @Transactional
    public CourseProgressResponse execute(SaveCourseProgressCommand command) {
        return persistencePort.saveCourseProgress(command);
    }
}
