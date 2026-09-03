-- =============================================================================
-- V2__seed_test_data.sql
-- Seed initial realistic categories, courses, modules, and lessons for testing.
-- =============================================================================

-- 1. Insert Categories
INSERT INTO categories (id, name, slug, description, status) VALUES
('11111111-1111-1111-1111-111111111111', 'Lập trình Backend', 'lap-trinh-backend', 'Các khóa học về Java, Spring Boot, Microservices, Node.js, Go và cơ sở dữ liệu', 'ACTIVE'),
('22222222-2222-2222-2222-222222222222', 'Lập trình Frontend', 'lap-trinh-frontend', 'Các khóa học về React, Vue, Angular, HTML/CSS và UI/UX', 'ACTIVE'),
('33333333-3333-3333-3333-333333333333', 'DevOps & Cloud', 'devops-and-cloud', 'Khóa học về Docker, Kubernetes, CI/CD, AWS và Google Cloud', 'ACTIVE'),
('44444444-4444-4444-4444-444444444444', 'Khoa học Dữ liệu & AI', 'khoa-hoc-du-lieu-ai', 'Khóa học Python, Machine Learning, Deep Learning và Data Analysis', 'ACTIVE')
ON CONFLICT (id) DO NOTHING;

-- 2. Insert Sample Courses
INSERT INTO courses (
    id, manager_id, title, slug, short_description, description,
    thumbnail_url, price, difficulty_level, status, published_at, created_at, updated_at
) VALUES
(
    'a1111111-1111-1111-1111-111111111111',
    '99999999-9999-9999-9999-999999999999',
    'Khóa học Java Spring Boot Microservices từ Chuyên gia',
    'khoa-hoc-java-spring-boot-microservices-tu-chuyen-gia',
    'Làm chủ kiến trúc Microservices với Spring Boot 3, Spring Cloud, Kafka và Elasticsearch.',
    'Trong khóa học này, bạn sẽ học cách thiết kế và triển khai hệ thống Microservices thực tế với Spring Boot, Spring Cloud Gateway, OpenFeign, Kafka, Redis và Elasticsearch. Khóa học hướng dẫn bài bản từ Clean Architecture đến CQRS Pattern.',
    'https://cdn.coursehub.com/thumbnails/spring-boot-microservices.jpg',
    499000.00,
    'INTERMEDIATE',
    'PUBLISHED',
    CURRENT_TIMESTAMP - INTERVAL '10 days',
    CURRENT_TIMESTAMP - INTERVAL '15 days',
    CURRENT_TIMESTAMP - INTERVAL '10 days'
),
(
    'a2222222-2222-2222-2222-222222222222',
    '99999999-9999-9999-9999-999999999999',
    'Lập trình ReactJS & Next.js 14 Toàn Tập',
    'lap-trinh-reactjs-nextjs-14-toan-tap',
    'Xây dựng các ứng dụng Web hiện đại, chuẩn SEO với ReactJS, Next.js App Router và Tailwind CSS.',
    'Khóa học giúp bạn thành thạo React Hooks, Server Components, State Management với Redux Toolkit, Zod validation và tích hợp REST API / GraphQL.',
    'https://cdn.coursehub.com/thumbnails/react-nextjs.jpg',
    350000.00,
    'BEGINNER',
    'PUBLISHED',
    CURRENT_TIMESTAMP - INTERVAL '5 days',
    CURRENT_TIMESTAMP - INTERVAL '7 days',
    CURRENT_TIMESTAMP - INTERVAL '5 days'
),
(
    'a3333333-3333-3333-3333-333333333333',
    '99999999-9999-9999-9999-999999999999',
    'Docker & Kubernetes Thực Chiến cho Developer',
    'docker-kubernetes-thuc-chien-cho-developer',
    'Triển khai ứng dụng Containerized chuyên nghiệp với Docker Compose & K8s Cluster.',
    'Học cách đóng gói ứng dụng Node.js, Java, Python thành các Docker image tối ưu. Quản lý container với Kubernetes, Helm Charts và thiết lập CI/CD Pipeline với GitHub Actions.',
    'https://cdn.coursehub.com/thumbnails/docker-k8s.jpg',
    599000.00,
    'ADVANCED',
    'PUBLISHED',
    CURRENT_TIMESTAMP - INTERVAL '2 days',
    CURRENT_TIMESTAMP - INTERVAL '3 days',
    CURRENT_TIMESTAMP - INTERVAL '2 days'
),
(
    'a4444444-4444-4444-4444-444444444444',
    '99999999-9999-9999-9999-999999999999',
    'Python cho Nhập Môn Khoa Học Dữ Liệu',
    'python-cho-nhap-mon-khoa-hoc-du-lieu',
    'Học lập trình Python từ cơ bản đến phân tích dữ liệu thực tế với Pandas, NumPy và Matplotlib.',
    'Khóa học dành cho người mới bắt đầu lập trình Python. Bạn sẽ nắm chắc cú pháp ngôn ngữ, cấu trúc dữ liệu, và thực hành xử lý bộ dữ liệu hàng triệu bản ghi.',
    'https://cdn.coursehub.com/thumbnails/python-data-science.jpg',
    0.00,
    'BEGINNER',
    'PUBLISHED',
    CURRENT_TIMESTAMP - INTERVAL '1 day',
    CURRENT_TIMESTAMP - INTERVAL '1 day',
    CURRENT_TIMESTAMP - INTERVAL '1 day'
),
(
    'a5555555-5555-5555-5555-555555555555',
    '99999999-9999-9999-9999-999999999999',
    'Khóa Học Nháp Golang Microservices',
    'khoa-hoc-nhap-golang-microservices',
    'Khóa học thử nghiệm phát triển Go gRPC microservices.',
    'Nội dung đang được biên soạn bởi giảng viên.',
    'https://cdn.coursehub.com/thumbnails/golang-draft.jpg',
    199000.00,
    'INTERMEDIATE',
    'DRAFT',
    NULL,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON CONFLICT (id) DO NOTHING;

-- 3. Associate Courses with Categories
INSERT INTO course_categories (course_id, category_id) VALUES
('a1111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111'), -- Java -> Backend
('a2222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222'), -- React -> Frontend
('a3333333-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333'), -- Docker -> DevOps
('a3333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111'), -- Docker -> Backend
('a4444444-4444-4444-4444-444444444444', '44444444-4444-4444-4444-444444444444'), -- Python -> AI & Data
('a5555555-5555-5555-5555-555555555555', '11111111-1111-1111-1111-111111111111')  -- Golang -> Backend
ON CONFLICT (course_id, category_id) DO NOTHING;

-- 4. Insert Modules for Course 1 (Java Spring Boot)
INSERT INTO modules (id, course_id, title, description, position, created_at, updated_at) VALUES
(
    'b1111111-1111-1111-1111-111111111111',
    'a1111111-1111-1111-1111-111111111111',
    'Chương 1: Tổng quan Kiến trúc Microservices',
    'Giới thiệu tổng quan về Monolith vs Microservices và kiến trúc tổng thể dự án.',
    1,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'b2222222-2222-2222-2222-222222222222',
    'a1111111-1111-1111-1111-111111111111',
    'Chương 2: Thiết kế REST API & Kafka Event Sync',
    'Hướng dẫn triển khai REST Controller và cơ chế Event-Driven với Apache Kafka.',
    2,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
)
ON CONFLICT (id) DO NOTHING;

-- 5. Insert Lessons for Module 1 & 2
INSERT INTO lessons (
    id, module_id, title, description, lesson_type, content, video_url, duration_seconds, position, is_preview, is_required
) VALUES
(
    'c1111111-1111-1111-1111-111111111111',
    'b1111111-1111-1111-1111-111111111111',
    'Bài 1: Phân tích Monolithic vs Microservices Architecture',
    'So sánh chi tiết ưu nhược điểm giữa hai mô hình kiến trúc.',
    'VIDEO',
    'Nội dung tổng quan kiến trúc phần mềm hiện đại...',
    'https://cdn.coursehub.com/videos/lesson1.mp4',
    720,
    1,
    TRUE,
    TRUE
),
(
    'c2222222-2222-2222-2222-222222222222',
    'b1111111-1111-1111-1111-111111111111',
    'Bài 2: Cài đặt và cấu hình Spring Boot 3',
    'Tạo dự án Spring Boot với Maven, Lombok, và PostgreSQL driver.',
    'TEXT',
    'Hướng dẫn các bước cấu hình pom.xml và application.yaml...',
    NULL,
    300,
    2,
    FALSE,
    TRUE
),
(
    'c3333333-3333-3333-3333-333333333333',
    'b2222222-2222-2222-2222-222222222222',
    'Bài 3: Tích hợp Kafka Producer trong Spring Boot',
    'Cấu hình KafkaTemplate và phát Event sản phẩm sang Kafka topic.',
    'VIDEO',
    'Bài thực hành viết Kafka Publisher Adapter...',
    'https://cdn.coursehub.com/videos/lesson3.mp4',
    950,
    1,
    FALSE,
    TRUE
)
ON CONFLICT (id) DO NOTHING;
