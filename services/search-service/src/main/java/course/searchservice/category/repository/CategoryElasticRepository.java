package course.searchservice.category.repository;

import course.searchservice.category.document.CategoryDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryElasticRepository extends ElasticsearchRepository<CategoryDocument, String> {
    Page<CategoryDocument> findByNameContainingOrDescriptionContaining(String nameKeyword, String descKeyword, Pageable pageable);
}
