package course.courseservice.domain.model.course.entity;

import course.courseservice.domain.model.course.enums.CourseAssetType;
import course.courseservice.domain.model.course.exception.CourseDomainException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class CourseAsset {

    private final UUID id;
    private final UUID courseId;
    private CourseAssetType assetType;
    private String assetUrl;
    private String fileName;
    private Long fileSize;
    private final Instant createdAt;

    private CourseAsset(UUID id, UUID courseId, CourseAssetType assetType, String assetUrl,
                        String fileName, Long fileSize, Instant createdAt) {
        this.id = Objects.requireNonNull(id, "Asset id must not be null");
        this.courseId = Objects.requireNonNull(courseId, "Course id must not be null");
        this.assetType = Objects.requireNonNull(assetType, "Asset type must not be null");
        this.assetUrl = validateAssetUrl(assetUrl);
        this.fileName = normalizeNullable(fileName);
        this.fileSize = validateFileSize(fileSize);
        this.createdAt = Objects.requireNonNull(createdAt, "Created time must not be null");
    }

    public static CourseAsset create(UUID courseId, CourseAssetType assetType, String assetUrl,
                                     String fileName, Long fileSize) {
        return new CourseAsset(UUID.randomUUID(), courseId, assetType, assetUrl, fileName, fileSize, Instant.now());
    }

    public static CourseAsset restore(UUID id, UUID courseId, CourseAssetType assetType, String assetUrl,
                                      String fileName, Long fileSize, Instant createdAt) {
        return new CourseAsset(id, courseId, assetType, assetUrl, fileName, fileSize, createdAt);
    }

    public void update(CourseAssetType assetType, String assetUrl, String fileName, Long fileSize) {
        this.assetType = Objects.requireNonNull(assetType, "Asset type must not be null");
        this.assetUrl = validateAssetUrl(assetUrl);
        this.fileName = normalizeNullable(fileName);
        this.fileSize = validateFileSize(fileSize);
    }

    private static String validateAssetUrl(String assetUrl) {
        if (assetUrl == null || assetUrl.isBlank()) {
            throw new CourseDomainException("Asset URL must not be blank");
        }
        return assetUrl.trim();
    }

    private static Long validateFileSize(Long fileSize) {
        if (fileSize != null && fileSize < 0) {
            throw new CourseDomainException("Asset file size must not be negative");
        }
        return fileSize;
    }

    private static String normalizeNullable(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    public UUID getId() { return id; }
    public UUID getCourseId() { return courseId; }
    public CourseAssetType getAssetType() { return assetType; }
    public String getAssetUrl() { return assetUrl; }
    public String getFileName() { return fileName; }
    public Long getFileSize() { return fileSize; }
    public Instant getCreatedAt() { return createdAt; }
}
