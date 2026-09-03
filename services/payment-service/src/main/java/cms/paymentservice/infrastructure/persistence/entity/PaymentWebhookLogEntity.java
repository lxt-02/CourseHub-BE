package cms.paymentservice.infrastructure.persistence.entity;

import cms.paymentservice.infrastructure.persistence.entity.enums.WebhookProcessingStatus;

import java.time.Instant;
import java.util.UUID;

public class PaymentWebhookLogEntity {

    private UUID id;
    private String provider;
    private String externalEventId;
    private String payload;
    private WebhookProcessingStatus processingStatus = WebhookProcessingStatus.RECEIVED;
    private Instant receivedAt;
    private Instant processedAt;
    private String errorMessage;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getExternalEventId() { return externalEventId; }
    public void setExternalEventId(String externalEventId) { this.externalEventId = externalEventId; }
    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
    public WebhookProcessingStatus getProcessingStatus() { return processingStatus; }
    public void setProcessingStatus(WebhookProcessingStatus processingStatus) { this.processingStatus = processingStatus; }
    public Instant getReceivedAt() { return receivedAt; }
    public void setReceivedAt(Instant receivedAt) { this.receivedAt = receivedAt; }
    public Instant getProcessedAt() { return processedAt; }
    public void setProcessedAt(Instant processedAt) { this.processedAt = processedAt; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
