package course.searchservice.category.service;

import course.searchservice.category.document.CategoryDocument;
import course.searchservice.category.dto.CategoryEventPayload;
import course.searchservice.category.dto.CategorySearchRequest;
import course.searchservice.category.dto.CategorySearchResponse;
import course.searchservice.category.event.CategoryEventPublisher;
import course.searchservice.category.mapper.CategoryMapper;
import course.searchservice.category.repository.CategoryElasticRepository;
import course.searchservice.course.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategorySearchServiceImpl implements CategorySearchService {

    private final CategoryElasticRepository categoryElasticRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryEventPublisher categoryEventPublisher;

    @Override
    public PageResponse<CategorySearchResponse> searchCategories(CategorySearchRequest request) {
        Pageable pageable = PageRequest.of(Math.max(0, request.getPage()), Math.max(1, request.getSize()));

        Page<CategoryDocument> pageResult;
        if (StringUtils.hasText(request.getKeyword())) {
            String kw = request.getKeyword().trim();
            pageResult = categoryElasticRepository.findByNameContainingOrDescriptionContaining(kw, kw, pageable);
        } else {
            pageResult = categoryElasticRepository.findAll(pageable);
        }

        List<CategorySearchResponse> responses = pageResult.getContent().stream()
                .map(categoryMapper::toResponse)
                .toList();

        return PageResponse.<CategorySearchResponse>builder()
                .content(responses)
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .last(pageResult.isLast())
                .build();
    }

    @Override
    public void indexCategory(CategoryEventPayload payload) {
        log.info("Indexing category in Elasticsearch: id={}", payload.getId());
        CategoryDocument doc = categoryMapper.toDocument(payload);
        categoryElasticRepository.save(doc);
    }

    @Override
    public void deleteCategoryIndex(String id) {
        log.info("Deleting category from Elasticsearch: id={}", id);
        categoryElasticRepository.deleteById(id);
    }

    @Override
    public void publishCategoryEvent(CategoryEventPayload payload) {
        categoryEventPublisher.publishCategoryEvent(payload);
    }
}
