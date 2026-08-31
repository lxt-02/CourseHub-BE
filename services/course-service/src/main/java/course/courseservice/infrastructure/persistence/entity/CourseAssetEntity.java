package course.courseservice.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.UUID;

public class CourseAssetEntity {

    private UUID id;
    private UUID courseId;
    private CourseAssetType assetType;
    private String assetUrl;
    private String fileName;
    private Long fileSize;
    private Instant createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public CourseAssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(CourseAssetType assetType) {
        this.assetType = assetType;
    }

    public String getAssetUrl() {
        return assetUrl;
    }

    public void setAssetUrl(String assetUrl) {
        this.assetUrl = assetUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
