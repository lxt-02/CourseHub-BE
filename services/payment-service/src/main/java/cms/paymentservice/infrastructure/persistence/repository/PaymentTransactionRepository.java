package cms.paymentservice.infrastructure.persistence.repository;

import cms.paymentservice.infrastructure.persistence.entity.PaymentTransactionEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentTransactionRepository {

    @Select("""
            SELECT id, payment_id, provider, external_transaction_id, amount,
                   currency, status, transaction_time, raw_response::text AS raw_response,
                   created_at
            FROM payment_transactions
            WHERE id = #{id}
            """)
    Optional<PaymentTransactionEntity> findById(UUID id);

    @Select("""
            SELECT id, payment_id, provider, external_transaction_id, amount,
                   currency, status, transaction_time, raw_response::text AS raw_response,
                   created_at
            FROM payment_transactions
            WHERE payment_id = #{paymentId}
            ORDER BY created_at DESC
            """)
    List<PaymentTransactionEntity> findByPaymentId(UUID paymentId);

    @Select("""
            SELECT id, payment_id, provider, external_transaction_id, amount,
                   currency, status, transaction_time, raw_response::text AS raw_response,
                   created_at
            FROM payment_transactions
            WHERE external_transaction_id = #{externalTransactionId}
            ORDER BY created_at DESC
            """)
    List<PaymentTransactionEntity> findByExternalTransactionId(String externalTransactionId);

    @Insert("""
            INSERT INTO payment_transactions (
                payment_id, provider, external_transaction_id, amount, currency,
                status, transaction_time, raw_response
            )
            VALUES (
                #{paymentId}, #{provider}, #{externalTransactionId}, #{amount}, #{currency},
                #{status}, #{transactionTime}, CAST(#{rawResponse} AS jsonb)
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(PaymentTransactionEntity transaction);

    @Delete("""
            DELETE FROM payment_transactions
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
