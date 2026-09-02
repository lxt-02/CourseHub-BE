package course.courseservice.domain.model.course.aggregate;

import course.courseservice.domain.model.course.entity.CourseAsset;
import course.courseservice.domain.model.course.entity.CourseModule;
import course.courseservice.domain.model.course.enums.CourseAssetType;
import course.courseservice.domain.model.course.enums.CourseDifficultyLevel;
import course.courseservice.domain.model.course.enums.CourseStatus;
import course.courseservice.domain.model.course.enums.LessonType;
import course.courseservice.domain.model.course.exception.CourseDomainException;
import course.courseservice.domain.model.course.valueobject.Money;
import course.courseservice.domain.model.course.valueobject.Slug;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Course {

    private final UUID id;
    private UUID managerId;
    private String title;
    private Slug slug;
    private String shortDescription;
    private String description;
    private String thumbnailUrl;
    private Money price;
    private CourseDifficultyLevel difficultyLevel;
    private CourseStatus status;
    private Instant publishedAt;
    private final Set<UUID> categoryIds;
    private final List<CourseModule> modules;
    private final List<CourseAsset> assets;
    private final Instant createdAt;
    private Instant updatedAt;

    private Course(UUID id, UUID managerId, String title, Slug slug, String shortDescription,
                   String description, String thumbnailUrl, Money price, CourseDifficultyLevel difficultyLevel,
                   CourseStatus status, Instant publishedAt, Set<UUID> categoryIds,
                   List<CourseModule> modules, List<CourseAsset> assets, Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id, "Course id must not be null");
        this.managerId = Objects.requireNonNull(managerId, "Manager id must not be null");
        this.title = validateTitle(title);
        this.slug = Objects.requireNonNull(slug, "Course slug must not be null");
        this.shortDescription = validateShortDescription(shortDescription);
        this.description = normalizeNullable(description);
        this.thumbnailUrl = normalizeNullable(thumbnailUrl);
        this.price = Objects.requireNonNull(price, "Course price must not be null");
        this.difficultyLevel = difficultyLevel;
        this.status = Objects.requireNonNull(status, "Course status must not be null");
        this.publishedAt = publishedAt;
        this.categoryIds = new LinkedHashSet<>(categoryIds == null ? Set.of() : categoryIds);
        this.modules = new ArrayList<>(modules == null ? List.of() : modules);
        this.assets = new ArrayList<>(assets == null ? List.of() : assets);
        ensureUniqueModulePositions(this.modules);
        this.createdAt = Objects.requireNonNull(createdAt, "Created time must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Updated time must not be null");
    }

    public static Course create(UUID managerId, String title, String shortDescription, String description,
                                Money price, CourseDifficultyLevel difficultyLevel) {
        Instant now = Instant.now();
        return new Course(UUID.randomUUID(), managerId, title, Slug.fromTitle(title), shortDescription,
                description, null, price == null ? Money.zero() : price, difficultyLevel, CourseStatus.DRAFT,
                null, Set.of(), List.of(), List.of(), now, now);
    }

    public static Course restore(UUID id, UUID managerId, String title, Slug slug, String shortDescription,
                                 String description, String thumbnailUrl, Money price,
                                 CourseDifficultyLevel difficultyLevel, CourseStatus status, Instant publishedAt,
                                 Set<UUID> categoryIds, List<CourseModule> modules, List<CourseAsset> assets,
                                 Instant createdAt, Instant updatedAt) {
        return new Course(id, managerId, title, slug, shortDescription, description, thumbnailUrl, price,
                difficultyLevel, status, publishedAt, categoryIds, modules, assets, createdAt, updatedAt);
    }

    public void updateDetails(String title, Slug slug, String shortDescription, String description,
                              String thumbnailUrl, Money price, CourseDifficultyLevel difficultyLevel) {
        ensureEditable();
        this.title = validateTitle(title);
        this.slug = Objects.requireNonNull(slug, "Course slug must not be null");
        this.shortDescription = validateShortDescription(shortDescription);
        this.description = normalizeNullable(description);
        this.thumbnailUrl = normalizeNullable(thumbnailUrl);
        this.price = Objects.requireNonNull(price, "Course price must not be null");
        this.difficultyLevel = difficultyLevel;
        this.updatedAt = Instant.now();
    }

    public CourseModule addModule(String title, int position) {
        ensureEditable();
        ensureModulePositionAvailable(position, null);
        CourseModule module = CourseModule.create(id, title, position);
        modules.add(module);
        sortModules();
        updatedAt = Instant.now();
        return module;
    }

    public void removeModule(UUID moduleId) {
        ensureEditable();
        boolean removed = modules.removeIf(module -> module.getId().equals(moduleId));
        if (!removed) {
            throw new CourseDomainException("Module not found: " + moduleId);
        }
        updatedAt = Instant.now();
    }

    public void moveModule(UUID moduleId, int position) {
        ensureEditable();
        CourseModule module = findModule(moduleId);
        ensureModulePositionAvailable(position, moduleId);
        module.update(module.getTitle(), module.getDescription(), position);
        sortModules();
        updatedAt = Instant.now();
    }

    public void addLesson(UUID moduleId, String title, LessonType lessonType, int position) {
        ensureEditable();
        findModule(moduleId).addLesson(title, lessonType, position);
        updatedAt = Instant.now();
    }

    public void removeLesson(UUID moduleId, UUID lessonId) {
        ensureEditable();
        findModule(moduleId).removeLesson(lessonId);
        updatedAt = Instant.now();
    }

    public void moveLesson(UUID moduleId, UUID lessonId, int position) {
        ensureEditable();
        findModule(moduleId).moveLesson(lessonId, position);
        updatedAt = Instant.now();
    }

    public CourseAsset addAsset(CourseAssetType assetType, String assetUrl, String fileName, Long fileSize) {
        ensureEditable();
        CourseAsset asset = CourseAsset.create(id, assetType, assetUrl, fileName, fileSize);
        assets.add(asset);
        if (assetType == CourseAssetType.THUMBNAIL) {
            thumbnailUrl = asset.getAssetUrl();
        }
        updatedAt = Instant.now();
        return asset;
    }

    public void removeAsset(UUID assetId) {
        ensureEditable();
        CourseAsset removedAsset = assets.stream()
                .filter(asset -> asset.getId().equals(assetId))
                .findFirst()
                .orElseThrow(() -> new CourseDomainException("Asset not found: " + assetId));
        assets.remove(removedAsset);
        if (Objects.equals(thumbnailUrl, removedAsset.getAssetUrl())) {
            thumbnailUrl = null;
        }
        updatedAt = Instant.now();
    }

    public void assignCategories(Set<UUID> categoryIds) {
        ensureEditable();
        this.categoryIds.clear();
        if (categoryIds != null) {
            categoryIds.forEach(categoryId -> this.categoryIds.add(Objects.requireNonNull(categoryId)));
        }
        updatedAt = Instant.now();
    }

    public void publish() {
        if (status == CourseStatus.ARCHIVED) {
            throw new CourseDomainException("Archived course cannot be published");
        }
        if (modules.stream().flatMap(module -> module.getLessons().stream()).findAny().isEmpty()) {
            throw new CourseDomainException("Course must have at least one lesson before publishing");
        }
        status = CourseStatus.PUBLISHED;
        publishedAt = Instant.now();
        updatedAt = publishedAt;
    }

    public void archive() {
        status = CourseStatus.ARCHIVED;
        updatedAt = Instant.now();
    }

    public void returnToDraft() {
        if (status == CourseStatus.ARCHIVED) {
            throw new CourseDomainException("Archived course cannot return to draft");
        }
        status = CourseStatus.DRAFT;
        publishedAt = null;
        updatedAt = Instant.now();
    }

    private void ensureEditable() {
        if (status == CourseStatus.ARCHIVED) {
            throw new CourseDomainException("Archived course cannot be edited");
        }
    }

    private CourseModule findModule(UUID moduleId) {
        return modules.stream()
                .filter(module -> module.getId().equals(moduleId))
                .findFirst()
                .orElseThrow(() -> new CourseDomainException("Module not found: " + moduleId));
    }

    private void ensureModulePositionAvailable(int position, UUID currentModuleId) {
        if (position <= 0) {
            throw new CourseDomainException("Module position must be greater than zero");
        }
        boolean duplicate = modules.stream()
                .anyMatch(module -> module.getPosition() == position && !module.getId().equals(currentModuleId));
        if (duplicate) {
            throw new CourseDomainException("Duplicate module position: " + position);
        }
    }

    private void sortModules() {
        modules.sort(Comparator.comparingInt(CourseModule::getPosition));
    }

    private static void ensureUniqueModulePositions(List<CourseModule> modules) {
        long distinctPositions = modules.stream().map(CourseModule::getPosition).distinct().count();
        if (distinctPositions != modules.size()) {
            throw new CourseDomainException("Module positions must be unique within a course");
        }
    }

    private static String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new CourseDomainException("Course title must not be blank");
        }
        String normalized = title.trim();
        if (normalized.length() > 255) {
            throw new CourseDomainException("Course title must not exceed 255 characters");
        }
        return normalized;
    }

    private static String validateShortDescription(String shortDescription) {
        String normalized = normalizeNullable(shortDescription);
        if (normalized != null && normalized.length() > 500) {
            throw new CourseDomainException("Course short description must not exceed 500 characters");
        }
        return normalized;
    }

    private static String normalizeNullable(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    public UUID getId() { return id; }
    public UUID getManagerId() { return managerId; }
    public String getTitle() { return title; }
    public Slug getSlug() { return slug; }
    public String getShortDescription() { return shortDescription; }
    public String getDescription() { return description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public Money getPrice() { return price; }
    public CourseDifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public CourseStatus getStatus() { return status; }
    public Instant getPublishedAt() { return publishedAt; }
    public Set<UUID> getCategoryIds() { return Set.copyOf(categoryIds); }
    public List<CourseModule> getModules() { return List.copyOf(modules); }
    public List<CourseAsset> getAssets() { return List.copyOf(assets); }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
