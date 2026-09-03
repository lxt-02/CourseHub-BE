package course.searchservice.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySearchRequest {
    private String keyword;
    private String status;

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 10;
}
