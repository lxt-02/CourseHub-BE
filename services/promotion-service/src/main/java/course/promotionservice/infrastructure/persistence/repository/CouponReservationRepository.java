package course.promotionservice.infrastructure.persistence.repository;

import course.promotionservice.infrastructure.persistence.entity.CouponReservationEntity;
import course.promotionservice.infrastructure.persistence.entity.enums.CouponReservationStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponReservationRepository {

    @Select("""
            SELECT id, coupon_id, learner_id, enrollment_id, status, reserved_at,
                   expires_at, confirmed_at, released_at, created_at, updated_at
            FROM coupon_reservations
            WHERE id = #{id}
            """)
    Optional<CouponReservationEntity> findById(UUID id);

    @Select("""
            SELECT id, coupon_id, learner_id, enrollment_id, status, reserved_at,
                   expires_at, confirmed_at, released_at, created_at, updated_at
            FROM coupon_reservations
            WHERE enrollment_id = #{enrollmentId}
            ORDER BY created_at DESC
            """)
    List<CouponReservationEntity> findByEnrollmentId(UUID enrollmentId);

    @Select("""
            SELECT id, coupon_id, learner_id, enrollment_id, status, reserved_at,
                   expires_at, confirmed_at, released_at, created_at, updated_at
            FROM coupon_reservations
            WHERE status = #{status}
            ORDER BY expires_at ASC
            """)
    List<CouponReservationEntity> findByStatus(CouponReservationStatus status);

    @Select("""
            SELECT id, coupon_id, learner_id, enrollment_id, status, reserved_at,
                   expires_at, confirmed_at, released_at, created_at, updated_at
            FROM coupon_reservations
            WHERE status = #{status}
              AND expires_at <= CURRENT_TIMESTAMP
            ORDER BY expires_at ASC
            """)
    List<CouponReservationEntity> findExpiredByStatus(CouponReservationStatus status);

    @Insert("""
            INSERT INTO coupon_reservations (
                coupon_id, learner_id, enrollment_id, status, reserved_at,
                expires_at, confirmed_at, released_at
            )
            VALUES (
                #{couponId}, #{learnerId}, #{enrollmentId}, #{status}, #{reservedAt},
                #{expiresAt}, #{confirmedAt}, #{releasedAt}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(CouponReservationEntity reservation);

    @Update("""
            UPDATE coupon_reservations
            SET coupon_id = #{couponId},
                learner_id = #{learnerId},
                enrollment_id = #{enrollmentId},
                status = #{status},
                reserved_at = #{reservedAt},
                expires_at = #{expiresAt},
                confirmed_at = #{confirmedAt},
                released_at = #{releasedAt},
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int update(CouponReservationEntity reservation);

    @Delete("""
            DELETE FROM coupon_reservations
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
