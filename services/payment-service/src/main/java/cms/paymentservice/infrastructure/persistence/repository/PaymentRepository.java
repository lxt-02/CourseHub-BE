package cms.paymentservice.infrastructure.persistence.repository;

import cms.paymentservice.infrastructure.persistence.entity.PaymentEntity;
import cms.paymentservice.infrastructure.persistence.entity.enums.PaymentStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {

    @Select("""
            SELECT id, enrollment_id, learner_id, course_id, currency,
                   original_amount, discount_amount, final_amount,
                   transaction_reference, status, expires_at, paid_at,
                   created_at, updated_at
            FROM payments
            WHERE id = #{id}
            """)
    Optional<PaymentEntity> findById(UUID id);

    @Select("""
            SELECT id, enrollment_id, learner_id, course_id, currency,
                   original_amount, discount_amount, final_amount,
                   transaction_reference, status, expires_at, paid_at,
                   created_at, updated_at
            FROM payments
            WHERE transaction_reference = #{transactionReference}
            """)
    Optional<PaymentEntity> findByTransactionReference(String transactionReference);

    @Select("""
            SELECT id, enrollment_id, learner_id, course_id, currency,
                   original_amount, discount_amount, final_amount,
                   transaction_reference, status, expires_at, paid_at,
                   created_at, updated_at
            FROM payments
            WHERE enrollment_id = #{enrollmentId}
            ORDER BY created_at DESC
            """)
    List<PaymentEntity> findByEnrollmentId(UUID enrollmentId);

    @Select("""
            SELECT id, enrollment_id, learner_id, course_id, currency,
                   original_amount, discount_amount, final_amount,
                   transaction_reference, status, expires_at, paid_at,
                   created_at, updated_at
            FROM payments
            WHERE learner_id = #{learnerId}
            ORDER BY created_at DESC
            """)
    List<PaymentEntity> findByLearnerId(UUID learnerId);

    @Select("""
            SELECT id, enrollment_id, learner_id, course_id, currency,
                   original_amount, discount_amount, final_amount,
                   transaction_reference, status, expires_at, paid_at,
                   created_at, updated_at
            FROM payments
            WHERE status = #{status}
            ORDER BY created_at DESC
            """)
    List<PaymentEntity> findByStatus(PaymentStatus status);

    @Insert("""
            INSERT INTO payments (
                enrollment_id, learner_id, course_id, currency,
                original_amount, discount_amount, final_amount,
                transaction_reference, status, expires_at, paid_at
            )
            VALUES (
                #{enrollmentId}, #{learnerId}, #{courseId}, #{currency},
                #{originalAmount}, #{discountAmount}, #{finalAmount},
                #{transactionReference}, #{status}, #{expiresAt}, #{paidAt}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(PaymentEntity payment);

    @Update("""
            UPDATE payments
            SET enrollment_id = #{enrollmentId},
                learner_id = #{learnerId},
                course_id = #{courseId},
                currency = #{currency},
                original_amount = #{originalAmount},
                discount_amount = #{discountAmount},
                final_amount = #{finalAmount},
                transaction_reference = #{transactionReference},
                status = #{status},
                expires_at = #{expiresAt},
                paid_at = #{paidAt},
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int update(PaymentEntity payment);

    @Update("""
            UPDATE payments
            SET status = #{status},
                paid_at = #{paidAt},
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int updateStatus(PaymentEntity payment);

    @Delete("""
            DELETE FROM payments
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
