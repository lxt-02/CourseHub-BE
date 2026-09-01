package course.courseservice.application.usecase.category;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.category.CategoryResponse;
import course.courseservice.application.service.CategoryApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record GetAllCategoriesUseCase(CategoryApplicationService categoryApplicationService) {

    public ApiResponse<List<CategoryResponse>> execute() {
        return ApiResponse.success("Categories fetched successfully", categoryApplicationService.getAll());
    }
}
