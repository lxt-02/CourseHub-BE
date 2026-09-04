package course.learningservice.application.port;

import course.learningservice.application.command.IssueCertificateCommand;
import course.learningservice.application.command.RecordLearningActivityCommand;
import course.learningservice.application.command.SaveCourseProgressCommand;
import course.learningservice.application.command.SaveLessonProgressCommand;
import course.learningservice.application.dto.CertificateResponse;
import course.learningservice.application.dto.CourseProgressResponse;
import course.learningservice.application.dto.LearningActivityResponse;
import course.learningservice.application.dto.LessonProgressResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearningPersistencePort {

    Optional<LessonProgressResponse> findLessonProgress(UUID learnerId, UUID lessonId);

    List<LessonProgressResponse> findLessonProgressByCourse(UUID learnerId, UUID courseId);

    LessonProgressResponse saveLessonProgress(SaveLessonProgressCommand command);

    Optional<CourseProgressResponse> findCourseProgress(UUID learnerId, UUID courseId);

    List<CourseProgressResponse> findCourseProgressByLearner(UUID learnerId);

    CourseProgressResponse saveCourseProgress(SaveCourseProgressCommand command);

    LearningActivityResponse recordActivity(RecordLearningActivityCommand command);

    List<LearningActivityResponse> findActivities(UUID learnerId, UUID courseId);

    Optional<CertificateResponse> findCertificate(UUID learnerId, UUID courseId);

    CertificateResponse issueCertificate(IssueCertificateCommand command);
}
