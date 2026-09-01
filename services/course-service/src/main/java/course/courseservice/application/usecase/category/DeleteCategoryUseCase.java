package course.courseservice.application.usecase.category;

import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.service.CategoryApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record DeleteCategoryUseCase(CategoryApplicationService categoryApplicationService) {

    public ApiResponse<Void> execute(UUID id) {
        categoryApplicationService.delete(id);
        return ApiResponse.success("Category deleted successfully", null);
    }
}
