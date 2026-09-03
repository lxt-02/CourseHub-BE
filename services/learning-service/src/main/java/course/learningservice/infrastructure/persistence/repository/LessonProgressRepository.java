package course.learningservice.infrastructure.persistence.repository;

import course.learningservice.infrastructure.persistence.entity.LessonProgressEntity;
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

public interface LessonProgressRepository {

    @Select("""
            SELECT id, learner_id, course_id, lesson_id, watched_seconds,
                   progress_percent, status, started_at, last_accessed_at,
                   completed_at, created_at, updated_at
            FROM lesson_progress
            WHERE id = #{id}
            """)
    Optional<LessonProgressEntity> findById(UUID id);

    @Select("""
            SELECT id, learner_id, course_id, lesson_id, watched_seconds,
                   progress_percent, status, started_at, last_accessed_at,
                   completed_at, created_at, updated_at
            FROM lesson_progress
            WHERE learner_id = #{learnerId}
              AND lesson_id = #{lessonId}
            """)
    Optional<LessonProgressEntity> findByLearnerIdAndLessonId(@Param("learnerId") UUID learnerId,
                                                              @Param("lessonId") UUID lessonId);

    @Select("""
            SELECT id, learner_id, course_id, lesson_id, watched_seconds,
                   progress_percent, status, started_at, last_accessed_at,
                   completed_at, created_at, updated_at
            FROM lesson_progress
            WHERE learner_id = #{learnerId}
              AND course_id = #{courseId}
            ORDER BY updated_at DESC
            """)
    List<LessonProgressEntity> findByLearnerIdAndCourseId(@Param("learnerId") UUID learnerId,
                                                          @Param("courseId") UUID courseId);

    @Select("""
            SELECT id, learner_id, course_id, lesson_id, watched_seconds,
                   progress_percent, status, started_at, last_accessed_at,
                   completed_at, created_at, updated_at
            FROM lesson_progress
            WHERE learner_id = #{learnerId}
            ORDER BY updated_at DESC
            """)
    List<LessonProgressEntity> findByLearnerId(UUID learnerId);

    @Select("""
            SELECT id, learner_id, course_id, lesson_id, watched_seconds,
                   progress_percent, status, started_at, last_accessed_at,
                   completed_at, created_at, updated_at
            FROM lesson_progress
            WHERE course_id = #{courseId}
            ORDER BY updated_at DESC
            """)
    List<LessonProgressEntity> findByCourseId(UUID courseId);

    @Select("""
            SELECT id, learner_id, course_id, lesson_id, watched_seconds,
                   progress_percent, status, started_at, last_accessed_at,
                   completed_at, created_at, updated_at
            FROM lesson_progress
            WHERE status = #{status}
            ORDER BY updated_at DESC
            """)
    List<LessonProgressEntity> findByStatus(ProgressStatus status);

    @Insert("""
            INSERT INTO lesson_progress (
                learner_id, course_id, lesson_id, watched_seconds,
                progress_percent, status, started_at, last_accessed_at, completed_at
            )
            VALUES (
                #{learnerId}, #{courseId}, #{lessonId}, #{watchedSeconds},
                #{progressPercent}, #{status}, #{startedAt}, #{lastAccessedAt}, #{completedAt}
            )
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(LessonProgressEntity lessonProgress);

    @Update("""
            UPDATE lesson_progress
            SET learner_id = #{learnerId},
                course_id = #{courseId},
                lesson_id = #{lessonId},
                watched_seconds = #{watchedSeconds},
                progress_percent = #{progressPercent},
                status = #{status},
                started_at = #{startedAt},
                last_accessed_at = #{lastAccessedAt},
                completed_at = #{completedAt},
                updated_at = CURRENT_TIMESTAMP
            WHERE id = #{id}
            """)
    int update(LessonProgressEntity lessonProgress);

    @Delete("""
            DELETE FROM lesson_progress
            WHERE id = #{id}
            """)
    int deleteById(UUID id);
}
