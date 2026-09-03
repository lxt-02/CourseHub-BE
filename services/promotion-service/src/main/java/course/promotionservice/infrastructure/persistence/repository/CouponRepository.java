package course.promotionservice.infrastructure.persistence.repository;

import course.promotionservice.infrastructure.persistence.entity.CouponEntity;
import course.promotionservice.infrastructure.persistence.entity.enums.CouponStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponRepository {

    @Select("""
            SELECT id, code, name, description, discount_type, discount_value,
                   max_discount_amount, minimum_order_amount, usage_limit, used_count,
                   start_at, end_at, status, created_by, created_at, updated_at
            FROM coupons
            WHERE id = #{id}
            """)
    Optional<CouponEntity> findById(UUID id);

    @Select("""
            SELECT id, code, name, description, discount_type, discount_value,
                   max_discount_amount, minimum_order_amount, usage_limit, used_count,
                   start_at, end_at, status, created_by, created_at, updated_at
            FROM coupons
            WHERE code = #{code}
            """)
    Optional<CouponEntity> findByCode(String code);

    @Select("""
            SELECT id, code, name, description, discount_type, discount_value,
                   max_discount_amount, minimum_order_amount, usage_limit, used_count,
                   start_at, end_at, status, created_by, created_at, updated_at
            FROM coupons
            WHERE status = #{status}
            ORDER BY created_at DESC
            """)
    List<CouponEntity> findByStatus(CouponStatus status);

    @Select("""
            SELECT id, code, name, description, discount_type, discount_value,
                   max_discount_amount, minimum_order_amount, usage_limit, used_count,
                   start_at, end_at, status, created_by, created_at, updated_at
            FROM coupons
            ORDER BY created_at DESC
            """)
    List<CouponEntity> findAll();

    @Insert("""
            INSERT INTO coupons (
                code, name, description, discount_type, discount_value,
                max_discount_amount, minimum_order_amount, usage_limit, used_count,
                start_at, end_at, status, created_by
            )
            VALUES (
                #{code}, #{name}, #{description}, #{discountType}, #{discountValue},
                #{maxDiscountAmount}, #{minimumOrderAmount}, #{usageLimit}, #{usedCount},
                #{startAt}, #{endAt}, #{status}, #{createdBy}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(CouponEntity coupon);

    @Update("""
            UPDATE coupons
            SET code = #{code},
                name = #{name},
                description = #{description},
                discount_type = #{discountType},
                discount_value = #{discountValue},
                max_discount_amount = #{maxDiscountAmount},
                minimum_order_amount = #{minimumOrderAmount},
                usage_limit = #{usageLimit},
                used_count = #{usedCount},
                start_at = #{startAt},
                end_at = #{endAt},
                status = #{status},
                created_by = #{createdBy},
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int update(CouponEntity coupon);

    @Update("""
            UPDATE coupons
            SET used_count = used_count + 1,
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int incrementUsedCount(UUID id);

    @Delete("""
            DELETE FROM coupons
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
