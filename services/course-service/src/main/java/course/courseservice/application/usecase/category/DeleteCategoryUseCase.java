package course.courseservice.application.usecase.category;

import course.courseservice.application.dto.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record DeleteCategoryUseCase(CategoryUseCaseHandler CategoryUseCaseHandler) {

    public ApiResponse<Void> execute(UUID id) {
        CategoryUseCaseHandler.delete(id);
        return ApiResponse.success("Category deleted successfully", null);
    }
}
