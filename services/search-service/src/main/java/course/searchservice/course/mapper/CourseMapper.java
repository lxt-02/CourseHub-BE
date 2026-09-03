package course.searchservice.course.mapper;

import course.searchservice.course.document.CourseDocument;
import course.searchservice.course.dto.CourseEventPayload;
import course.searchservice.course.dto.CourseSearchResponse;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDocument toDocument(CourseEventPayload payload) {
        if (payload == null) return null;
        return CourseDocument.builder()
                .id(payload.getId())
                .managerId(payload.getManagerId())
                .title(payload.getTitle())
                .slug(payload.getSlug())
                .shortDescription(payload.getShortDescription())
                .description(payload.getDescription())
                .thumbnailUrl(payload.getThumbnailUrl())
                .price(payload.getPrice())
                .difficultyLevel(payload.getDifficultyLevel())
                .status(payload.getStatus())
                .categoryIds(payload.getCategoryIds())
                .publishedAt(payload.getPublishedAt())
                .createdAt(payload.getCreatedAt())
                .updatedAt(payload.getUpdatedAt())
                .build();
    }

    public CourseSearchResponse toResponse(CourseDocument doc) {
        if (doc == null) return null;
        return CourseSearchResponse.builder()
                .id(doc.getId())
                .managerId(doc.getManagerId())
                .title(doc.getTitle())
                .slug(doc.getSlug())
                .shortDescription(doc.getShortDescription())
                .description(doc.getDescription())
                .thumbnailUrl(doc.getThumbnailUrl())
                .price(doc.getPrice())
                .difficultyLevel(doc.getDifficultyLevel())
                .status(doc.getStatus())
                .categoryIds(doc.getCategoryIds())
                .publishedAt(doc.getPublishedAt())
                .createdAt(doc.getCreatedAt())
                .build();
    }
}
