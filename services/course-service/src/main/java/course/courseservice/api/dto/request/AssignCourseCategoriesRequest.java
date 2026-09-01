package course.courseservice.api.dto.request;

import java.util.Set;
import java.util.UUID;

public record AssignCourseCategoriesRequest(Set<UUID> categoryIds) {
}
