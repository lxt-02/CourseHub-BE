package course.searchservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public static final String COURSE_EVENTS_TOPIC = "course-events";
    public static final String CATEGORY_EVENTS_TOPIC = "category-events";

    @Bean
    public NewTopic courseEventsTopic() {
        return TopicBuilder.name(COURSE_EVENTS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic categoryEventsTopic() {
        return TopicBuilder.name(CATEGORY_EVENTS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
