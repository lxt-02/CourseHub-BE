package course.courseservice.application.command.course;

import java.util.Set;
import java.util.UUID;

public record AssignCourseCategoriesCommand(Set<UUID> categoryIds) {
}
