package course.courseservice.domain.repository;

import course.courseservice.domain.model.course.aggregate.Course;
import course.courseservice.domain.model.course.valueobject.Slug;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository {

    Optional<Course> findById(UUID id);

    Optional<Course> findBySlug(Slug slug);

    List<Course> findByManagerId(UUID managerId);

    boolean existsBySlug(Slug slug);

    Course save(Course course);

    void deleteById(UUID id);
}
