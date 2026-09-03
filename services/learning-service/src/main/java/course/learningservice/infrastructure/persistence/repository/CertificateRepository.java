package course.learningservice.infrastructure.persistence.repository;

import course.learningservice.infrastructure.persistence.entity.CertificateEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CertificateRepository {

    @Select("""
            SELECT id, learner_id, course_id, certificate_code, certificate_url,
                   issued_at, created_at
            FROM certificates
            WHERE id = #{id}
            """)
    Optional<CertificateEntity> findById(UUID id);

    @Select("""
            SELECT id, learner_id, course_id, certificate_code, certificate_url,
                   issued_at, created_at
            FROM certificates
            WHERE certificate_code = #{certificateCode}
            """)
    Optional<CertificateEntity> findByCertificateCode(String certificateCode);

    @Select("""
            SELECT id, learner_id, course_id, certificate_code, certificate_url,
                   issued_at, created_at
            FROM certificates
            WHERE learner_id = #{learnerId}
              AND course_id = #{courseId}
            """)
    Optional<CertificateEntity> findByLearnerIdAndCourseId(@Param("learnerId") UUID learnerId,
                                                           @Param("courseId") UUID courseId);

    @Select("""
            SELECT id, learner_id, course_id, certificate_code, certificate_url,
                   issued_at, created_at
            FROM certificates
            WHERE learner_id = #{learnerId}
            ORDER BY issued_at DESC
            """)
    List<CertificateEntity> findByLearnerId(UUID learnerId);

    @Insert("""
            INSERT INTO certificates (
                learner_id, course_id, certificate_code, certificate_url, issued_at
            )
            VALUES (
                #{learnerId}, #{courseId}, #{certificateCode}, #{certificateUrl}, #{issuedAt}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(CertificateEntity certificate);

    @Update("""
            UPDATE certificates
            SET certificate_code = #{certificateCode},
                certificate_url = #{certificateUrl},
                issued_at = #{issuedAt}
            WHERE id = #{id}
            """)
    int update(CertificateEntity certificate);

    @Delete("""
            DELETE FROM certificates
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
