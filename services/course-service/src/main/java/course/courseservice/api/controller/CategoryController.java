package course.courseservice.api.controller;

import course.courseservice.api.dto.request.CreateCategoryRequest;
import course.courseservice.api.dto.request.UpdateCategoryRequest;
import course.courseservice.api.dto.response.CategoryApiResponse;
import course.courseservice.application.command.category.CreateCategoryCommand;
import course.courseservice.application.command.category.UpdateCategoryCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.usecase.category.ActivateCategoryUseCase;
import course.courseservice.application.usecase.category.CreateCategoryUseCase;
import course.courseservice.application.usecase.category.DeactivateCategoryUseCase;
import course.courseservice.application.usecase.category.DeleteCategoryUseCase;
import course.courseservice.application.usecase.category.GetAllCategoriesUseCase;
import course.courseservice.application.usecase.category.GetCategoryByIdUseCase;
import course.courseservice.application.usecase.category.GetCategoryBySlugUseCase;
import course.courseservice.application.usecase.category.UpdateCategoryUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPath.CATEGORY)
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final GetCategoryBySlugUseCase getCategoryBySlugUseCase;
    private final GetAllCategoriesUseCase getAllCategoriesUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final ActivateCategoryUseCase activateCategoryUseCase;
    private final DeactivateCategoryUseCase deactivateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase,
                              GetCategoryByIdUseCase getCategoryByIdUseCase,
                              GetCategoryBySlugUseCase getCategoryBySlugUseCase,
                              GetAllCategoriesUseCase getAllCategoriesUseCase,
                              UpdateCategoryUseCase updateCategoryUseCase,
                              ActivateCategoryUseCase activateCategoryUseCase,
                              DeactivateCategoryUseCase deactivateCategoryUseCase,
                              DeleteCategoryUseCase deleteCategoryUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.getCategoryByIdUseCase = getCategoryByIdUseCase;
        this.getCategoryBySlugUseCase = getCategoryBySlugUseCase;
        this.getAllCategoriesUseCase = getAllCategoriesUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.activateCategoryUseCase = activateCategoryUseCase;
        this.deactivateCategoryUseCase = deactivateCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CategoryApiResponse> create(@RequestBody CreateCategoryRequest request) {
        return createCategoryUseCase.execute(new CreateCategoryCommand(request.name(), request.description()))
                .map(CategoryApiResponse::from);
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryApiResponse> getById(@PathVariable UUID id) {
        return getCategoryByIdUseCase.execute(id).map(CategoryApiResponse::from);
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse<CategoryApiResponse> getBySlug(@PathVariable String slug) {
        return getCategoryBySlugUseCase.execute(slug).map(CategoryApiResponse::from);
    }

    @GetMapping
    public ApiResponse<List<CategoryApiResponse>> getAll() {
        return getAllCategoriesUseCase.execute()
                .map(categories -> categories.stream().map(CategoryApiResponse::from).toList());
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryApiResponse> update(@PathVariable UUID id, @RequestBody UpdateCategoryRequest request) {
        return updateCategoryUseCase.execute(id, new UpdateCategoryCommand(request.name(), request.slug(), request.description()))
                .map(CategoryApiResponse::from);
    }

    @PostMapping("/{id}/activate")
    public ApiResponse<CategoryApiResponse> activate(@PathVariable UUID id) {
        return activateCategoryUseCase.execute(id).map(CategoryApiResponse::from);
    }

    @PostMapping("/{id}/deactivate")
    public ApiResponse<CategoryApiResponse> deactivate(@PathVariable UUID id) {
        return deactivateCategoryUseCase.execute(id).map(CategoryApiResponse::from);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        return deleteCategoryUseCase.execute(id);
    }
}
