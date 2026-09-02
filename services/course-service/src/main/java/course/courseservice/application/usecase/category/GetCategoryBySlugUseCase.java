package course.courseservice.application.usecase.category;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.category.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public record GetCategoryBySlugUseCase(CategoryUseCaseHandler CategoryUseCaseHandler) {

    public ApiResponse<CategoryResponse> execute(String slug) {
        return ApiResponse.success("Category fetched successfully", CategoryUseCaseHandler.getBySlug(slug));
    }
}
