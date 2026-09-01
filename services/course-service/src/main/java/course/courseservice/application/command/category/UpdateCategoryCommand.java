package course.courseservice.application.command.category;

public record UpdateCategoryCommand(String name, String slug, String description) {
}
