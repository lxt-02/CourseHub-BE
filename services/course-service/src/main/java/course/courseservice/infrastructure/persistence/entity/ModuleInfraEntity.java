package course.courseservice.infrastructure.persistence.entity;

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
public class ModuleInfraEntity {

    private UUID id;
    private UUID courseId;
    private String title;
    private String description;
    private Integer position;
    private Instant createdAt;
    private Instant updatedAt;
}
