package cms.communicationservice.infrastructure.persistence.repository;

import cms.communicationservice.infrastructure.persistence.entity.NotificationDeliveryEntity;
import cms.communicationservice.infrastructure.persistence.entity.enums.DeliveryStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationDeliveryRepository {

    @Select("""
            SELECT id, notification_id, channel, status, retry_count, sent_at,
                   next_retry_at, error_message, created_at, updated_at
            FROM notification_deliveries
            WHERE id = #{id}
            """)
    Optional<NotificationDeliveryEntity> findById(UUID id);

    @Select("""
            SELECT id, notification_id, channel, status, retry_count, sent_at,
                   next_retry_at, error_message, created_at, updated_at
            FROM notification_deliveries
            WHERE notification_id = #{notificationId}
            ORDER BY created_at DESC
            """)
    List<NotificationDeliveryEntity> findByNotificationId(UUID notificationId);

    @Select("""
            SELECT id, notification_id, channel, status, retry_count, sent_at,
                   next_retry_at, error_message, created_at, updated_at
            FROM notification_deliveries
            WHERE status = #{status}
            ORDER BY next_retry_at ASC
            """)
    List<NotificationDeliveryEntity> findByStatus(DeliveryStatus status);

    @Select("""
            SELECT id, notification_id, channel, status, retry_count, sent_at,
                   next_retry_at, error_message, created_at, updated_at
            FROM notification_deliveries
            WHERE status = 'PENDING'
              AND (next_retry_at IS NULL OR next_retry_at <= CURRENT_TIMESTAMP)
            ORDER BY created_at ASC
            LIMIT #{limit}
            """)
    List<NotificationDeliveryEntity> findPendingForDelivery(@Param("limit") int limit);

    @Insert("""
            INSERT INTO notification_deliveries (
                notification_id, channel, status, retry_count, sent_at,
                next_retry_at, error_message
            )
            VALUES (
                #{notificationId}, #{channel}, #{status}, #{retryCount}, #{sentAt},
                #{nextRetryAt}, #{errorMessage}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(NotificationDeliveryEntity delivery);

    @Update("""
            UPDATE notification_deliveries
            SET status = #{status},
                retry_count = #{retryCount},
                sent_at = #{sentAt},
                next_retry_at = #{nextRetryAt},
                error_message = #{errorMessage},
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int update(NotificationDeliveryEntity delivery);

    @Delete("""
            DELETE FROM notification_deliveries
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
