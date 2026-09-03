package course.searchservice.course.cache;

import course.searchservice.cache.VersionedCacheStore;
import course.searchservice.course.dto.CourseSearchRequest;
import course.searchservice.course.dto.CourseSearchResponse;
import course.searchservice.course.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseSearchCache {

    private static final String DOMAIN = "courses";
    private final VersionedCacheStore versionedCacheStore;

    @SuppressWarnings("unchecked")
    public PageResponse<CourseSearchResponse> getCachedSearchResults(CourseSearchRequest request) {
        String rawKey = request.toString();
        Object cached = versionedCacheStore.get(DOMAIN, rawKey);
        if (cached instanceof PageResponse) {
            return (PageResponse<CourseSearchResponse>) cached;
        }
        return null;
    }

    public void cacheSearchResults(CourseSearchRequest request, PageResponse<CourseSearchResponse> response) {
        String rawKey = request.toString();
        versionedCacheStore.put(DOMAIN, rawKey, response);
    }

    public void invalidateCache() {
        versionedCacheStore.invalidateDomain(DOMAIN);
    }
}
