package course.courseservice.api.controller;

import course.courseservice.api.dto.request.CreateCategoryRequest;
import course.courseservice.api.dto.request.UpdateCategoryRequest;
import course.courseservice.api.mapper.CategoryMapper;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.category.CategoryResponse;
import course.courseservice.application.usecase.category.ActivateCategoryUseCase;
import course.courseservice.application.usecase.category.CreateCategoryUseCase;
import course.courseservice.application.usecase.category.DeactivateCategoryUseCase;
import course.courseservice.application.usecase.category.DeleteCategoryUseCase;
import course.courseservice.application.usecase.category.GetAllCategoriesUseCase;
import course.courseservice.application.usecase.category.GetCategoryByIdUseCase;
import course.courseservice.application.usecase.category.GetCategoryBySlugUseCase;
import course.courseservice.application.usecase.category.UpdateCategoryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final CategoryMapper categoryMapper;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase,
                              GetCategoryByIdUseCase getCategoryByIdUseCase,
                              GetCategoryBySlugUseCase getCategoryBySlugUseCase,
                              GetAllCategoriesUseCase getAllCategoriesUseCase,
                              UpdateCategoryUseCase updateCategoryUseCase,
                              ActivateCategoryUseCase activateCategoryUseCase,
                              DeactivateCategoryUseCase deactivateCategoryUseCase,
                              DeleteCategoryUseCase deleteCategoryUseCase,
                              CategoryMapper categoryMapper) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.getCategoryByIdUseCase = getCategoryByIdUseCase;
        this.getCategoryBySlugUseCase = getCategoryBySlugUseCase;
        this.getAllCategoriesUseCase = getAllCategoriesUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.activateCategoryUseCase = activateCategoryUseCase;
        this.deactivateCategoryUseCase = deactivateCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@RequestBody CreateCategoryRequest request) {
        return ResponseEntity.ok(createCategoryUseCase.execute(categoryMapper.toCommand(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(getCategoryByIdUseCase.execute(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(getCategoryBySlugUseCase.execute(slug));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll() {
        return ResponseEntity.ok(getAllCategoriesUseCase.execute());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(@PathVariable UUID id,
                                                                @RequestBody UpdateCategoryRequest request) {
        return ResponseEntity.ok(updateCategoryUseCase.execute(id, categoryMapper.toCommand(request)));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<CategoryResponse>> activate(@PathVariable UUID id) {
        return ResponseEntity.ok(activateCategoryUseCase.execute(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<CategoryResponse>> deactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(deactivateCategoryUseCase.execute(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(deleteCategoryUseCase.execute(id));
    }
}
