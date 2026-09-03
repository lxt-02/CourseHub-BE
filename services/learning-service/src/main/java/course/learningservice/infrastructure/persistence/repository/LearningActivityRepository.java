package course.learningservice.infrastructure.persistence.repository;

import course.learningservice.infrastructure.persistence.entity.LearningActivityEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearningActivityRepository {

    @Select("""
            SELECT id, learner_id, course_id, lesson_id, activity_type,
                   metadata::text AS metadata, occurred_at, created_at
            FROM learning_activities
            WHERE id = #{id}
            """)
    Optional<LearningActivityEntity> findById(UUID id);

    @Select("""
            SELECT id, learner_id, course_id, lesson_id, activity_type,
                   metadata::text AS metadata, occurred_at, created_at
            FROM learning_activities
            WHERE learner_id = #{learnerId}
            ORDER BY occurred_at DESC
            """)
    List<LearningActivityEntity> findByLearnerId(UUID learnerId);

    @Select("""
            SELECT id, learner_id, course_id, lesson_id, activity_type,
                   metadata::text AS metadata, occurred_at, created_at
            FROM learning_activities
            WHERE course_id = #{courseId}
            ORDER BY occurred_at DESC
            """)
    List<LearningActivityEntity> findByCourseId(UUID courseId);

    @Select("""
            SELECT id, learner_id, course_id, lesson_id, activity_type,
                   metadata::text AS metadata, occurred_at, created_at
            FROM learning_activities
            WHERE learner_id = #{learnerId}
              AND course_id = #{courseId}
            ORDER BY occurred_at DESC
            """)
    List<LearningActivityEntity> findByLearnerIdAndCourseId(@Param("learnerId") UUID learnerId,
                                                            @Param("courseId") UUID courseId);

    @Insert("""
            INSERT INTO learning_activities (
                learner_id, course_id, lesson_id, activity_type, metadata, occurred_at
            )
            VALUES (
                #{learnerId}, #{courseId}, #{lessonId}, #{activityType},
                CAST(#{metadata} AS jsonb), #{occurredAt}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(LearningActivityEntity learningActivity);

    @Delete("""
            DELETE FROM learning_activities
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
