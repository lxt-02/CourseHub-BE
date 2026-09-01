package course.enrollmentservice.infrastructure.persistence.repository;

import course.enrollmentservice.infrastructure.persistence.entity.EnrollmentEntity;
import course.enrollmentservice.infrastructure.persistence.entity.enums.EnrollmentStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository {

    @Select("""
            SELECT id, learner_id, course_id, payment_id, status,
                   enrolled_at, activated_at, cancelled_at, revoked_at,
                   created_at, updated_at
            FROM enrollments
            WHERE id = #{id}
            """)
    Optional<EnrollmentEntity> findById(UUID id);

    @Select("""
            SELECT id, learner_id, course_id, payment_id, status,
                   enrolled_at, activated_at, cancelled_at, revoked_at,
                   created_at, updated_at
            FROM enrollments
            WHERE learner_id = #{learnerId}
              AND course_id = #{courseId}
            """)
    Optional<EnrollmentEntity> findByLearnerIdAndCourseId(@Param("learnerId") UUID learnerId,
                                                          @Param("courseId") UUID courseId);

    @Select("""
            SELECT id, learner_id, course_id, payment_id, status,
                   enrolled_at, activated_at, cancelled_at, revoked_at,
                   created_at, updated_at
            FROM enrollments
            WHERE learner_id = #{learnerId}
            ORDER BY created_at DESC
            """)
    List<EnrollmentEntity> findByLearnerId(UUID learnerId);

    @Select("""
            SELECT id, learner_id, course_id, payment_id, status,
                   enrolled_at, activated_at, cancelled_at, revoked_at,
                   created_at, updated_at
            FROM enrollments
            WHERE course_id = #{courseId}
            ORDER BY created_at DESC
            """)
    List<EnrollmentEntity> findByCourseId(UUID courseId);

    @Select("""
            SELECT id, learner_id, course_id, payment_id, status,
                   enrolled_at, activated_at, cancelled_at, revoked_at,
                   created_at, updated_at
            FROM enrollments
            WHERE status = #{status}
            ORDER BY created_at DESC
            """)
    List<EnrollmentEntity> findByStatus(EnrollmentStatus status);

    @Insert("""
            INSERT INTO enrollments (
                learner_id, course_id, payment_id, status,
                enrolled_at, activated_at, cancelled_at, revoked_at
            )
            VALUES (
                #{learnerId}, #{courseId}, #{paymentId}, #{status},
                #{enrolledAt}, #{activatedAt}, #{cancelledAt}, #{revokedAt}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(EnrollmentEntity enrollment);

    @Update("""
            UPDATE enrollments
            SET learner_id = #{learnerId},
                course_id = #{courseId},
                payment_id = #{paymentId},
                status = #{status},
                enrolled_at = #{enrolledAt},
                activated_at = #{activatedAt},
                cancelled_at = #{cancelledAt},
                revoked_at = #{revokedAt},
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int update(EnrollmentEntity enrollment);

    @Delete("""
            DELETE FROM enrollments
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
