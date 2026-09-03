package course.searchservice.course.service;

import course.searchservice.course.dto.CourseEventPayload;
import course.searchservice.course.dto.CourseSearchRequest;
import course.searchservice.course.dto.CourseSearchResponse;
import course.searchservice.course.dto.PageResponse;

public interface CourseSearchService {
    PageResponse<CourseSearchResponse> searchCourses(CourseSearchRequest request);
    void indexCourse(CourseEventPayload payload);
    void deleteCourseIndex(String id);
    void publishCourseEvent(CourseEventPayload payload);
}
