package course.enrollmentservice.infrastructure.persistence.repository;

import course.enrollmentservice.infrastructure.persistence.entity.SagaInstanceEntity;
import course.enrollmentservice.infrastructure.persistence.entity.enums.SagaStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SagaInstanceRepository {

    @Select("""
            SELECT id, saga_type, enrollment_id, current_step, status,
                   payload::text AS payload, failure_reason, started_at, completed_at,
                   created_at, updated_at
            FROM saga_instances
            WHERE id = #{id}
            """)
    Optional<SagaInstanceEntity> findById(UUID id);

    @Select("""
            SELECT id, saga_type, enrollment_id, current_step, status,
                   payload::text AS payload, failure_reason, started_at, completed_at,
                   created_at, updated_at
            FROM saga_instances
            WHERE enrollment_id = #{enrollmentId}
            ORDER BY created_at DESC
            """)
    List<SagaInstanceEntity> findByEnrollmentId(UUID enrollmentId);

    @Select("""
            SELECT id, saga_type, enrollment_id, current_step, status,
                   payload::text AS payload, failure_reason, started_at, completed_at,
                   created_at, updated_at
            FROM saga_instances
            WHERE status = #{status}
            ORDER BY created_at ASC
            """)
    List<SagaInstanceEntity> findByStatus(SagaStatus status);

    @Insert("""
            INSERT INTO saga_instances (
                saga_type, enrollment_id, current_step, status, payload,
                failure_reason, started_at, completed_at
            )
            VALUES (
                #{sagaType}, #{enrollmentId}, #{currentStep}, #{status},
                CAST(#{payload} AS jsonb), #{failureReason}, #{startedAt}, #{completedAt}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(SagaInstanceEntity sagaInstance);

    @Update("""
            UPDATE saga_instances
            SET saga_type = #{sagaType},
                enrollment_id = #{enrollmentId},
                current_step = #{currentStep},
                status = #{status},
                payload = CAST(#{payload} AS jsonb),
                failure_reason = #{failureReason},
                started_at = #{startedAt},
                completed_at = #{completedAt},
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int update(SagaInstanceEntity sagaInstance);

    @Delete("""
            DELETE FROM saga_instances
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
