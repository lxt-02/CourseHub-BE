package course.searchservice.course.controller;

import course.searchservice.course.dto.CourseEventPayload;
import course.searchservice.course.dto.CourseSearchRequest;
import course.searchservice.course.dto.CourseSearchResponse;
import course.searchservice.course.dto.PageResponse;
import course.searchservice.course.service.CourseSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/search/courses")
@RequiredArgsConstructor
public class CourseSearchController {

    private final CourseSearchService courseSearchService;

    @GetMapping
    public ResponseEntity<PageResponse<CourseSearchResponse>> searchCourses(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<String> categoryIds,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String difficultyLevel,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {

        CourseSearchRequest request = CourseSearchRequest.builder()
                .keyword(keyword)
                .categoryIds(categoryIds)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .difficultyLevel(difficultyLevel)
                .status(status)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortOrder(sortOrder)
                .build();

        return ResponseEntity.ok(courseSearchService.searchCourses(request));
    }

    @PostMapping("/events/publish")
    public ResponseEntity<String> publishCourseEvent(@RequestBody CourseEventPayload payload) {
        courseSearchService.publishCourseEvent(payload);
        return ResponseEntity.ok("Event published to Kafka successfully");
    }

    @PostMapping("/index")
    public ResponseEntity<String> indexCourseDirectly(@RequestBody CourseEventPayload payload) {
        courseSearchService.indexCourse(payload);
        return ResponseEntity.ok("Course indexed successfully");
    }

    @DeleteMapping("/index/{id}")
    public ResponseEntity<String> deleteCourseIndexDirectly(@PathVariable String id) {
        courseSearchService.deleteCourseIndex(id);
        return ResponseEntity.ok("Course index deleted successfully");
    }
}
