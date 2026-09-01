package course.courseservice.application.usecase.category;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.category.CategoryResponse;
import course.courseservice.application.service.CategoryApplicationService;
import org.springframework.stereotype.Service;

@Service
public record GetCategoryBySlugUseCase(CategoryApplicationService categoryApplicationService) {

    public ApiResponse<CategoryResponse> execute(String slug) {
        return ApiResponse.success("Category fetched successfully", categoryApplicationService.getBySlug(slug));
    }
}
