package course.courseservice.domain.model.category.aggregate;

import course.courseservice.domain.model.category.enums.CategoryStatus;
import course.courseservice.domain.model.course.exception.CourseDomainException;
import course.courseservice.domain.model.course.valueobject.Slug;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Category {

    private final UUID id;
    private String name;
    private Slug slug;
    private String description;
    private CategoryStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    private Category(UUID id, String name, Slug slug, String description, CategoryStatus status,
                     Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id, "Category id must not be null");
        this.name = validateName(name);
        this.slug = Objects.requireNonNull(slug, "Category slug must not be null");
        this.description = normalizeNullable(description);
        this.status = Objects.requireNonNull(status, "Category status must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "Created time must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Updated time must not be null");
    }

    public static Category create(String name, String description) {
        Instant now = Instant.now();
        return new Category(UUID.randomUUID(), name, Slug.fromTitle(name), description, CategoryStatus.ACTIVE, now, now);
    }

    public static Category restore(UUID id, String name, Slug slug, String description, CategoryStatus status,
                                   Instant createdAt, Instant updatedAt) {
        return new Category(id, name, slug, description, status, createdAt, updatedAt);
    }

    public void update(String name, Slug slug, String description) {
        this.name = validateName(name);
        this.slug = Objects.requireNonNull(slug, "Category slug must not be null");
        this.description = normalizeNullable(description);
        this.updatedAt = Instant.now();
    }

    public void activate() {
        this.status = CategoryStatus.ACTIVE;
        this.updatedAt = Instant.now();
    }

    public void deactivate() {
        this.status = CategoryStatus.INACTIVE;
        this.updatedAt = Instant.now();
    }

    private static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new CourseDomainException("Category name must not be blank");
        }
        String normalized = name.trim();
        if (normalized.length() > 150) {
            throw new CourseDomainException("Category name must not exceed 150 characters");
        }
        return normalized;
    }

    private static String normalizeNullable(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public Slug getSlug() { return slug; }
    public String getDescription() { return description; }
    public CategoryStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
