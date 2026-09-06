package course.courseservice.application.dto.course;

import course.courseservice.domain.model.course.aggregate.Course;
import course.courseservice.domain.model.course.enums.CourseDifficultyLevel;
import course.courseservice.domain.model.course.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private UUID id;
    private UUID managerId;
    private String title;
    private String slug;
    private String shortDescription;
    private String description;
    private String thumbnailUrl;
    private BigDecimal price;
    private CourseDifficultyLevel difficultyLevel;
    private CourseStatus status;
    private Instant publishedAt;
    private Set<UUID> categoryIds;
    private List<CourseModuleResponse> modules;
    private List<CourseAssetResponse> assets;
    private Instant createdAt;
    private Instant updatedAt;

    public static CourseResponse from(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getManagerId(),
                course.getTitle(),
                course.getSlug().value(),
                course.getShortDescription(),
                course.getDescription(),
                course.getThumbnailUrl(),
                course.getPrice().amount(),
                course.getDifficultyLevel(),
                course.getStatus(),
                course.getPublishedAt(),
                course.getCategoryIds(),
                course.getModules() != null ? course.getModules().stream().map(CourseModuleResponse::from).toList() : List.of(),
                course.getAssets() != null ? course.getAssets().stream().map(CourseAssetResponse::from).toList() : List.of(),
                course.getCreatedAt(),
                course.getUpdatedAt()
        );
    }
}
