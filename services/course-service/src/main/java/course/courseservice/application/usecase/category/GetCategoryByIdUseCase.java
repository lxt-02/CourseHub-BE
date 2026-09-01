package course.courseservice.application.usecase.category;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.category.CategoryResponse;
import course.courseservice.application.service.CategoryApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record GetCategoryByIdUseCase(CategoryApplicationService categoryApplicationService) {

    public ApiResponse<CategoryResponse> execute(UUID id) {
        return ApiResponse.success("Category fetched successfully", categoryApplicationService.getById(id));
    }
}
