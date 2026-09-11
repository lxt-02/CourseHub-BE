package course.courseservice.api.mapper;

import course.courseservice.api.dto.request.CreateCategoryRequest;
import course.courseservice.api.dto.request.UpdateCategoryRequest;
import course.courseservice.application.command.category.CreateCategoryCommand;
import course.courseservice.application.command.category.UpdateCategoryCommand;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CreateCategoryCommand toCommand(CreateCategoryRequest request) {
        return new CreateCategoryCommand(
                request.getName(),
                request.getDescription()
        );
    }

    public UpdateCategoryCommand toCommand(UpdateCategoryRequest request) {
        return new UpdateCategoryCommand(
                request.getName(),
                request.getSlug(),
                request.getDescription()
        );
    }
}
