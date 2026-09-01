package course.enrollmentservice.infrastructure.persistence.repository;

import course.enrollmentservice.infrastructure.persistence.entity.EnrollmentHistoryEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentHistoryRepository {

    @Select("""
            SELECT id, enrollment_id, old_status, new_status, reason, changed_at
            FROM enrollment_history
            WHERE id = #{id}
            """)
    Optional<EnrollmentHistoryEntity> findById(UUID id);

    @Select("""
            SELECT id, enrollment_id, old_status, new_status, reason, changed_at
            FROM enrollment_history
            WHERE enrollment_id = #{enrollmentId}
            ORDER BY changed_at DESC
            """)
    List<EnrollmentHistoryEntity> findByEnrollmentId(UUID enrollmentId);

    @Insert("""
            INSERT INTO enrollment_history (enrollment_id, old_status, new_status, reason)
            VALUES (#{enrollmentId}, #{oldStatus}, #{newStatus}, #{reason})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(EnrollmentHistoryEntity history);

    @Delete("""
            DELETE FROM enrollment_history
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
