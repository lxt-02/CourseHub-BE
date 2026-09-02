package course.courseservice.application.usecase.category;

import course.courseservice.application.command.category.CreateCategoryCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.category.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public record CreateCategoryUseCase(CategoryUseCaseHandler CategoryUseCaseHandler) {

    public ApiResponse<CategoryResponse> execute(CreateCategoryCommand command) {
        return ApiResponse.success("Category created successfully", CategoryUseCaseHandler.create(command));
    }
}
