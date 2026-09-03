package course.courseservice.infrastructure.messaging;

import course.courseservice.application.port.CourseEventPublisherPort;
import course.courseservice.domain.model.course.aggregate.Course;
import course.courseservice.infrastructure.messaging.dto.CourseEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaCourseEventPublisherAdapter implements CourseEventPublisherPort {

    private static final String TOPIC = "course-events";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishCreated(Course course) {
        publishEvent("EVENT_CREATED", course);
    }

    @Override
    public void publishUpdated(Course course) {
        publishEvent("EVENT_UPDATED", course);
    }

    @Override
    public void publishDeleted(String courseId) {
        CourseEventPayload payload = CourseEventPayload.builder()
                .eventType("EVENT_DELETED")
                .id(courseId)
                .build();

        log.info("Publishing course deleted event to Kafka: id={}", courseId);
        kafkaTemplate.send(TOPIC, courseId, payload);
    }

    private void publishEvent(String eventType, Course course) {
        CourseEventPayload payload = CourseEventPayload.builder()
                .eventType(eventType)
                .id(course.getId().toString())
                .managerId(course.getManagerId().toString())
                .title(course.getTitle())
                .slug(course.getSlug() != null ? course.getSlug().value() : null)
                .shortDescription(course.getShortDescription())
                .description(course.getDescription())
                .thumbnailUrl(course.getThumbnailUrl())
                .price(course.getPrice() != null ? course.getPrice().amount() : null)
                .difficultyLevel(course.getDifficultyLevel() != null ? course.getDifficultyLevel().name() : null)
                .status(course.getStatus() != null ? course.getStatus().name() : null)
                .categoryIds(course.getCategoryIds().stream().map(Object::toString).collect(Collectors.toList()))
                .publishedAt(course.getPublishedAt())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();

        log.info("Publishing course event to Kafka: type={}, id={}", eventType, payload.getId());
        kafkaTemplate.send(TOPIC, payload.getId(), payload);
    }
}
