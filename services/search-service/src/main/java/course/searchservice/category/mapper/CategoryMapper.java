package course.searchservice.category.mapper;

import course.searchservice.category.document.CategoryDocument;
import course.searchservice.category.dto.CategoryEventPayload;
import course.searchservice.category.dto.CategorySearchResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDocument toDocument(CategoryEventPayload payload) {
        if (payload == null) return null;
        return CategoryDocument.builder()
                .id(payload.getId())
                .name(payload.getName())
                .slug(payload.getSlug())
                .description(payload.getDescription())
                .status(payload.getStatus())
                .createdAt(payload.getCreatedAt())
                .updatedAt(payload.getUpdatedAt())
                .build();
    }

    public CategorySearchResponse toResponse(CategoryDocument doc) {
        if (doc == null) return null;
        return CategorySearchResponse.builder()
                .id(doc.getId())
                .name(doc.getName())
                .slug(doc.getSlug())
                .description(doc.getDescription())
                .status(doc.getStatus())
                .createdAt(doc.getCreatedAt())
                .build();
    }
}
