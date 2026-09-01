CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE enrollments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    learner_id UUID NOT NULL,
    course_id UUID NOT NULL,
    payment_id UUID,

    status VARCHAR(30) NOT NULL,

    enrolled_at TIMESTAMPTZ,
    activated_at TIMESTAMPTZ,
    cancelled_at TIMESTAMPTZ,
    revoked_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_enrollment_learner_course
        UNIQUE (learner_id, course_id),

    CONSTRAINT chk_enrollment_status
        CHECK (
            status IN (
                'PENDING_PAYMENT',
                'ACTIVE',
                'CANCELLED',
                'REVOKED'
            )
        )
);

CREATE TABLE enrollment_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    enrollment_id UUID NOT NULL,

    old_status VARCHAR(30),
    new_status VARCHAR(30) NOT NULL,

    reason VARCHAR(500),

    changed_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_enrollment_history_enrollment
        FOREIGN KEY (enrollment_id)
        REFERENCES enrollments(id)
        ON DELETE CASCADE
);

CREATE TABLE saga_instances (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    saga_type VARCHAR(100) NOT NULL,

    enrollment_id UUID NOT NULL,

    current_step VARCHAR(100),
    status VARCHAR(30) NOT NULL,

    payload JSONB,
    failure_reason TEXT,

    started_at TIMESTAMPTZ,
    completed_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_saga_enrollment
        FOREIGN KEY (enrollment_id)
        REFERENCES enrollments(id),

    CONSTRAINT chk_saga_status
        CHECK (
            status IN (
                'STARTED',
                'PROCESSING',
                'COMPLETED',
                'COMPENSATING',
                'COMPENSATED',
                'FAILED'
            )
        )
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

    CONSTRAINT chk_outbox_status
        CHECK (status IN ('PENDING', 'PUBLISHED', 'FAILED')),

    CONSTRAINT chk_outbox_retry_count
        CHECK (retry_count >= 0)
);

CREATE TABLE processed_events (
    event_id UUID NOT NULL,
    consumer_name VARCHAR(150) NOT NULL,

    event_type VARCHAR(150) NOT NULL,

    processed_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (event_id, consumer_name)
);

CREATE INDEX idx_enrollments_learner_id
    ON enrollments(learner_id);

CREATE INDEX idx_enrollments_course_id
    ON enrollments(course_id);

CREATE INDEX idx_enrollments_status
    ON enrollments(status);

CREATE INDEX idx_saga_status
    ON saga_instances(status);

CREATE INDEX idx_outbox_pending
    ON outbox_events(status, created_at);
