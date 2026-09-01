package course.courseservice.domain.model.course.entity;

import course.courseservice.domain.model.course.enums.LessonType;
import course.courseservice.domain.model.course.exception.CourseDomainException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CourseModule {

    private final UUID id;
    private final UUID courseId;
    private String title;
    private String description;
    private int position;
    private final List<Lesson> lessons;
    private final Instant createdAt;
    private Instant updatedAt;

    private CourseModule(UUID id, UUID courseId, String title, String description, int position,
                         List<Lesson> lessons, Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id, "Module id must not be null");
        this.courseId = Objects.requireNonNull(courseId, "Course id must not be null");
        this.title = validateTitle(title);
        this.description = normalizeNullable(description);
        this.position = validatePosition(position);
        this.lessons = new ArrayList<>(lessons == null ? List.of() : lessons);
        ensureUniqueLessonPositions(this.lessons);
        this.createdAt = Objects.requireNonNull(createdAt, "Created time must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Updated time must not be null");
    }

    public static CourseModule create(UUID courseId, String title, int position) {
        Instant now = Instant.now();
        return new CourseModule(UUID.randomUUID(), courseId, title, null, position, List.of(), now, now);
    }

    public static CourseModule restore(UUID id, UUID courseId, String title, String description, int position,
                                       List<Lesson> lessons, Instant createdAt, Instant updatedAt) {
        return new CourseModule(id, courseId, title, description, position, lessons, createdAt, updatedAt);
    }

    public Lesson addLesson(String title, LessonType lessonType, int position) {
        ensureLessonPositionAvailable(position, null);
        Lesson lesson = Lesson.create(id, title, lessonType, position);
        lessons.add(lesson);
        sortLessons();
        updatedAt = Instant.now();
        return lesson;
    }

    public void removeLesson(UUID lessonId) {
        boolean removed = lessons.removeIf(lesson -> lesson.getId().equals(lessonId));
        if (!removed) {
            throw new CourseDomainException("Lesson not found: " + lessonId);
        }
        updatedAt = Instant.now();
    }

    public void moveLesson(UUID lessonId, int position) {
        Lesson lesson = findLesson(lessonId);
        ensureLessonPositionAvailable(position, lessonId);
        lesson.moveTo(position);
        sortLessons();
        updatedAt = Instant.now();
    }

    public void update(String title, String description, int position) {
        this.title = validateTitle(title);
        this.description = normalizeNullable(description);
        this.position = validatePosition(position);
        this.updatedAt = Instant.now();
    }

    private Lesson findLesson(UUID lessonId) {
        return lessons.stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .findFirst()
                .orElseThrow(() -> new CourseDomainException("Lesson not found: " + lessonId));
    }

    private void ensureLessonPositionAvailable(int position, UUID currentLessonId) {
        validatePosition(position);
        boolean duplicate = lessons.stream()
                .anyMatch(lesson -> lesson.getPosition() == position && !lesson.getId().equals(currentLessonId));
        if (duplicate) {
            throw new CourseDomainException("Duplicate lesson position: " + position);
        }
    }

    private void sortLessons() {
        lessons.sort(Comparator.comparingInt(Lesson::getPosition));
    }

    private static void ensureUniqueLessonPositions(List<Lesson> lessons) {
        long distinctPositions = lessons.stream().map(Lesson::getPosition).distinct().count();
        if (distinctPositions != lessons.size()) {
            throw new CourseDomainException("Lesson positions must be unique within a module");
        }
    }

    private static String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new CourseDomainException("Module title must not be blank");
        }
        String normalized = title.trim();
        if (normalized.length() > 255) {
            throw new CourseDomainException("Module title must not exceed 255 characters");
        }
        return normalized;
    }

    private static int validatePosition(int position) {
        if (position <= 0) {
            throw new CourseDomainException("Module position must be greater than zero");
        }
        return position;
    }

    private static String normalizeNullable(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    public UUID getId() { return id; }
    public UUID getCourseId() { return courseId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getPosition() { return position; }
    public List<Lesson> getLessons() { return List.copyOf(lessons); }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
