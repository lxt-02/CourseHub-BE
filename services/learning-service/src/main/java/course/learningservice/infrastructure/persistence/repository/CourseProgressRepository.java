package course.learningservice.infrastructure.persistence.repository;

import course.learningservice.infrastructure.persistence.entity.CourseProgressEntity;
import course.learningservice.infrastructure.persistence.entity.enums.ProgressStatus;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseProgressRepository {

    @Select("""
            SELECT id, learner_id, course_id, total_lessons, completed_lessons,
                   progress_percent, status, started_at, completed_at,
                   created_at, updated_at
            FROM course_progress
            WHERE id = #{id}
            """)
    Optional<CourseProgressEntity> findById(UUID id);

    @Select("""
            SELECT id, learner_id, course_id, total_lessons, completed_lessons,
                   progress_percent, status, started_at, completed_at,
                   created_at, updated_at
            FROM course_progress
            WHERE learner_id = #{learnerId}
              AND course_id = #{courseId}
            """)
    Optional<CourseProgressEntity> findByLearnerIdAndCourseId(@Param("learnerId") UUID learnerId,
                                                              @Param("courseId") UUID courseId);

    @Select("""
            SELECT id, learner_id, course_id, total_lessons, completed_lessons,
                   progress_percent, status, started_at, completed_at,
                   created_at, updated_at
            FROM course_progress
            WHERE learner_id = #{learnerId}
            ORDER BY updated_at DESC
            """)
    List<CourseProgressEntity> findByLearnerId(UUID learnerId);

    @Select("""
            SELECT id, learner_id, course_id, total_lessons, completed_lessons,
                   progress_percent, status, started_at, completed_at,
                   created_at, updated_at
            FROM course_progress
            WHERE course_id = #{courseId}
            ORDER BY updated_at DESC
            """)
    List<CourseProgressEntity> findByCourseId(UUID courseId);

    @Select("""
            SELECT id, learner_id, course_id, total_lessons, completed_lessons,
                   progress_percent, status, started_at, completed_at,
                   created_at, updated_at
            FROM course_progress
            WHERE status = #{status}
            ORDER BY updated_at DESC
            """)
    List<CourseProgressEntity> findByStatus(ProgressStatus status);

    @Insert("""
            INSERT INTO course_progress (
                learner_id, course_id, total_lessons, completed_lessons,
                progress_percent, status, started_at, completed_at
            )
            VALUES (
                #{learnerId}, #{courseId}, #{totalLessons}, #{completedLessons},
                #{progressPercent}, #{status}, #{startedAt}, #{completedAt}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(CourseProgressEntity courseProgress);

    @Update("""
            UPDATE course_progress
            SET learner_id = #{learnerId},
                course_id = #{courseId},
                total_lessons = #{totalLessons},
                completed_lessons = #{completedLessons},
                progress_percent = #{progressPercent},
                status = #{status},
                started_at = #{startedAt},
                completed_at = #{completedAt},
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int update(CourseProgressEntity courseProgress);

    @Delete("""
            DELETE FROM course_progress
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
