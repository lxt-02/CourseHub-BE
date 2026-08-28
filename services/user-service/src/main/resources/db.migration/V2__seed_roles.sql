INSERT INTO roles (code, name, description)
VALUES
    ('LEARNER', 'Learner', 'Default learner role'),
    ('MANAGER', 'Manager', 'Course manager role'),
    ('ADMIN', 'Admin', 'System administrator role')
    ON CONFLICT (code) DO NOTHING;