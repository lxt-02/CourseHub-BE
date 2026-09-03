package course.searchservice.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseSearchRequest {
    private String keyword;
    private List<String> categoryIds;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String difficultyLevel; // BEGINNER, INTERMEDIATE, ADVANCED
    private String status;          // PUBLISHED, DRAFT, etc.

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 10;

    @Builder.Default
    private String sortBy = "createdAt";

    @Builder.Default
    private String sortOrder = "desc";
}
