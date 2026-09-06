package course.courseservice.api.dto.response;

import course.courseservice.application.dto.course.CourseResponse;
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
public class CourseApiResponse {
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
    private List<CourseModuleApiResponse> modules;
    private List<CourseAssetApiResponse> assets;
    private Instant createdAt;
    private Instant updatedAt;

    public static CourseApiResponse from(CourseResponse response) {
        return new CourseApiResponse(
                response.getId(),
                response.getManagerId(),
                response.getTitle(),
                response.getSlug(),
                response.getShortDescription(),
                response.getDescription(),
                response.getThumbnailUrl(),
                response.getPrice(),
                response.getDifficultyLevel(),
                response.getStatus(),
                response.getPublishedAt(),
                response.getCategoryIds(),
                response.getModules() != null ? response.getModules().stream().map(CourseModuleApiResponse::from).toList() : List.of(),
                response.getAssets() != null ? response.getAssets().stream().map(CourseAssetApiResponse::from).toList() : List.of(),
                response.getCreatedAt(),
                response.getUpdatedAt()
        );
    }
}
