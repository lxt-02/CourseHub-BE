CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE notifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    recipient_user_id UUID NOT NULL,

    type VARCHAR(50) NOT NULL,

    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,

    reference_type VARCHAR(50),
    reference_id UUID,

    read_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notification_deliveries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    notification_id UUID NOT NULL,

    channel VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

    retry_count INTEGER NOT NULL DEFAULT 0,

    sent_at TIMESTAMPTZ,
    next_retry_at TIMESTAMPTZ,

    error_message TEXT,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_notification_delivery_notification
        FOREIGN KEY (notification_id)
        REFERENCES notifications(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_notification_channel
        CHECK (
            channel IN (
                'IN_APP',
                'EMAIL',
                'WEBSOCKET'
            )
        ),

    CONSTRAINT chk_notification_delivery_status
        CHECK (
            status IN (
                'PENDING',
                'SENT',
                'FAILED'
            )
        ),

    CONSTRAINT chk_notification_retry_count
        CHECK (retry_count >= 0)
);

CREATE TABLE email_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    recipient VARCHAR(255) NOT NULL,

    subject VARCHAR(500) NOT NULL,
    template_code VARCHAR(100),

    provider_message_id VARCHAR(255),

    status VARCHAR(30) NOT NULL,

    error_message TEXT,

    sent_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_email_log_status
        CHECK (
            status IN (
                'PENDING',
                'SENT',
                'FAILED'
            )
        )
);

CREATE TABLE processed_events (
    event_id UUID NOT NULL,
    consumer_name VARCHAR(150) NOT NULL,

    event_type VARCHAR(150) NOT NULL,

    processed_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (event_id, consumer_name)
);

CREATE INDEX idx_notifications_recipient
    ON notifications(recipient_user_id);

CREATE INDEX idx_notifications_read
    ON notifications(recipient_user_id, read_at);

CREATE INDEX idx_delivery_pending
    ON notification_deliveries(status, next_retry_at);

CREATE INDEX idx_email_status
    ON email_logs(status);
