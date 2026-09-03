package course.searchservice.course.search;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import course.searchservice.course.dto.CourseSearchRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CourseQueryBuilder {

    public NativeQuery buildSearchQuery(CourseSearchRequest request) {
        NativeQueryBuilder builder = new NativeQueryBuilder();

        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        // 1. Keyword search across title, shortDescription, description
        if (StringUtils.hasText(request.getKeyword())) {
            String keyword = request.getKeyword().trim();
            Query multiMatchQuery = Query.of(q -> q
                    .multiMatch(m -> m
                            .query(keyword)
                            .fields("title^3", "shortDescription^2", "description")
                            .fuzziness("AUTO")
                    )
            );
            boolQueryBuilder.must(multiMatchQuery);
        }

        // 2. Category filtering
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            for (String categoryId : request.getCategoryIds()) {
                Query catQuery = Query.of(q -> q.term(t -> t.field("categoryIds").value(categoryId)));
                boolQueryBuilder.filter(catQuery);
            }
        }

        // 3. Price Range
        if (request.getMinPrice() != null || request.getMaxPrice() != null) {
            Query priceQuery = Query.of(q -> q.range(r -> r.number(n -> {
                n.field("price");
                if (request.getMinPrice() != null) {
                    n.gte(request.getMinPrice().doubleValue());
                }
                if (request.getMaxPrice() != null) {
                    n.lte(request.getMaxPrice().doubleValue());
                }
                return n;
            })));
            boolQueryBuilder.filter(priceQuery);
        }


        // 4. Difficulty Level
        if (StringUtils.hasText(request.getDifficultyLevel())) {
            Query diffQuery = Query.of(q -> q.term(t -> t.field("difficultyLevel").value(request.getDifficultyLevel())));
            boolQueryBuilder.filter(diffQuery);
        }

        // 5. Status
        if (StringUtils.hasText(request.getStatus())) {
            Query statusQuery = Query.of(q -> q.term(t -> t.field("status").value(request.getStatus())));
            boolQueryBuilder.filter(statusQuery);
        }

        builder.withQuery(Query.of(q -> q.bool(boolQueryBuilder.build())));

        // 6. Pagination & Sorting
        int page = Math.max(0, request.getPage());
        int size = Math.max(1, Math.min(100, request.getSize()));
        builder.withPageable(PageRequest.of(page, size));

        String sortBy = StringUtils.hasText(request.getSortBy()) ? request.getSortBy() : "createdAt";
        SortOrder order = "asc".equalsIgnoreCase(request.getSortOrder()) ? SortOrder.Asc : SortOrder.Desc;
        builder.withSort(s -> s.field(f -> f.field(sortBy).order(order)));

        return builder.build();
    }
}
