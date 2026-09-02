package course.courseservice.infrastructure.persistence.repository;

import course.courseservice.infrastructure.persistence.entity.CategoryInfraEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface CategoryMybatisRepository {

    Optional<CategoryInfraEntity> findById(@Param("id") UUID id);

    Optional<CategoryInfraEntity> findBySlug(@Param("slug") String slug);

    Optional<CategoryInfraEntity> findByName(@Param("name") String name);

    List<CategoryInfraEntity> findAll();

    boolean existsBySlug(@Param("slug") String slug);

    boolean existsById(@Param("id") UUID id);

    int insert(@Param("category") CategoryInfraEntity category);

    int update(@Param("category") CategoryInfraEntity category);

    int deleteById(@Param("id") UUID id);
}
