package course.courseservice.application.usecase.category;

import course.courseservice.application.command.category.UpdateCategoryCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.category.CategoryResponse;
import course.courseservice.application.service.CategoryApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record UpdateCategoryUseCase(CategoryApplicationService categoryApplicationService) {

    public ApiResponse<CategoryResponse> execute(UUID id, UpdateCategoryCommand command) {
        return ApiResponse.success("Category updated successfully", categoryApplicationService.update(id, command));
    }
}
