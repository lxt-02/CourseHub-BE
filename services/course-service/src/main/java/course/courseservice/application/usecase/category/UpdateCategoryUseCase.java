package course.courseservice.application.usecase.category;

import course.courseservice.application.command.category.UpdateCategoryCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.category.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record UpdateCategoryUseCase(CategoryUseCaseHandler CategoryUseCaseHandler) {

    public ApiResponse<CategoryResponse> execute(UUID id, UpdateCategoryCommand command) {
        return ApiResponse.success("Category updated successfully", CategoryUseCaseHandler.update(id, command));
    }
}
