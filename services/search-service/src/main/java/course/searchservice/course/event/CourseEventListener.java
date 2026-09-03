package course.searchservice.course.event;

import course.searchservice.config.KafkaConfig;
import course.searchservice.course.dto.CourseEventPayload;
import course.searchservice.course.service.CourseSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourseEventListener {

    private final CourseSearchService courseSearchService;

    @KafkaListener(topics = KafkaConfig.COURSE_EVENTS_TOPIC, groupId = "search-service-group")
    public void handleCourseEvent(CourseEventPayload payload) {
        log.info("Received Kafka course event: type={}, id={}", payload.getEventType(), payload.getId());
        if ("EVENT_DELETED".equalsIgnoreCase(payload.getEventType())) {
            courseSearchService.deleteCourseIndex(payload.getId());
        } else {
            courseSearchService.indexCourse(payload);
        }
    }
}
