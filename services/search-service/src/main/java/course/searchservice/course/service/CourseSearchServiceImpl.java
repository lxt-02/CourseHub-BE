package course.searchservice.course.service;

import course.searchservice.course.cache.CourseSearchCache;
import course.searchservice.course.document.CourseDocument;
import course.searchservice.course.dto.CourseEventPayload;
import course.searchservice.course.dto.CourseSearchRequest;
import course.searchservice.course.dto.CourseSearchResponse;
import course.searchservice.course.dto.PageResponse;
import course.searchservice.course.event.CourseEventPublisher;
import course.searchservice.course.mapper.CourseMapper;
import course.searchservice.course.repository.CourseElasticRepository;
import course.searchservice.course.repository.CourseSearchCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseSearchServiceImpl implements CourseSearchService {

    private final CourseElasticRepository courseElasticRepository;
    private final CourseSearchCustomRepository courseSearchCustomRepository;
    private final CourseMapper courseMapper;
    private final CourseSearchCache courseSearchCache;
    private final CourseEventPublisher courseEventPublisher;

    @Override
    public PageResponse<CourseSearchResponse> searchCourses(CourseSearchRequest request) {
        // 1. Check Redis Cache
        PageResponse<CourseSearchResponse> cached = courseSearchCache.getCachedSearchResults(request);
        if (cached != null) {
            log.info("Returning cached course search results for query: {}", request.getKeyword());
            return cached;
        }

        // 2. Query Elasticsearch
        log.info("Querying Elasticsearch for courses with request: {}", request);
        Page<CourseDocument> pageResult = courseSearchCustomRepository.searchCourses(request);

        List<CourseSearchResponse> responses = pageResult.getContent().stream()
                .map(courseMapper::toResponse)
                .toList();

        PageResponse<CourseSearchResponse> response = PageResponse.<CourseSearchResponse>builder()
                .content(responses)
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .last(pageResult.isLast())
                .build();

        // 3. Populate Cache
        courseSearchCache.cacheSearchResults(request, response);

        return response;
    }

    @Override
    public void indexCourse(CourseEventPayload payload) {
        log.info("Indexing course in Elasticsearch: id={}", payload.getId());
        CourseDocument doc = courseMapper.toDocument(payload);
        courseElasticRepository.save(doc);
        courseSearchCache.invalidateCache();
    }

    @Override
    public void deleteCourseIndex(String id) {
        log.info("Deleting course from Elasticsearch: id={}", id);
        courseElasticRepository.deleteById(id);
        courseSearchCache.invalidateCache();
    }

    @Override
    public void publishCourseEvent(CourseEventPayload payload) {
        courseEventPublisher.publishCourseEvent(payload);
    }
}
