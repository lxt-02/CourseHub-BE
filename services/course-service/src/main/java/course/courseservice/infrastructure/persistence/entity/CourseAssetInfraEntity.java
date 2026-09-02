package course.courseservice.infrastructure.persistence.entity;

import course.courseservice.infrastructure.persistence.entity.enums.CourseAssetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseAssetInfraEntity {

    private UUID id;
    private UUID courseId;
    private CourseAssetType assetType;
    private String assetUrl;
    private String fileName;
    private Long fileSize;
    private Instant createdAt;
}
