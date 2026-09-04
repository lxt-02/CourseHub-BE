package course.learningservice.api.dto.response;

import course.learningservice.application.dto.LessonProgressResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record LessonProgressApiResponse(
        UUID id,
        UUID learnerId,
        UUID courseId,
        UUID lessonId,
        int watchedSeconds,
        BigDecimal progressPercent,
        String status,
        Instant startedAt,
        Instant lastAccessedAt,
        Instant completedAt,
        Instant createdAt,
        Instant updatedAt
) {
    public static LessonProgressApiResponse from(LessonProgressResponse response) {
        return new LessonProgressApiResponse(
                response.id(),
                response.learnerId(),
                response.courseId(),
                response.lessonId(),
                response.watchedSeconds(),
                response.progressPercent(),
                response.status(),
                response.startedAt(),
                response.lastAccessedAt(),
                response.completedAt(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
