package course.searchservice.course.repository;

import course.searchservice.course.document.CourseDocument;
import course.searchservice.course.dto.CourseSearchRequest;
import course.searchservice.course.search.CourseQueryBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseSearchCustomRepositoryImpl implements CourseSearchCustomRepository {

    private final ElasticsearchOperations elasticsearchOperations;
    private final CourseQueryBuilder courseQueryBuilder;

    @Override
    public Page<CourseDocument> searchCourses(CourseSearchRequest request) {
        NativeQuery nativeQuery = courseQueryBuilder.buildSearchQuery(request);
        SearchHits<CourseDocument> searchHits = elasticsearchOperations.search(nativeQuery, CourseDocument.class);

        List<CourseDocument> documents = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();

        return new PageImpl<>(documents, nativeQuery.getPageable(), searchHits.getTotalHits());
    }
}
