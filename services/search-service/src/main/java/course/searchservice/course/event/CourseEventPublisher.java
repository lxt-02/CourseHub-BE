package course.searchservice.course.event;

import course.searchservice.config.KafkaConfig;
import course.searchservice.course.dto.CourseEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCourseEvent(CourseEventPayload payload) {
        log.info("Publishing Kafka course event: type={}, id={}", payload.getEventType(), payload.getId());
        kafkaTemplate.send(KafkaConfig.COURSE_EVENTS_TOPIC, payload.getId(), payload);
    }
}
