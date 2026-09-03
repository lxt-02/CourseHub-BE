package course.searchservice.course.repository;

import course.searchservice.course.document.CourseDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseElasticRepository extends ElasticsearchRepository<CourseDocument, String> {
}
