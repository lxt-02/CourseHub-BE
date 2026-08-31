CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE courses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    manager_id UUID NOT NULL,

    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,

    short_description VARCHAR(500),
    description TEXT,

    thumbnail_url TEXT,

    price NUMERIC(15,2) NOT NULL DEFAULT 0,

    difficulty_level VARCHAR(30),
    status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',

    published_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_course_price
        CHECK (price >= 0),

    CONSTRAINT chk_course_status
        CHECK (status IN ('DRAFT', 'PUBLISHED', 'ARCHIVED')),

    CONSTRAINT chk_course_difficulty
        CHECK (
            difficulty_level IS NULL
            OR difficulty_level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED')
        )
);

CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(150) NOT NULL UNIQUE,
    slug VARCHAR(150) NOT NULL UNIQUE,
    description VARCHAR(500),

    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_category_status
        CHECK (status IN ('ACTIVE', 'INACTIVE'))
);

CREATE TABLE course_categories (
    course_id UUID NOT NULL,
    category_id UUID NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (course_id, category_id),

    CONSTRAINT fk_course_categories_course
        FOREIGN KEY (course_id)
        REFERENCES courses(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_course_categories_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
        ON DELETE CASCADE
);

CREATE TABLE modules (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    course_id UUID NOT NULL,

    title VARCHAR(255) NOT NULL,
    description TEXT,

    position INTEGER NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_modules_course
        FOREIGN KEY (course_id)
        REFERENCES courses(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_module_position
        UNIQUE (course_id, position),

    CONSTRAINT chk_module_position
        CHECK (position > 0)
);

CREATE TABLE lessons (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    module_id UUID NOT NULL,

    title VARCHAR(255) NOT NULL,
    description TEXT,

    lesson_type VARCHAR(30) NOT NULL,

    content TEXT,
    video_url TEXT,
    document_url TEXT,

    duration_seconds INTEGER,
    position INTEGER NOT NULL,

    is_preview BOOLEAN NOT NULL DEFAULT FALSE,
    is_required BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_lessons_module
        FOREIGN KEY (module_id)
        REFERENCES modules(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_lesson_position
        UNIQUE (module_id, position),

    CONSTRAINT chk_lesson_type
        CHECK (lesson_type IN ('VIDEO', 'TEXT', 'DOCUMENT')),

    CONSTRAINT chk_lesson_duration
        CHECK (duration_seconds IS NULL OR duration_seconds >= 0),

    CONSTRAINT chk_lesson_position
        CHECK (position > 0)
);

CREATE TABLE course_assets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    course_id UUID NOT NULL,

    asset_type VARCHAR(30) NOT NULL,
    asset_url TEXT NOT NULL,

    file_name VARCHAR(255),
    file_size BIGINT,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_course_assets_course
        FOREIGN KEY (course_id)
        REFERENCES courses(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_course_asset_type
        CHECK (
            asset_type IN (
                'THUMBNAIL',
                'VIDEO',
                'DOCUMENT',
                'ATTACHMENT'
            )
        ),

    CONSTRAINT chk_course_asset_size
        CHECK (file_size IS NULL OR file_size >= 0)
);

CREATE INDEX idx_courses_manager_id
    ON courses(manager_id);

CREATE INDEX idx_courses_status
    ON courses(status);

CREATE INDEX idx_courses_published_at
    ON courses(published_at);

CREATE INDEX idx_modules_course_id
    ON modules(course_id);

CREATE INDEX idx_lessons_module_id
    ON lessons(module_id);

CREATE INDEX idx_course_assets_course_id
    ON course_assets(course_id);
