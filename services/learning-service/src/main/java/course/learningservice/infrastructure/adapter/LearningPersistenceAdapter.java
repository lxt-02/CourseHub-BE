package course.learningservice.infrastructure.adapter;

import course.learningservice.application.command.IssueCertificateCommand;
import course.learningservice.application.command.RecordLearningActivityCommand;
import course.learningservice.application.command.SaveCourseProgressCommand;
import course.learningservice.application.command.SaveLessonProgressCommand;
import course.learningservice.application.dto.CertificateResponse;
import course.learningservice.application.dto.CourseProgressResponse;
import course.learningservice.application.dto.LearningActivityResponse;
import course.learningservice.application.dto.LessonProgressResponse;
import course.learningservice.application.port.LearningPersistencePort;
import course.learningservice.domain.model.learning.aggregate.Certificate;
import course.learningservice.domain.model.learning.aggregate.CourseProgress;
import course.learningservice.domain.model.learning.aggregate.LessonProgress;
import course.learningservice.domain.model.learning.entity.LearningActivity;
import course.learningservice.domain.model.learning.enums.ProgressStatus;
import course.learningservice.domain.model.learning.valueobject.ProgressPercent;
import course.learningservice.infrastructure.persistence.entity.CertificateEntity;
import course.learningservice.infrastructure.persistence.entity.CourseProgressEntity;
import course.learningservice.infrastructure.persistence.entity.LearningActivityEntity;
import course.learningservice.infrastructure.persistence.entity.LessonProgressEntity;
import course.learningservice.infrastructure.persistence.repository.CertificateRepository;
import course.learningservice.infrastructure.persistence.repository.CourseProgressRepository;
import course.learningservice.infrastructure.persistence.repository.LearningActivityRepository;
import course.learningservice.infrastructure.persistence.repository.LessonProgressRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class LearningPersistenceAdapter implements LearningPersistencePort {

    private final LessonProgressRepository lessonProgressRepository;
    private final CourseProgressRepository courseProgressRepository;
    private final LearningActivityRepository learningActivityRepository;
    private final CertificateRepository certificateRepository;

    public LearningPersistenceAdapter(LessonProgressRepository lessonProgressRepository,
                                      CourseProgressRepository courseProgressRepository,
                                      LearningActivityRepository learningActivityRepository,
                                      CertificateRepository certificateRepository) {
        this.lessonProgressRepository = lessonProgressRepository;
        this.courseProgressRepository = courseProgressRepository;
        this.learningActivityRepository = learningActivityRepository;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public Optional<LessonProgressResponse> findLessonProgress(UUID learnerId, UUID lessonId) {
        return lessonProgressRepository.findByLearnerIdAndLessonId(learnerId, lessonId)
                .map(this::toDomain)
                .map(this::toResponse);
    }

    @Override
    public List<LessonProgressResponse> findLessonProgressByCourse(UUID learnerId, UUID courseId) {
        return lessonProgressRepository.findByLearnerIdAndCourseId(learnerId, courseId)
                .stream()
                .map(this::toDomain)
                .map(this::toResponse)
                .toList();
    }

    @Override
    public LessonProgressResponse saveLessonProgress(SaveLessonProgressCommand command) {
        LessonProgress lessonProgress = LessonProgress.create(
                command.learnerId(),
                command.courseId(),
                command.lessonId(),
                command.watchedSeconds(),
                new ProgressPercent(command.progressPercent()),
                parseStatus(command.status()),
                command.startedAt(),
                command.lastAccessedAt(),
                command.completedAt()
        );
        LessonProgressEntity entity = toEntity(command.id(), lessonProgress);
        if (command.id() == null) {
            lessonProgressRepository.insert(entity);
        } else {
            lessonProgressRepository.update(entity);
        }
        return lessonProgressRepository.findById(entity.getId())
                .map(this::toDomain)
                .map(this::toResponse)
                .orElseGet(() -> toResponse(lessonProgress));
    }

    @Override
    public Optional<CourseProgressResponse> findCourseProgress(UUID learnerId, UUID courseId) {
        return courseProgressRepository.findByLearnerIdAndCourseId(learnerId, courseId)
                .map(this::toDomain)
                .map(this::toResponse);
    }

    @Override
    public List<CourseProgressResponse> findCourseProgressByLearner(UUID learnerId) {
        return courseProgressRepository.findByLearnerId(learnerId)
                .stream()
                .map(this::toDomain)
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CourseProgressResponse saveCourseProgress(SaveCourseProgressCommand command) {
        CourseProgress courseProgress = CourseProgress.create(
                command.learnerId(),
                command.courseId(),
                command.totalLessons(),
                command.completedLessons(),
                new ProgressPercent(command.progressPercent()),
                parseStatus(command.status()),
                command.startedAt(),
                command.completedAt()
        );
        CourseProgressEntity entity = toEntity(command.id(), courseProgress);
        if (command.id() == null) {
            courseProgressRepository.insert(entity);
        } else {
            courseProgressRepository.update(entity);
        }
        return courseProgressRepository.findById(entity.getId())
                .map(this::toDomain)
                .map(this::toResponse)
                .orElseGet(() -> toResponse(courseProgress));
    }

    @Override
    public LearningActivityResponse recordActivity(RecordLearningActivityCommand command) {
        LearningActivity activity = LearningActivity.record(
                command.learnerId(),
                command.courseId(),
                command.lessonId(),
                command.activityType(),
                command.metadata(),
                command.occurredAt()
        );
        LearningActivityEntity entity = toEntity(activity);

        learningActivityRepository.insert(entity);
        return learningActivityRepository.findById(entity.getId())
                .map(this::toDomain)
                .map(this::toResponse)
                .orElseGet(() -> toResponse(activity));
    }

    @Override
    public List<LearningActivityResponse> findActivities(UUID learnerId, UUID courseId) {
        return learningActivityRepository.findByLearnerIdAndCourseId(learnerId, courseId)
                .stream()
                .map(this::toDomain)
                .map(this::toResponse)
                .toList();
    }

    @Override
    public Optional<CertificateResponse> findCertificate(UUID learnerId, UUID courseId) {
        return certificateRepository.findByLearnerIdAndCourseId(learnerId, courseId)
                .map(this::toDomain)
                .map(this::toResponse);
    }

    @Override
    public CertificateResponse issueCertificate(IssueCertificateCommand command) {
        Certificate certificate = Certificate.issue(
                command.learnerId(),
                command.courseId(),
                command.certificateCode(),
                command.certificateUrl(),
                command.issuedAt()
        );
        CertificateEntity entity = toEntity(certificate);

        certificateRepository.insert(entity);
        return certificateRepository.findById(entity.getId())
                .map(this::toDomain)
                .map(this::toResponse)
                .orElseGet(() -> toResponse(certificate));
    }

    private LessonProgressEntity toEntity(UUID id, LessonProgress lessonProgress) {
        LessonProgressEntity entity = new LessonProgressEntity();
        entity.setId(id);
        entity.setLearnerId(lessonProgress.getLearnerId());
        entity.setCourseId(lessonProgress.getCourseId());
        entity.setLessonId(lessonProgress.getLessonId());
        entity.setWatchedSeconds(lessonProgress.getWatchedSeconds());
        entity.setProgressPercent(lessonProgress.getProgressPercent().value());
        entity.setStatus(toPersistenceStatus(lessonProgress.getStatus()));
        entity.setStartedAt(lessonProgress.getStartedAt());
        entity.setLastAccessedAt(lessonProgress.getLastAccessedAt());
        entity.setCompletedAt(lessonProgress.getCompletedAt());
        return entity;
    }

    private CourseProgressEntity toEntity(UUID id, CourseProgress courseProgress) {
        CourseProgressEntity entity = new CourseProgressEntity();
        entity.setId(id);
        entity.setLearnerId(courseProgress.getLearnerId());
        entity.setCourseId(courseProgress.getCourseId());
        entity.setTotalLessons(courseProgress.getTotalLessons());
        entity.setCompletedLessons(courseProgress.getCompletedLessons());
        entity.setProgressPercent(courseProgress.getProgressPercent().value());
        entity.setStatus(toPersistenceStatus(courseProgress.getStatus()));
        entity.setStartedAt(courseProgress.getStartedAt());
        entity.setCompletedAt(courseProgress.getCompletedAt());
        return entity;
    }

    private LearningActivityEntity toEntity(LearningActivity activity) {
        LearningActivityEntity entity = new LearningActivityEntity();
        entity.setLearnerId(activity.getLearnerId());
        entity.setCourseId(activity.getCourseId());
        entity.setLessonId(activity.getLessonId());
        entity.setActivityType(activity.getActivityType());
        entity.setMetadata(activity.getMetadata());
        entity.setOccurredAt(activity.getOccurredAt());
        return entity;
    }

    private CertificateEntity toEntity(Certificate certificate) {
        CertificateEntity entity = new CertificateEntity();
        entity.setLearnerId(certificate.getLearnerId());
        entity.setCourseId(certificate.getCourseId());
        entity.setCertificateCode(certificate.getCertificateCode());
        entity.setCertificateUrl(certificate.getCertificateUrl());
        entity.setIssuedAt(certificate.getIssuedAt());
        return entity;
    }

    private LessonProgress toDomain(LessonProgressEntity entity) {
        return LessonProgress.restore(
                entity.getId(),
                entity.getLearnerId(),
                entity.getCourseId(),
                entity.getLessonId(),
                entity.getWatchedSeconds(),
                new ProgressPercent(entity.getProgressPercent()),
                parseStatus(entity.getStatus().name()),
                entity.getStartedAt(),
                entity.getLastAccessedAt(),
                entity.getCompletedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    private CourseProgress toDomain(CourseProgressEntity entity) {
        return CourseProgress.restore(
                entity.getId(),
                entity.getLearnerId(),
                entity.getCourseId(),
                entity.getTotalLessons(),
                entity.getCompletedLessons(),
                new ProgressPercent(entity.getProgressPercent()),
                parseStatus(entity.getStatus().name()),
                entity.getStartedAt(),
                entity.getCompletedAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    private LearningActivity toDomain(LearningActivityEntity entity) {
        return LearningActivity.restore(
                entity.getId(),
                entity.getLearnerId(),
                entity.getCourseId(),
                entity.getLessonId(),
                entity.getActivityType(),
                entity.getMetadata(),
                entity.getOccurredAt(),
                entity.getCreatedAt()
        );
    }

    private Certificate toDomain(CertificateEntity entity) {
        return Certificate.restore(
                entity.getId(),
                entity.getLearnerId(),
                entity.getCourseId(),
                entity.getCertificateCode(),
                entity.getCertificateUrl(),
                entity.getIssuedAt(),
                entity.getCreatedAt()
        );
    }

    private LessonProgressResponse toResponse(LessonProgress lessonProgress) {
        return new LessonProgressResponse(
                lessonProgress.getId(),
                lessonProgress.getLearnerId(),
                lessonProgress.getCourseId(),
                lessonProgress.getLessonId(),
                lessonProgress.getWatchedSeconds(),
                lessonProgress.getProgressPercent().value(),
                lessonProgress.getStatus().name(),
                lessonProgress.getStartedAt(),
                lessonProgress.getLastAccessedAt(),
                lessonProgress.getCompletedAt(),
                lessonProgress.getCreatedAt(),
                lessonProgress.getUpdatedAt()
        );
    }

    private CourseProgressResponse toResponse(CourseProgress courseProgress) {
        return new CourseProgressResponse(
                courseProgress.getId(),
                courseProgress.getLearnerId(),
                courseProgress.getCourseId(),
                courseProgress.getTotalLessons(),
                courseProgress.getCompletedLessons(),
                courseProgress.getProgressPercent().value(),
                courseProgress.getStatus().name(),
                courseProgress.getStartedAt(),
                courseProgress.getCompletedAt(),
                courseProgress.getCreatedAt(),
                courseProgress.getUpdatedAt()
        );
    }

    private LearningActivityResponse toResponse(LearningActivity activity) {
        return new LearningActivityResponse(
                activity.getId(),
                activity.getLearnerId(),
                activity.getCourseId(),
                activity.getLessonId(),
                activity.getActivityType(),
                activity.getMetadata(),
                activity.getOccurredAt(),
                activity.getCreatedAt()
        );
    }

    private CertificateResponse toResponse(Certificate certificate) {
        return new CertificateResponse(
                certificate.getId(),
                certificate.getLearnerId(),
                certificate.getCourseId(),
                certificate.getCertificateCode(),
                certificate.getCertificateUrl(),
                certificate.getIssuedAt(),
                certificate.getCreatedAt()
        );
    }

    private ProgressStatus parseStatus(String status) {
        return status == null || status.isBlank() ? null : ProgressStatus.valueOf(status);
    }

    private course.learningservice.infrastructure.persistence.entity.enums.ProgressStatus toPersistenceStatus(ProgressStatus status) {
        return course.learningservice.infrastructure.persistence.entity.enums.ProgressStatus.valueOf(status.name());
    }
}
