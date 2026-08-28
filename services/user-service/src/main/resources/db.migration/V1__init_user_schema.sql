
CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255),

                       full_name VARCHAR(150) NOT NULL,
                       avatar_url TEXT,
                       bio VARCHAR(500),

                       status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
                       email_verified BOOLEAN NOT NULL DEFAULT FALSE,

                       created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT chk_users_status
                           CHECK (status IN ('ACTIVE', 'INACTIVE', 'BANNED'))
);

CREATE TABLE roles (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                       code VARCHAR(50) NOT NULL UNIQUE,
                       name VARCHAR(100) NOT NULL,
                       description VARCHAR(255),

                       created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
                            user_id UUID NOT NULL,
                            role_id UUID NOT NULL,

                            assigned_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            PRIMARY KEY (user_id, role_id),

                            CONSTRAINT fk_user_roles_user
                                FOREIGN KEY (user_id)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE,

                            CONSTRAINT fk_user_roles_role
                                FOREIGN KEY (role_id)
                                    REFERENCES roles(id)
                                    ON DELETE CASCADE
);

CREATE TABLE oauth_accounts (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                user_id UUID NOT NULL,

                                provider VARCHAR(50) NOT NULL,
                                provider_user_id VARCHAR(255) NOT NULL,
                                provider_email VARCHAR(255),

                                created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                CONSTRAINT fk_oauth_accounts_user
                                    FOREIGN KEY (user_id)
                                        REFERENCES users(id)
                                        ON DELETE CASCADE,

                                CONSTRAINT uk_oauth_provider_user
                                    UNIQUE (provider, provider_user_id)
);

CREATE INDEX idx_users_status
    ON users(status);

CREATE INDEX idx_user_roles_role_id
    ON user_roles(role_id);

CREATE INDEX idx_oauth_accounts_user_id
    ON oauth_accounts(user_id);