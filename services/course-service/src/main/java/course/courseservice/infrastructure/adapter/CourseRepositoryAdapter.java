package course.courseservice.infrastructure.adapter;

import course.courseservice.domain.model.course.aggregate.Course;
import course.courseservice.domain.model.course.valueobject.Slug;
import course.courseservice.domain.repository.CourseRepository;
import course.courseservice.infrastructure.mapper.CoursePersistenceMapper;
import course.courseservice.infrastructure.persistence.entity.CourseInfraEntity;
import course.courseservice.infrastructure.persistence.entity.LessonInfraEntity;
import course.courseservice.infrastructure.persistence.entity.ModuleInfraEntity;
import course.courseservice.infrastructure.persistence.repository.CourseMybatisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseRepositoryAdapter implements CourseRepository {

    private final CourseMybatisRepository repository;
    private final CoursePersistenceMapper mapper;

    @Override
    public Optional<Course> findById(UUID id) {
        return repository.findById(id).map(this::toDomainWithChildren);
    }

    @Override
    public Optional<Course> findBySlug(Slug slug) {
        return repository.findBySlug(slug.value()).map(this::toDomainWithChildren);
    }

    @Override
    public List<Course> findByManagerId(UUID managerId) {
        return repository.findByManagerId(managerId)
                .stream()
                .map(this::toDomainWithChildren)
                .toList();
    }

    @Override
    public boolean existsBySlug(Slug slug) {
        return repository.existsBySlug(slug.value());
    }

    @Override
    public Course save(Course course) {
        CourseInfraEntity entity = mapper.toCourseEntity(course);
        if (repository.existsById(course.getId())) {
            repository.update(entity);
        } else {
            repository.insert(entity);
        }

        replaceChildren(course);
        return findById(course.getId()).orElseThrow();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private Course toDomainWithChildren(CourseInfraEntity entity) {
        List<ModuleInfraEntity> modules = repository.findModulesByCourseId(entity.getId());
        Map<UUID, List<LessonInfraEntity>> lessonsByModuleId = modules.stream()
                .collect(Collectors.toMap(
                        ModuleInfraEntity::getId,
                        module -> repository.findLessonsByModuleId(module.getId())
                ));

        return mapper.toDomain(
                entity,
                modules,
                lessonsByModuleId,
                repository.findAssetsByCourseId(entity.getId()),
                repository.findCategoriesByCourseId(entity.getId())
        );
    }

    private void replaceChildren(Course course) {
        repository.deleteCategoriesByCourseId(course.getId());
        repository.deleteAssetsByCourseId(course.getId());
        repository.deleteModulesByCourseId(course.getId());

        course.getCategoryIds().forEach(categoryId ->
                repository.insertCategory(mapper.toCourseCategoryEntity(course.getId(), categoryId, course.getUpdatedAt())));

        course.getModules().forEach(module -> {
            repository.insertModule(mapper.toModuleEntity(module));
            module.getLessons().forEach(lesson -> repository.insertLesson(mapper.toLessonEntity(lesson)));
        });

        course.getAssets().forEach(asset -> repository.insertAsset(mapper.toAssetEntity(asset)));
    }
}
