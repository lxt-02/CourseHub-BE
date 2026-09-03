package course.promotionservice.infrastructure.persistence.repository;

import course.promotionservice.infrastructure.persistence.entity.CouponCourseEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponCourseRepository {

    @Select("""
            SELECT coupon_id, course_id, created_at
            FROM coupon_courses
            WHERE coupon_id = #{couponId}
              AND course_id = #{courseId}
            """)
    Optional<CouponCourseEntity> findById(@Param("couponId") UUID couponId,
                                          @Param("courseId") UUID courseId);

    @Select("""
            SELECT coupon_id, course_id, created_at
            FROM coupon_courses
            WHERE coupon_id = #{couponId}
            ORDER BY created_at DESC
            """)
    List<CouponCourseEntity> findByCouponId(UUID couponId);

    @Select("""
            SELECT coupon_id, course_id, created_at
            FROM coupon_courses
            WHERE course_id = #{courseId}
            ORDER BY created_at DESC
            """)
    List<CouponCourseEntity> findByCourseId(UUID courseId);

    @Insert("""
            INSERT INTO coupon_courses (coupon_id, course_id)
            VALUES (#{couponId}, #{courseId})
            """)
    int insert(CouponCourseEntity couponCourse);

    @Delete("""
            DELETE FROM coupon_courses
            WHERE coupon_id = #{couponId}
              AND course_id = #{courseId}
            """)
    int deleteById(@Param("couponId") UUID couponId,
                   @Param("courseId") UUID courseId);

    @Delete("""
            DELETE FROM coupon_courses
            WHERE coupon_id = #{couponId}
            """)
    int deleteByCouponId(UUID couponId);
}
