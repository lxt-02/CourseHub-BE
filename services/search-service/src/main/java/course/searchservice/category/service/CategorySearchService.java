package course.searchservice.category.service;

import course.searchservice.category.dto.CategoryEventPayload;
import course.searchservice.category.dto.CategorySearchRequest;
import course.searchservice.category.dto.CategorySearchResponse;
import course.searchservice.course.dto.PageResponse;

public interface CategorySearchService {
    PageResponse<CategorySearchResponse> searchCategories(CategorySearchRequest request);
    void indexCategory(CategoryEventPayload payload);
    void deleteCategoryIndex(String id);
    void publishCategoryEvent(CategoryEventPayload payload);
}
