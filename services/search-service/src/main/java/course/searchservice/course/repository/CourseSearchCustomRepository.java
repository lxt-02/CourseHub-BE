package course.searchservice.course.repository;

import course.searchservice.course.document.CourseDocument;
import course.searchservice.course.dto.CourseSearchRequest;
import org.springframework.data.domain.Page;

public interface CourseSearchCustomRepository {
    Page<CourseDocument> searchCourses(CourseSearchRequest request);
}
