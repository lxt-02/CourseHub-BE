package cms.paymentservice.infrastructure.persistence.repository;

import cms.paymentservice.infrastructure.persistence.entity.PaymentIdempotencyKeyEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Optional;

public interface PaymentIdempotencyKeyRepository {

    @Select("""
            SELECT idempotency_key, payment_id, request_hash, created_at, expires_at
            FROM payment_idempotency_keys
            WHERE idempotency_key = #{idempotencyKey}
            """)
    Optional<PaymentIdempotencyKeyEntity> findById(String idempotencyKey);

    @Insert("""
            INSERT INTO payment_idempotency_keys (
                idempotency_key, payment_id, request_hash, expires_at
            )
            VALUES (
                #{idempotencyKey}, #{paymentId}, #{requestHash}, #{expiresAt}
            )
            """)
    int insert(PaymentIdempotencyKeyEntity idempotencyKey);

    @Update("""
            UPDATE payment_idempotency_keys
            SET payment_id = #{paymentId},
                request_hash = #{requestHash},
                expires_at = #{expiresAt}
            WHERE idempotency_key = #{idempotencyKey}
            """)
    int update(PaymentIdempotencyKeyEntity idempotencyKey);

    @Delete("""
            DELETE FROM payment_idempotency_keys
            WHERE idempotency_key = #{idempotencyKey}
            """)
    int deleteById(String idempotencyKey);
}
