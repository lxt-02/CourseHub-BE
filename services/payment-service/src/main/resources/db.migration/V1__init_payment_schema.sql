CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE payments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    enrollment_id UUID NOT NULL,
    learner_id UUID NOT NULL,
    course_id UUID NOT NULL,

    currency VARCHAR(10) NOT NULL DEFAULT 'VND',

    original_amount NUMERIC(15,2) NOT NULL,
    discount_amount NUMERIC(15,2) NOT NULL DEFAULT 0,
    final_amount NUMERIC(15,2) NOT NULL,

    transaction_reference VARCHAR(100) NOT NULL UNIQUE,

    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

    expires_at TIMESTAMPTZ,
    paid_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_payment_status
        CHECK (
            status IN (
                'PENDING',
                'SUCCESS',
                'FAILED',
                'EXPIRED'
            )
        ),

    CONSTRAINT chk_payment_original_amount
        CHECK (original_amount >= 0),

    CONSTRAINT chk_payment_discount_amount
        CHECK (discount_amount >= 0),

    CONSTRAINT chk_payment_final_amount
        CHECK (final_amount >= 0),

    CONSTRAINT chk_payment_discount_not_exceed_original
        CHECK (discount_amount <= original_amount)
);

CREATE TABLE payment_transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    payment_id UUID NOT NULL,

    provider VARCHAR(50) NOT NULL,
    external_transaction_id VARCHAR(255),

    amount NUMERIC(15,2),
    currency VARCHAR(10) NOT NULL DEFAULT 'VND',

    status VARCHAR(30),

    transaction_time TIMESTAMPTZ,

    raw_response JSONB,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payment_transactions_payment
        FOREIGN KEY (payment_id)
        REFERENCES payments(id),

    CONSTRAINT chk_payment_transaction_amount
        CHECK (amount IS NULL OR amount >= 0)
);

CREATE TABLE payment_webhook_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    provider VARCHAR(50) NOT NULL,
    external_event_id VARCHAR(255),

    payload JSONB NOT NULL,

    processing_status VARCHAR(30) NOT NULL DEFAULT 'RECEIVED',

    received_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMPTZ,

    error_message TEXT,

    CONSTRAINT uk_payment_webhook_event
        UNIQUE (provider, external_event_id),

    CONSTRAINT chk_payment_webhook_status
        CHECK (
            processing_status IN (
                'RECEIVED',
                'PROCESSED',
                'FAILED',
                'IGNORED'
            )
        )
);

CREATE TABLE payment_idempotency_keys (
    idempotency_key VARCHAR(255) PRIMARY KEY,

    payment_id UUID,

    request_hash VARCHAR(255),

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMPTZ,

    CONSTRAINT fk_payment_idempotency_payment
        FOREIGN KEY (payment_id)
        REFERENCES payments(id)
);

CREATE TABLE outbox_events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    aggregate_type VARCHAR(100) NOT NULL,
    aggregate_id UUID NOT NULL,

    event_type VARCHAR(150) NOT NULL,
    payload JSONB NOT NULL,

    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

    retry_count INTEGER NOT NULL DEFAULT 0,
    last_error TEXT,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published_at TIMESTAMPTZ,

    CONSTRAINT chk_payment_outbox_status
        CHECK (status IN ('PENDING', 'PUBLISHED', 'FAILED'))
);

CREATE TABLE processed_events (
    event_id UUID NOT NULL,
    consumer_name VARCHAR(150) NOT NULL,

    event_type VARCHAR(150) NOT NULL,

    processed_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (event_id, consumer_name)
);

CREATE INDEX idx_payments_enrollment_id
    ON payments(enrollment_id);

CREATE INDEX idx_payments_learner_id
    ON payments(learner_id);

CREATE INDEX idx_payments_course_id
    ON payments(course_id);

CREATE INDEX idx_payments_status
    ON payments(status);

CREATE INDEX idx_payments_expires_at
    ON payments(expires_at);

CREATE INDEX idx_payment_transactions_payment_id
    ON payment_transactions(payment_id);

CREATE INDEX idx_payment_transactions_external_id
    ON payment_transactions(external_transaction_id);

CREATE INDEX idx_payment_outbox_pending
    ON outbox_events(status, created_at);
