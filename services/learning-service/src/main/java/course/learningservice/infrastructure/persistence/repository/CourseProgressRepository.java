package course.learningservice.infrastructure.persistence.repository;

import course.learningservice.infrastructure.persistence.entity.CourseProgressEntity;
import course.learningservice.infrastructure.persistence.entity.enums.ProgressStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CourseProgressRepository {

    Optional<CourseProgressEntity> findById(@Param("id") UUID id);

    Optional<CourseProgressEntity> findByLearnerIdAndCourseId(@Param("learnerId") UUID learnerId,
                                                              @Param("courseId") UUID courseId);

    List<CourseProgressEntity> findByLearnerId(@Param("learnerId") UUID learnerId);

    List<CourseProgressEntity> findByCourseId(@Param("courseId") UUID courseId);

    List<CourseProgressEntity> findByStatus(@Param("status") ProgressStatus status);

    int insert(@Param("courseProgress") CourseProgressEntity courseProgress);

    int update(@Param("courseProgress") CourseProgressEntity courseProgress);

    int deleteById(@Param("id") UUID id);
}
