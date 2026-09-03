package course.searchservice.category.event;

import course.searchservice.category.dto.CategoryEventPayload;
import course.searchservice.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCategoryEvent(CategoryEventPayload payload) {
        log.info("Publishing Kafka category event: type={}, id={}", payload.getEventType(), payload.getId());
        kafkaTemplate.send(KafkaConfig.CATEGORY_EVENTS_TOPIC, payload.getId(), payload);
    }
}
