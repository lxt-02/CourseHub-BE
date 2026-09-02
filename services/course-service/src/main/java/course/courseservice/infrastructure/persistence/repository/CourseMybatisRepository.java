package course.courseservice.infrastructure.persistence.repository;

import course.courseservice.infrastructure.persistence.entity.CourseAssetInfraEntity;
import course.courseservice.infrastructure.persistence.entity.CourseCategoryInfraEntity;
import course.courseservice.infrastructure.persistence.entity.CourseInfraEntity;
import course.courseservice.infrastructure.persistence.entity.LessonInfraEntity;
import course.courseservice.infrastructure.persistence.entity.ModuleInfraEntity;
import course.courseservice.infrastructure.persistence.entity.enums.CourseStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CourseMybatisRepository {

    Optional<CourseInfraEntity> findById(@Param("id") UUID id);

    Optional<CourseInfraEntity> findBySlug(@Param("slug") String slug);

    List<CourseInfraEntity> findByManagerId(@Param("managerId") UUID managerId);

    boolean existsBySlug(@Param("slug") String slug);

    boolean existsById(@Param("id") UUID id);

    int insert(@Param("course") CourseInfraEntity course);

    int update(@Param("course") CourseInfraEntity course);

    int updateStatus(@Param("id") UUID id, @Param("status") CourseStatus status);

    int deleteById(@Param("id") UUID id);

    List<ModuleInfraEntity> findModulesByCourseId(@Param("courseId") UUID courseId);

    List<LessonInfraEntity> findLessonsByModuleId(@Param("moduleId") UUID moduleId);

    List<CourseAssetInfraEntity> findAssetsByCourseId(@Param("courseId") UUID courseId);

    List<CourseCategoryInfraEntity> findCategoriesByCourseId(@Param("courseId") UUID courseId);

    int insertModule(@Param("module") ModuleInfraEntity module);

    int insertLesson(@Param("lesson") LessonInfraEntity lesson);

    int insertAsset(@Param("asset") CourseAssetInfraEntity asset);

    int insertCategory(@Param("courseCategory") CourseCategoryInfraEntity courseCategory);

    int deleteModulesByCourseId(@Param("courseId") UUID courseId);

    int deleteAssetsByCourseId(@Param("courseId") UUID courseId);

    int deleteCategoriesByCourseId(@Param("courseId") UUID courseId);
}
