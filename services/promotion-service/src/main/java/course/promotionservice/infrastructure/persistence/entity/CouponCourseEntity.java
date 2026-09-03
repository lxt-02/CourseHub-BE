package course.promotionservice.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.UUID;

public class CouponCourseEntity {

    private UUID couponId;
    private UUID courseId;
    private Instant createdAt;

    public UUID getCouponId() { return couponId; }
    public void setCouponId(UUID couponId) { this.couponId = couponId; }
    public UUID getCourseId() { return courseId; }
    public void setCourseId(UUID courseId) { this.courseId = courseId; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
