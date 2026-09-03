package course.courseservice.infrastructure.messaging;

import course.courseservice.application.port.CategoryEventPublisherPort;
import course.courseservice.domain.model.category.aggregate.Category;
import course.courseservice.infrastructure.messaging.dto.CategoryEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaCategoryEventPublisherAdapter implements CategoryEventPublisherPort {

    private static final String TOPIC = "category-events";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishCreated(Category category) {
        publishEvent("EVENT_CREATED", category);
    }

    @Override
    public void publishUpdated(Category category) {
        publishEvent("EVENT_UPDATED", category);
    }

    @Override
    public void publishDeleted(String categoryId) {
        CategoryEventPayload payload = CategoryEventPayload.builder()
                .eventType("EVENT_DELETED")
                .id(categoryId)
                .build();

        log.info("Publishing category deleted event to Kafka: id={}", categoryId);
        kafkaTemplate.send(TOPIC, categoryId, payload);
    }

    private void publishEvent(String eventType, Category category) {
        CategoryEventPayload payload = CategoryEventPayload.builder()
                .eventType(eventType)
                .id(category.getId().toString())
                .name(category.getName())
                .slug(category.getSlug() != null ? category.getSlug().value() : null)
                .description(category.getDescription())
                .status(category.getStatus() != null ? category.getStatus().name() : null)
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();

        log.info("Publishing category event to Kafka: type={}, id={}", eventType, payload.getId());
        kafkaTemplate.send(TOPIC, payload.getId(), payload);
    }
}
