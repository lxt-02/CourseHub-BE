package course.courseservice.application.usecase.category;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.category.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record GetAllCategoriesUseCase(CategoryUseCaseHandler CategoryUseCaseHandler) {

    public ApiResponse<List<CategoryResponse>> execute() {
        return ApiResponse.success("Categories fetched successfully", CategoryUseCaseHandler.getAll());
    }
}
