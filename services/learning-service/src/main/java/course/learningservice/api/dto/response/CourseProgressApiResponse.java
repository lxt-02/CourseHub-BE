package course.learningservice.api.dto.response;

import course.learningservice.application.dto.CourseProgressResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CourseProgressApiResponse(
        UUID id,
        UUID learnerId,
        UUID courseId,
        int totalLessons,
        int completedLessons,
        BigDecimal progressPercent,
        String status,
        Instant startedAt,
        Instant completedAt,
        Instant createdAt,
        Instant updatedAt
) {
    public static CourseProgressApiResponse from(CourseProgressResponse response) {
        return new CourseProgressApiResponse(
                response.id(),
                response.learnerId(),
                response.courseId(),
                response.totalLessons(),
                response.completedLessons(),
                response.progressPercent(),
                response.status(),
                response.startedAt(),
                response.completedAt(),
                response.createdAt(),
                response.updatedAt()
        );
    }
}
