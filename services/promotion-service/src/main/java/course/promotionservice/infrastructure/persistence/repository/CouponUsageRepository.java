package course.promotionservice.infrastructure.persistence.repository;

import course.promotionservice.infrastructure.persistence.entity.CouponUsageEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponUsageRepository {

    @Select("""
            SELECT id, coupon_id, learner_id, enrollment_id, discount_amount, used_at
            FROM coupon_usages
            WHERE id = #{id}
            """)
    Optional<CouponUsageEntity> findById(UUID id);

    @Select("""
            SELECT id, coupon_id, learner_id, enrollment_id, discount_amount, used_at
            FROM coupon_usages
            WHERE coupon_id = #{couponId}
              AND learner_id = #{learnerId}
              AND enrollment_id = #{enrollmentId}
            """)
    Optional<CouponUsageEntity> findByCouponIdAndLearnerIdAndEnrollmentId(@Param("couponId") UUID couponId,
                                                                          @Param("learnerId") UUID learnerId,
                                                                          @Param("enrollmentId") UUID enrollmentId);

    @Select("""
            SELECT id, coupon_id, learner_id, enrollment_id, discount_amount, used_at
            FROM coupon_usages
            WHERE learner_id = #{learnerId}
            ORDER BY used_at DESC
            """)
    List<CouponUsageEntity> findByLearnerId(UUID learnerId);

    @Select("""
            SELECT id, coupon_id, learner_id, enrollment_id, discount_amount, used_at
            FROM coupon_usages
            WHERE coupon_id = #{couponId}
            ORDER BY used_at DESC
            """)
    List<CouponUsageEntity> findByCouponId(UUID couponId);

    @Insert("""
            INSERT INTO coupon_usages (coupon_id, learner_id, enrollment_id, discount_amount)
            VALUES (#{couponId}, #{learnerId}, #{enrollmentId}, #{discountAmount})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(CouponUsageEntity usage);

    @Delete("""
            DELETE FROM coupon_usages
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
