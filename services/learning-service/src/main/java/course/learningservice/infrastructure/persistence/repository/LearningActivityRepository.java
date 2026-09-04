package course.learningservice.infrastructure.persistence.repository;

import course.learningservice.infrastructure.persistence.entity.LearningActivityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface LearningActivityRepository {

    Optional<LearningActivityEntity> findById(@Param("id") UUID id);

    List<LearningActivityEntity> findByLearnerId(@Param("learnerId") UUID learnerId);

    List<LearningActivityEntity> findByCourseId(@Param("courseId") UUID courseId);

    List<LearningActivityEntity> findByLearnerIdAndCourseId(@Param("learnerId") UUID learnerId,
                                                            @Param("courseId") UUID courseId);

    int insert(@Param("learningActivity") LearningActivityEntity learningActivity);

    int deleteById(@Param("id") UUID id);
}
