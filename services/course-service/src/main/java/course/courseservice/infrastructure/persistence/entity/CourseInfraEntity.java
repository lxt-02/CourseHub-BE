package course.courseservice.infrastructure.persistence.entity;

import course.courseservice.infrastructure.persistence.entity.enums.CourseDifficultyLevel;
import course.courseservice.infrastructure.persistence.entity.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseInfraEntity {

    private UUID id;
    private UUID managerId;
    private String title;
    private String slug;
    private String shortDescription;
    private String description;
    private String thumbnailUrl;

    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;

    private CourseDifficultyLevel difficultyLevel;

    @Builder.Default
    private CourseStatus status = CourseStatus.DRAFT;

    private Instant publishedAt;
    private Instant createdAt;
    private Instant updatedAt;
}
