package course.searchservice.category.event;

import course.searchservice.category.dto.CategoryEventPayload;
import course.searchservice.category.service.CategorySearchService;
import course.searchservice.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryEventListener {

    private final CategorySearchService categorySearchService;

    @KafkaListener(topics = KafkaConfig.CATEGORY_EVENTS_TOPIC, groupId = "search-service-group")
    public void handleCategoryEvent(CategoryEventPayload payload) {
        log.info("Received Kafka category event: type={}, id={}", payload.getEventType(), payload.getId());
        if ("EVENT_DELETED".equalsIgnoreCase(payload.getEventType())) {
            categorySearchService.deleteCategoryIndex(payload.getId());
        } else {
            categorySearchService.indexCategory(payload);
        }
    }
}
