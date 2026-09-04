package course.learningservice.api.controller;

import course.learningservice.api.dto.request.IssueCertificateRequest;
import course.learningservice.api.dto.request.RecordLearningActivityRequest;
import course.learningservice.api.dto.request.SaveCourseProgressRequest;
import course.learningservice.api.dto.request.SaveLessonProgressRequest;
import course.learningservice.api.dto.response.CertificateApiResponse;
import course.learningservice.api.dto.response.CourseProgressApiResponse;
import course.learningservice.api.dto.response.LearningActivityApiResponse;
import course.learningservice.api.dto.response.LessonProgressApiResponse;
import course.learningservice.application.command.IssueCertificateCommand;
import course.learningservice.application.command.RecordLearningActivityCommand;
import course.learningservice.application.command.SaveCourseProgressCommand;
import course.learningservice.application.command.SaveLessonProgressCommand;
import course.learningservice.application.dto.ApiResponse;
import course.learningservice.application.exception.ApplicationNotFoundException;
import course.learningservice.application.usecase.GetCourseProgressUseCase;
import course.learningservice.application.usecase.GetCertificateUseCase;
import course.learningservice.application.usecase.GetLessonProgressUseCase;
import course.learningservice.application.usecase.IssueCertificateUseCase;
import course.learningservice.application.usecase.ListLearnerCourseActivitiesUseCase;
import course.learningservice.application.usecase.ListLearnerCourseProgressUseCase;
import course.learningservice.application.usecase.ListLessonProgressByCourseUseCase;
import course.learningservice.application.usecase.RecordLearningActivityUseCase;
import course.learningservice.application.usecase.SaveCourseProgressUseCase;
import course.learningservice.application.usecase.SaveLessonProgressUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPath.LEARNING)
public class LearningController {

    private final SaveLessonProgressUseCase saveLessonProgressUseCase;
    private final GetLessonProgressUseCase getLessonProgressUseCase;
    private final ListLessonProgressByCourseUseCase listLessonProgressByCourseUseCase;
    private final SaveCourseProgressUseCase saveCourseProgressUseCase;
    private final GetCourseProgressUseCase getCourseProgressUseCase;
    private final ListLearnerCourseProgressUseCase listLearnerCourseProgressUseCase;
    private final RecordLearningActivityUseCase recordLearningActivityUseCase;
    private final ListLearnerCourseActivitiesUseCase listLearnerCourseActivitiesUseCase;
    private final GetCertificateUseCase getCertificateUseCase;
    private final IssueCertificateUseCase issueCertificateUseCase;

    public LearningController(SaveLessonProgressUseCase saveLessonProgressUseCase,
                              GetLessonProgressUseCase getLessonProgressUseCase,
                              ListLessonProgressByCourseUseCase listLessonProgressByCourseUseCase,
                              SaveCourseProgressUseCase saveCourseProgressUseCase,
                              GetCourseProgressUseCase getCourseProgressUseCase,
                              ListLearnerCourseProgressUseCase listLearnerCourseProgressUseCase,
                              RecordLearningActivityUseCase recordLearningActivityUseCase,
                              ListLearnerCourseActivitiesUseCase listLearnerCourseActivitiesUseCase,
                              GetCertificateUseCase getCertificateUseCase,
                              IssueCertificateUseCase issueCertificateUseCase) {
        this.saveLessonProgressUseCase = saveLessonProgressUseCase;
        this.getLessonProgressUseCase = getLessonProgressUseCase;
        this.listLessonProgressByCourseUseCase = listLessonProgressByCourseUseCase;
        this.saveCourseProgressUseCase = saveCourseProgressUseCase;
        this.getCourseProgressUseCase = getCourseProgressUseCase;
        this.listLearnerCourseProgressUseCase = listLearnerCourseProgressUseCase;
        this.recordLearningActivityUseCase = recordLearningActivityUseCase;
        this.listLearnerCourseActivitiesUseCase = listLearnerCourseActivitiesUseCase;
        this.getCertificateUseCase = getCertificateUseCase;
        this.issueCertificateUseCase = issueCertificateUseCase;
    }

    @PostMapping("/lesson-progress")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<LessonProgressApiResponse> saveLessonProgress(@RequestBody SaveLessonProgressRequest request) {
        return ApiResponse.success(
                "Lesson progress saved successfully",
                LessonProgressApiResponse.from(saveLessonProgressUseCase.execute(new SaveLessonProgressCommand(
                        request.id(),
                        request.learnerId(),
                        request.courseId(),
                        request.lessonId(),
                        request.watchedSeconds(),
                        request.progressPercent(),
                        request.status(),
                        request.startedAt(),
                        request.lastAccessedAt(),
                        request.completedAt()
                )))
        );
    }

