package cms.communicationservice.infrastructure.persistence.repository;

import cms.communicationservice.infrastructure.persistence.entity.NotificationEntity;
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

public interface NotificationRepository {

    @Select("""
            SELECT id, recipient_user_id, type, title, content, reference_type,
                   reference_id, read_at, created_at
            FROM notifications
            WHERE id = #{id}
            """)
    Optional<NotificationEntity> findById(UUID id);

    @Select("""
            SELECT id, recipient_user_id, type, title, content, reference_type,
                   reference_id, read_at, created_at
            FROM notifications
            WHERE recipient_user_id = #{recipientUserId}
            ORDER BY created_at DESC
            """)
    List<NotificationEntity> findByRecipientUserId(UUID recipientUserId);

    @Select("""
            SELECT id, recipient_user_id, type, title, content, reference_type,
                   reference_id, read_at, created_at
            FROM notifications
            WHERE recipient_user_id = #{recipientUserId}
              AND read_at IS NULL
            ORDER BY created_at DESC
            """)
    List<NotificationEntity> findUnreadByRecipientUserId(UUID recipientUserId);

    @Insert("""
            INSERT INTO notifications (
                recipient_user_id, type, title, content, reference_type, reference_id, read_at
            )
            VALUES (
                #{recipientUserId}, #{type}, #{title}, #{content}, #{referenceType}, #{referenceId}, #{readAt}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(NotificationEntity notification);

    @Update("""
            UPDATE notifications
            SET read_at = #{readAt}
            WHERE id = #{id}
            """)
    int markRead(@Param("id") UUID id, @Param("readAt") Instant readAt);

    @Delete("""
            DELETE FROM notifications
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
