CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE coupons (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    code VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,

    discount_type VARCHAR(30) NOT NULL,
    discount_value NUMERIC(15,2) NOT NULL,

    max_discount_amount NUMERIC(15,2),
    minimum_order_amount NUMERIC(15,2),

    usage_limit INTEGER,
    used_count INTEGER NOT NULL DEFAULT 0,

    start_at TIMESTAMPTZ,
    end_at TIMESTAMPTZ,

    status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',

    created_by UUID,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_coupon_discount_type
        CHECK (
            discount_type IN (
                'PERCENTAGE',
                'FIXED_AMOUNT'
            )
        ),

    CONSTRAINT chk_coupon_status
        CHECK (
            status IN (
                'DRAFT',
                'ACTIVE',
                'EXPIRED',
                'DISABLED'
            )
        ),

    CONSTRAINT chk_coupon_discount_value
        CHECK (discount_value > 0),

    CONSTRAINT chk_coupon_max_discount
        CHECK (
            max_discount_amount IS NULL
            OR max_discount_amount >= 0
        ),

    CONSTRAINT chk_coupon_minimum_order
        CHECK (
            minimum_order_amount IS NULL
            OR minimum_order_amount >= 0
        ),

    CONSTRAINT chk_coupon_usage_limit
        CHECK (
            usage_limit IS NULL
            OR usage_limit > 0
        ),

    CONSTRAINT chk_coupon_used_count
        CHECK (used_count >= 0),

    CONSTRAINT chk_coupon_period
        CHECK (
            end_at IS NULL
            OR start_at IS NULL
            OR end_at > start_at
        )
);

CREATE TABLE coupon_courses (
    coupon_id UUID NOT NULL,
    course_id UUID NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (coupon_id, course_id),

    CONSTRAINT fk_coupon_courses_coupon
        FOREIGN KEY (coupon_id)
        REFERENCES coupons(id)
        ON DELETE CASCADE
);

CREATE TABLE coupon_reservations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    coupon_id UUID NOT NULL,

    learner_id UUID NOT NULL,
    enrollment_id UUID NOT NULL,

    status VARCHAR(30) NOT NULL DEFAULT 'RESERVED',

    reserved_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMPTZ,

    confirmed_at TIMESTAMPTZ,
    released_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_coupon_reservation_coupon
        FOREIGN KEY (coupon_id)
        REFERENCES coupons(id),

    CONSTRAINT chk_coupon_reservation_status
        CHECK (
            status IN (
                'RESERVED',
                'CONFIRMED',
                'RELEASED',
                'EXPIRED'
            )
        )
);

CREATE TABLE coupon_usages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    coupon_id UUID NOT NULL,

    learner_id UUID NOT NULL,
    enrollment_id UUID NOT NULL,

    discount_amount NUMERIC(15,2) NOT NULL,

    used_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_coupon_usage_coupon
        FOREIGN KEY (coupon_id)
        REFERENCES coupons(id),

    CONSTRAINT uk_coupon_usage
        UNIQUE (coupon_id, learner_id, enrollment_id),

    CONSTRAINT chk_coupon_usage_discount
        CHECK (discount_amount >= 0)
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

    CONSTRAINT chk_promotion_outbox_status
        CHECK (status IN ('PENDING', 'PUBLISHED', 'FAILED'))
);

CREATE TABLE processed_events (
    event_id UUID NOT NULL,
    consumer_name VARCHAR(150) NOT NULL,

    event_type VARCHAR(150) NOT NULL,

    processed_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (event_id, consumer_name)
);

CREATE INDEX idx_coupons_code
    ON coupons(code);

CREATE INDEX idx_coupons_status
    ON coupons(status);

CREATE INDEX idx_coupon_reservations_enrollment
    ON coupon_reservations(enrollment_id);

CREATE INDEX idx_coupon_reservations_status_expiry
    ON coupon_reservations(status, expires_at);

CREATE INDEX idx_coupon_usages_learner
    ON coupon_usages(learner_id);

CREATE INDEX idx_promotion_outbox_pending
    ON outbox_events(status, created_at);
