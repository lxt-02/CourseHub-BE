package course.courseservice.domain.model.course.entity;

import course.courseservice.domain.model.course.enums.LessonType;
import course.courseservice.domain.model.course.exception.CourseDomainException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Lesson {

    private final UUID id;
    private final UUID moduleId;
    private String title;
    private String description;
    private LessonType lessonType;
    private String content;
    private String videoUrl;
    private String documentUrl;
    private Integer durationSeconds;
    private int position;
    private boolean preview;
    private boolean required;
    private final Instant createdAt;
    private Instant updatedAt;

    private Lesson(UUID id, UUID moduleId, String title, String description, LessonType lessonType,
                   String content, String videoUrl, String documentUrl, Integer durationSeconds,
                   int position, boolean preview, boolean required, Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id, "Lesson id must not be null");
        this.moduleId = Objects.requireNonNull(moduleId, "Module id must not be null");
        this.title = validateTitle(title);
        this.description = normalizeNullable(description);
        this.lessonType = Objects.requireNonNull(lessonType, "Lesson type must not be null");
        this.content = normalizeNullable(content);
        this.videoUrl = normalizeNullable(videoUrl);
        this.documentUrl = normalizeNullable(documentUrl);
        this.durationSeconds = validateDuration(durationSeconds);
        this.position = validatePosition(position);
        this.preview = preview;
        this.required = required;
        this.createdAt = Objects.requireNonNull(createdAt, "Created time must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Updated time must not be null");
    }

    public static Lesson create(UUID moduleId, String title, LessonType lessonType, int position) {
        Instant now = Instant.now();
        return new Lesson(UUID.randomUUID(), moduleId, title, null, lessonType,
                null, null, null, null, position, false, true, now, now);
    }

    public static Lesson restore(UUID id, UUID moduleId, String title, String description, LessonType lessonType,
                                 String content, String videoUrl, String documentUrl, Integer durationSeconds,
                                 int position, boolean preview, boolean required, Instant createdAt, Instant updatedAt) {
        return new Lesson(id, moduleId, title, description, lessonType, content, videoUrl, documentUrl,
                durationSeconds, position, preview, required, createdAt, updatedAt);
    }

    public void updateContent(String title, String description, LessonType lessonType, String content,
                              String videoUrl, String documentUrl, Integer durationSeconds) {
        this.title = validateTitle(title);
        this.description = normalizeNullable(description);
        this.lessonType = Objects.requireNonNull(lessonType, "Lesson type must not be null");
        this.content = normalizeNullable(content);
        this.videoUrl = normalizeNullable(videoUrl);
        this.documentUrl = normalizeNullable(documentUrl);
        this.durationSeconds = validateDuration(durationSeconds);
        this.updatedAt = Instant.now();
    }

    public void moveTo(int position) {
        this.position = validatePosition(position);
        this.updatedAt = Instant.now();
    }

    public void setPreview(boolean preview) {
        this.preview = preview;
        this.updatedAt = Instant.now();
    }

    public void setRequired(boolean required) {
        this.required = required;
        this.updatedAt = Instant.now();
    }

    private static String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new CourseDomainException("Lesson title must not be blank");
        }
        String normalized = title.trim();
        if (normalized.length() > 255) {
            throw new CourseDomainException("Lesson title must not exceed 255 characters");
        }
        return normalized;
    }

    private static Integer validateDuration(Integer durationSeconds) {
        if (durationSeconds != null && durationSeconds < 0) {
            throw new CourseDomainException("Lesson duration must not be negative");
        }
        return durationSeconds;
    }

    private static int validatePosition(int position) {
        if (position <= 0) {
            throw new CourseDomainException("Lesson position must be greater than zero");
        }
        return position;
    }

    private static String normalizeNullable(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    public UUID getId() { return id; }
    public UUID getModuleId() { return moduleId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LessonType getLessonType() { return lessonType; }
    public String getContent() { return content; }
    public String getVideoUrl() { return videoUrl; }
    public String getDocumentUrl() { return documentUrl; }
    public Integer getDurationSeconds() { return durationSeconds; }
    public int getPosition() { return position; }
    public boolean isPreview() { return preview; }
    public boolean isRequired() { return required; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
