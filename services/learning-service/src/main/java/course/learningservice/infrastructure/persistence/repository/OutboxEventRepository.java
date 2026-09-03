package course.learningservice.infrastructure.persistence.repository;

import course.learningservice.infrastructure.persistence.entity.OutboxEventEntity;
import course.learningservice.infrastructure.persistence.entity.enums.OutboxEventStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutboxEventRepository {

    @Select("""
            SELECT id, aggregate_type, aggregate_id, event_type, payload::text AS payload,
                   status, retry_count, last_error, created_at, published_at
            FROM outbox_events
            WHERE id = #{id}
            """)
    Optional<OutboxEventEntity> findById(UUID id);

    @Select("""
            SELECT id, aggregate_type, aggregate_id, event_type, payload::text AS payload,
                   status, retry_count, last_error, created_at, published_at
            FROM outbox_events
            WHERE status = #{status}
            ORDER BY created_at ASC
            """)
    List<OutboxEventEntity> findByStatus(OutboxEventStatus status);

    @Select("""
            SELECT id, aggregate_type, aggregate_id, event_type, payload::text AS payload,
                   status, retry_count, last_error, created_at, published_at
            FROM outbox_events
            WHERE status = 'PENDING'
            ORDER BY created_at ASC
            LIMIT #{limit}
            """)
    List<OutboxEventEntity> findPending(@Param("limit") int limit);

    @Insert("""
            INSERT INTO outbox_events (
                aggregate_type, aggregate_id, event_type, payload,
                status, retry_count, last_error, published_at
            )
            VALUES (
                #{aggregateType}, #{aggregateId}, #{eventType}, CAST(#{payload} AS jsonb),
                #{status}, #{retryCount}, #{lastError}, #{publishedAt}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(OutboxEventEntity outboxEvent);

    @Update("""
            UPDATE outbox_events
            SET status = #{status},
                retry_count = #{retryCount},
                last_error = #{lastError},
                published_at = #{publishedAt}
            WHERE id = #{id}
            """)
    int updatePublishState(OutboxEventEntity outboxEvent);

    @Update("""
            UPDATE outbox_events
            SET status = 'PUBLISHED',
                published_at = #{publishedAt},
                last_error = NULL
            WHERE id = #{id}
            """)
    int markPublished(@Param("id") UUID id, @Param("publishedAt") Instant publishedAt);

    @Update("""
            UPDATE outbox_events
            SET status = 'FAILED',
                retry_count = retry_count + 1,
                last_error = #{lastError}
            WHERE id = #{id}
            """)
    int markFailed(@Param("id") UUID id, @Param("lastError") String lastError);

    @Delete("""
            DELETE FROM outbox_events
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
