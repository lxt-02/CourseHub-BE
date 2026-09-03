package cms.communicationservice.infrastructure.persistence.repository;

import cms.communicationservice.infrastructure.persistence.entity.EmailLogEntity;
import cms.communicationservice.infrastructure.persistence.entity.enums.DeliveryStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmailLogRepository {

    @Select("""
            SELECT id, recipient, subject, template_code, provider_message_id,
                   status, error_message, sent_at, created_at
            FROM email_logs
            WHERE id = #{id}
            """)
    Optional<EmailLogEntity> findById(UUID id);

    @Select("""
            SELECT id, recipient, subject, template_code, provider_message_id,
                   status, error_message, sent_at, created_at
            FROM email_logs
            WHERE recipient = #{recipient}
            ORDER BY created_at DESC
            """)
    List<EmailLogEntity> findByRecipient(String recipient);

    @Select("""
            SELECT id, recipient, subject, template_code, provider_message_id,
                   status, error_message, sent_at, created_at
            FROM email_logs
            WHERE status = #{status}
            ORDER BY created_at DESC
            """)
    List<EmailLogEntity> findByStatus(DeliveryStatus status);

    @Insert("""
            INSERT INTO email_logs (
                recipient, subject, template_code, provider_message_id,
                status, error_message, sent_at
            )
            VALUES (
                #{recipient}, #{subject}, #{templateCode}, #{providerMessageId},
                #{status}, #{errorMessage}, #{sentAt}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(EmailLogEntity emailLog);

    @Update("""
            UPDATE email_logs
            SET provider_message_id = #{providerMessageId},
                status = #{status},
                error_message = #{errorMessage},
                sent_at = #{sentAt}
            WHERE id = #{id}
            """)
    int updateDeliveryState(EmailLogEntity emailLog);

    @Delete("""
            DELETE FROM email_logs
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
