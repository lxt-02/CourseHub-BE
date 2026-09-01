package course.courseservice.application.usecase.category;

import course.courseservice.application.command.category.CreateCategoryCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.category.CategoryResponse;
import course.courseservice.application.service.CategoryApplicationService;
import org.springframework.stereotype.Service;

@Service
public record CreateCategoryUseCase(CategoryApplicationService categoryApplicationService) {

    public ApiResponse<CategoryResponse> execute(CreateCategoryCommand command) {
        return ApiResponse.success("Category created successfully", categoryApplicationService.create(command));
    }
}
