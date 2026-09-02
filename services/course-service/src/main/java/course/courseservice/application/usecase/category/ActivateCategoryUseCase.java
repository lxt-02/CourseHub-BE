package course.courseservice.application.usecase.category;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.category.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record ActivateCategoryUseCase(CategoryUseCaseHandler CategoryUseCaseHandler) {

    public ApiResponse<CategoryResponse> execute(UUID id) {
        return ApiResponse.success("Category activated successfully", CategoryUseCaseHandler.activate(id));
    }
}
