package course.learningservice.infrastructure.persistence.repository;

import course.learningservice.infrastructure.persistence.entity.LessonProgressEntity;
import course.learningservice.infrastructure.persistence.entity.enums.ProgressStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface LessonProgressRepository {

    Optional<LessonProgressEntity> findById(@Param("id") UUID id);

    Optional<LessonProgressEntity> findByLearnerIdAndLessonId(@Param("learnerId") UUID learnerId,
                                                              @Param("lessonId") UUID lessonId);

    List<LessonProgressEntity> findByLearnerIdAndCourseId(@Param("learnerId") UUID learnerId,
                                                          @Param("courseId") UUID courseId);

    List<LessonProgressEntity> findByLearnerId(@Param("learnerId") UUID learnerId);

    List<LessonProgressEntity> findByCourseId(@Param("courseId") UUID courseId);

    List<LessonProgressEntity> findByStatus(@Param("status") ProgressStatus status);

    int insert(@Param("lessonProgress") LessonProgressEntity lessonProgress);

    int update(@Param("lessonProgress") LessonProgressEntity lessonProgress);

    int deleteById(@Param("id") UUID id);
}
