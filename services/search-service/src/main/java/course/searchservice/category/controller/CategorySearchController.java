package course.searchservice.category.controller;

import course.searchservice.category.dto.CategoryEventPayload;
import course.searchservice.category.dto.CategorySearchRequest;
import course.searchservice.category.dto.CategorySearchResponse;
import course.searchservice.category.service.CategorySearchService;
import course.searchservice.course.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search/categories")
@RequiredArgsConstructor
public class CategorySearchController {

    private final CategorySearchService categorySearchService;

    @GetMapping
    public ResponseEntity<PageResponse<CategorySearchResponse>> searchCategories(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        CategorySearchRequest request = CategorySearchRequest.builder()
                .keyword(keyword)
                .status(status)
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(categorySearchService.searchCategories(request));
    }

    @PostMapping("/events/publish")
    public ResponseEntity<String> publishCategoryEvent(@RequestBody CategoryEventPayload payload) {
        categorySearchService.publishCategoryEvent(payload);
        return ResponseEntity.ok("Category event published to Kafka successfully");
    }

    @PostMapping("/index")
    public ResponseEntity<String> indexCategoryDirectly(@RequestBody CategoryEventPayload payload) {
        categorySearchService.indexCategory(payload);
        return ResponseEntity.ok("Category indexed successfully");
    }

    @DeleteMapping("/index/{id}")
    public ResponseEntity<String> deleteCategoryIndexDirectly(@PathVariable String id) {
        categorySearchService.deleteCategoryIndex(id);
        return ResponseEntity.ok("Category index deleted successfully");
    }
}