    @GetMapping("/learners/{learnerId}/lessons/{lessonId}/progress")
    public ApiResponse<LessonProgressApiResponse> getLessonProgress(@PathVariable UUID learnerId,
                                                                    @PathVariable UUID lessonId) {
        return ApiResponse.success(
                "Lesson progress fetched successfully",
                getLessonProgressUseCase.execute(learnerId, lessonId)
                        .map(LessonProgressApiResponse::from)
                        .orElseThrow(() -> new ApplicationNotFoundException("Lesson progress not found"))
        );
    }

    @GetMapping("/learners/{learnerId}/courses/{courseId}/lessons/progress")
    public ApiResponse<List<LessonProgressApiResponse>> listLessonProgressByCourse(@PathVariable UUID learnerId,
                                                                                   @PathVariable UUID courseId) {
        return ApiResponse.success(
                "Lesson progress fetched successfully",
                listLessonProgressByCourseUseCase.execute(learnerId, courseId)
                        .stream()
                        .map(LessonProgressApiResponse::from)
                        .toList()
        );
    }

    @PostMapping("/course-progress")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CourseProgressApiResponse> saveCourseProgress(@RequestBody SaveCourseProgressRequest request) {
        return ApiResponse.success(
                "Course progress saved successfully",
                CourseProgressApiResponse.from(saveCourseProgressUseCase.execute(new SaveCourseProgressCommand(
                        request.id(),
                        request.learnerId(),
                        request.courseId(),
                        request.totalLessons(),
                        request.completedLessons(),
                        request.progressPercent(),
                        request.status(),
                        request.startedAt(),
                        request.completedAt()
                )))
        );
    }

    @GetMapping("/learners/{learnerId}/courses/{courseId}/progress")
    public ApiResponse<CourseProgressApiResponse> getCourseProgress(@PathVariable UUID learnerId,
                                                                    @PathVariable UUID courseId) {
        return ApiResponse.success(
                "Course progress fetched successfully",
                getCourseProgressUseCase.execute(learnerId, courseId)
                        .map(CourseProgressApiResponse::from)
                        .orElseThrow(() -> new ApplicationNotFoundException("Course progress not found"))
        );
    }

    @GetMapping("/learners/{learnerId}/courses/progress")
    public ApiResponse<List<CourseProgressApiResponse>> listLearnerCourseProgress(@PathVariable UUID learnerId) {
        return ApiResponse.success(
                "Course progress fetched successfully",
                listLearnerCourseProgressUseCase.execute(learnerId)
                        .stream()
                        .map(CourseProgressApiResponse::from)
                        .toList()
        );
    }

    @PostMapping("/activities")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<LearningActivityApiResponse> recordActivity(@RequestBody RecordLearningActivityRequest request) {
        return ApiResponse.success(
                "Learning activity recorded successfully",
                LearningActivityApiResponse.from(recordLearningActivityUseCase.execute(new RecordLearningActivityCommand(
                        request.learnerId(),
                        request.courseId(),
                        request.lessonId(),
                        request.activityType(),
                        request.metadata(),
                        request.occurredAt()
                )))
        );
    }

    @GetMapping("/learners/{learnerId}/courses/{courseId}/activities")
    public ApiResponse<List<LearningActivityApiResponse>> listActivities(@PathVariable UUID learnerId,
                                                                         @PathVariable UUID courseId) {
        return ApiResponse.success(
                "Learning activities fetched successfully",
                listLearnerCourseActivitiesUseCase.execute(learnerId, courseId)
                        .stream()
                        .map(LearningActivityApiResponse::from)
                        .toList()
        );
    }

    @PostMapping("/certificates")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CertificateApiResponse> issueCertificate(@RequestBody IssueCertificateRequest request) {
        return ApiResponse.success(
                "Certificate issued successfully",
                CertificateApiResponse.from(issueCertificateUseCase.execute(new IssueCertificateCommand(
                        request.learnerId(),
                        request.courseId(),
                        request.certificateCode(),
                        request.certificateUrl(),
                        request.issuedAt()
                )))
        );
    }

    @GetMapping("/learners/{learnerId}/courses/{courseId}/certificate")
    public ApiResponse<CertificateApiResponse> getCertificate(@PathVariable UUID learnerId,
                                                              @PathVariable UUID courseId) {
        return ApiResponse.success(
                "Certificate fetched successfully",
                getCertificateUseCase.execute(learnerId, courseId)
                        .map(CertificateApiResponse::from)
                        .orElseThrow(() -> new ApplicationNotFoundException("Certificate not found"))
        );
    }
}
