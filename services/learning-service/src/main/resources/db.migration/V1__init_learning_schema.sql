CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE lesson_progress (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    learner_id UUID NOT NULL,
    course_id UUID NOT NULL,
    lesson_id UUID NOT NULL,

    watched_seconds INTEGER NOT NULL DEFAULT 0,
    progress_percent NUMERIC(5,2) NOT NULL DEFAULT 0,

    status VARCHAR(30) NOT NULL DEFAULT 'NOT_STARTED',

    started_at TIMESTAMPTZ,
    last_accessed_at TIMESTAMPTZ,
    completed_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_lesson_progress
        UNIQUE (learner_id, lesson_id),

    CONSTRAINT chk_lesson_progress_status
        CHECK (
            status IN (
                'NOT_STARTED',
                'IN_PROGRESS',
                'COMPLETED'
            )
        ),

    CONSTRAINT chk_lesson_progress_percent
        CHECK (
            progress_percent >= 0
            AND progress_percent <= 100
        ),

    CONSTRAINT chk_lesson_watched_seconds
        CHECK (watched_seconds >= 0)
);

CREATE TABLE course_progress (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    learner_id UUID NOT NULL,
    course_id UUID NOT NULL,

    total_lessons INTEGER NOT NULL DEFAULT 0,
    completed_lessons INTEGER NOT NULL DEFAULT 0,

    progress_percent NUMERIC(5,2) NOT NULL DEFAULT 0,

    status VARCHAR(30) NOT NULL DEFAULT 'NOT_STARTED',

    started_at TIMESTAMPTZ,
    completed_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_course_progress
        UNIQUE (learner_id, course_id),

    CONSTRAINT chk_course_progress_status
        CHECK (
            status IN (
                'NOT_STARTED',
                'IN_PROGRESS',
                'COMPLETED'
            )
        ),

    CONSTRAINT chk_course_progress_percent
        CHECK (
            progress_percent >= 0
            AND progress_percent <= 100
        ),

    CONSTRAINT chk_course_progress_counts
        CHECK (
            total_lessons >= 0
            AND completed_lessons >= 0
            AND completed_lessons <= total_lessons
        )
);

CREATE TABLE certificates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    learner_id UUID NOT NULL,
    course_id UUID NOT NULL,

    certificate_code VARCHAR(100) NOT NULL UNIQUE,
    certificate_url TEXT,

    issued_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_certificate_learner_course
        UNIQUE (learner_id, course_id)
);

CREATE TABLE learning_activities (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    learner_id UUID NOT NULL,
    course_id UUID NOT NULL,
    lesson_id UUID,

    activity_type VARCHAR(50) NOT NULL,

    metadata JSONB,

    occurred_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
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

    CONSTRAINT chk_learning_outbox_status
        CHECK (status IN ('PENDING', 'PUBLISHED', 'FAILED'))
);

CREATE TABLE processed_events (
    event_id UUID NOT NULL,
    consumer_name VARCHAR(150) NOT NULL,

    event_type VARCHAR(150) NOT NULL,

    processed_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (event_id, consumer_name)
);

CREATE INDEX idx_lesson_progress_learner
    ON lesson_progress(learner_id);

CREATE INDEX idx_lesson_progress_course
    ON lesson_progress(course_id);

CREATE INDEX idx_course_progress_learner
    ON course_progress(learner_id);

CREATE INDEX idx_course_progress_course
    ON course_progress(course_id);

CREATE INDEX idx_learning_activities_learner
    ON learning_activities(learner_id);

CREATE INDEX idx_learning_activities_course
    ON learning_activities(course_id);

CREATE INDEX idx_learning_outbox_pending
    ON outbox_events(status, created_at);
