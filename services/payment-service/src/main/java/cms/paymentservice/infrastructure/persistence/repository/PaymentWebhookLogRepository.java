package cms.paymentservice.infrastructure.persistence.repository;

import cms.paymentservice.infrastructure.persistence.entity.PaymentWebhookLogEntity;
import cms.paymentservice.infrastructure.persistence.entity.enums.WebhookProcessingStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentWebhookLogRepository {

    @Select("""
            SELECT id, provider, external_event_id, payload::text AS payload,
                   processing_status, received_at, processed_at, error_message
            FROM payment_webhook_logs
            WHERE id = #{id}
            """)
    Optional<PaymentWebhookLogEntity> findById(UUID id);

    @Select("""
            SELECT id, provider, external_event_id, payload::text AS payload,
                   processing_status, received_at, processed_at, error_message
            FROM payment_webhook_logs
            WHERE provider = #{provider}
              AND external_event_id = #{externalEventId}
            """)
    Optional<PaymentWebhookLogEntity> findByProviderAndExternalEventId(@Param("provider") String provider,
                                                                       @Param("externalEventId") String externalEventId);

    @Select("""
            SELECT id, provider, external_event_id, payload::text AS payload,
                   processing_status, received_at, processed_at, error_message
            FROM payment_webhook_logs
            WHERE processing_status = #{processingStatus}
            ORDER BY received_at ASC
            """)
    List<PaymentWebhookLogEntity> findByProcessingStatus(WebhookProcessingStatus processingStatus);

    @Insert("""
            INSERT INTO payment_webhook_logs (
                provider, external_event_id, payload, processing_status,
                received_at, processed_at, error_message
            )
            VALUES (
                #{provider}, #{externalEventId}, CAST(#{payload} AS jsonb), #{processingStatus},
                #{receivedAt}, #{processedAt}, #{errorMessage}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(PaymentWebhookLogEntity webhookLog);

    @Update("""
            UPDATE payment_webhook_logs
            SET processing_status = #{processingStatus},
                processed_at = #{processedAt},
                error_message = #{errorMessage}
            WHERE id = #{id}
            """)
    int updateProcessingState(PaymentWebhookLogEntity webhookLog);

    @Delete("""
            DELETE FROM payment_webhook_logs
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
