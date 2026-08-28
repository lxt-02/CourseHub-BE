CREATE TABLE permissions (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                             code VARCHAR(100) NOT NULL UNIQUE,
                             description VARCHAR(255),

                             created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE role_permissions (
                                  role_id UUID NOT NULL,
                                  permission_id UUID NOT NULL,

                                  assigned_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                  PRIMARY KEY (role_id, permission_id),

                                  CONSTRAINT fk_role_permissions_role
                                      FOREIGN KEY (role_id)
                                          REFERENCES roles(id)
                                          ON DELETE CASCADE,

                                  CONSTRAINT fk_role_permissions_permission
                                      FOREIGN KEY (permission_id)
                                          REFERENCES permissions(id)
                                          ON DELETE CASCADE
);

CREATE INDEX idx_role_permissions_permission_id
    ON role_permissions(permission_id);

INSERT INTO permissions (code, description)
VALUES
    ('users:read', 'View users'),
    ('users:write', 'Create, update, or delete users'),
    ('courses:read', 'View courses'),
    ('courses:write', 'Create, update, or delete courses'),
    ('enrollments:read', 'View enrollments'),
    ('enrollments:write', 'Create, update, or delete enrollments')
ON CONFLICT (code) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN (
    'users:read',
    'users:write',
    'courses:read',
    'courses:write',
    'enrollments:read',
    'enrollments:write'
)
WHERE r.code = 'ADMIN'
ON CONFLICT DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN (
    'courses:read',
    'courses:write',
    'enrollments:read'
)
WHERE r.code = 'MANAGER'
ON CONFLICT DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.code IN (
    'courses:read'
)
WHERE r.code = 'LEARNER'
ON CONFLICT DO NOTHING;
