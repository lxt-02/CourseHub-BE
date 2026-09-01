package course.enrollmentservice.infrastructure.persistence.repository;

import course.enrollmentservice.infrastructure.persistence.entity.ProcessedEventEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;
import java.util.UUID;

public interface ProcessedEventRepository {

    @Select("""
            SELECT event_id, consumer_name, event_type, processed_at
            FROM processed_events
            WHERE event_id = #{eventId}
              AND consumer_name = #{consumerName}
            """)
    Optional<ProcessedEventEntity> findById(@Param("eventId") UUID eventId,
                                            @Param("consumerName") String consumerName);

    @Select("""
            SELECT EXISTS (
                SELECT 1
                FROM processed_events
                WHERE event_id = #{eventId}
                  AND consumer_name = #{consumerName}
            )
            """)
    boolean existsById(@Param("eventId") UUID eventId,
                       @Param("consumerName") String consumerName);

    @Insert("""
            INSERT INTO processed_events (event_id, consumer_name, event_type)
            VALUES (#{eventId}, #{consumerName}, #{eventType})
            """)
    int insert(ProcessedEventEntity processedEvent);

    @Delete("""
            DELETE FROM processed_events
            WHERE event_id = #{eventId}
              AND consumer_name = #{consumerName}
            """)
    int deleteById(@Param("eventId") UUID eventId,
                   @Param("consumerName") String consumerName);
}
