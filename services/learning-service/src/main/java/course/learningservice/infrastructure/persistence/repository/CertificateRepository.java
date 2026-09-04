package course.learningservice.infrastructure.persistence.repository;

import course.learningservice.infrastructure.persistence.entity.CertificateEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CertificateRepository {

    Optional<CertificateEntity> findById(@Param("id") UUID id);

    Optional<CertificateEntity> findByCertificateCode(@Param("certificateCode") String certificateCode);

    Optional<CertificateEntity> findByLearnerIdAndCourseId(@Param("learnerId") UUID learnerId,
                                                           @Param("courseId") UUID courseId);

    List<CertificateEntity> findByLearnerId(@Param("learnerId") UUID learnerId);

    int insert(@Param("certificate") CertificateEntity certificate);

    int update(@Param("certificate") CertificateEntity certificate);

    int deleteById(@Param("id") UUID id);
}
