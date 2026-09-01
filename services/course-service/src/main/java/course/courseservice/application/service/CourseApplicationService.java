package course.courseservice.application.service;

import course.courseservice.application.command.course.AddCourseAssetCommand;
import course.courseservice.application.command.course.AddLessonCommand;
import course.courseservice.application.command.course.AddModuleCommand;
import course.courseservice.application.command.course.AssignCourseCategoriesCommand;
import course.courseservice.application.command.course.CreateCourseCommand;
import course.courseservice.application.command.course.MoveLessonCommand;
import course.courseservice.application.command.course.MoveModuleCommand;
import course.courseservice.application.command.course.UpdateCourseCommand;
import course.courseservice.application.dto.course.CourseResponse;
import course.courseservice.application.exception.ApplicationConflictException;
import course.courseservice.application.exception.ApplicationNotFoundException;
import course.courseservice.domain.model.course.aggregate.Course;
import course.courseservice.domain.model.course.valueobject.Money;
import course.courseservice.domain.model.course.valueobject.Slug;
import course.courseservice.domain.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public record CourseApplicationService(CourseRepository courseRepository) {

    @Transactional
    public CourseResponse create(CreateCourseCommand command) {
        Slug slug = Slug.fromTitle(command.title());
        ensureSlugAvailable(slug, null);

        Course course = Course.create(
                command.managerId(),
                command.title(),
                command.shortDescription(),
                command.description(),
                moneyOrZero(command.price()),
                command.difficultyLevel()
        );

        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional(readOnly = true)
    public CourseResponse getById(UUID id) {
        return CourseResponse.from(findCourse(id));
    }

    @Transactional(readOnly = true)
    public CourseResponse getBySlug(String slug) {
        return courseRepository.findBySlug(new Slug(slug))
                .map(CourseResponse::from)
                .orElseThrow(() -> new ApplicationNotFoundException("Course not found by slug: " + slug));
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getByManagerId(UUID managerId) {
        return courseRepository.findByManagerId(managerId)
                .stream()
                .map(CourseResponse::from)
                .toList();
    }

    @Transactional
    public CourseResponse update(UUID id, UpdateCourseCommand command) {
        Course course = findCourse(id);
        Slug slug = command.slug() == null || command.slug().isBlank()
                ? Slug.fromTitle(command.title())
                : new Slug(command.slug());
        ensureSlugAvailable(slug, id);

        course.updateDetails(
                command.title(),
                slug,
                command.shortDescription(),
                command.description(),
                command.thumbnailUrl(),
                moneyOrZero(command.price()),
                command.difficultyLevel()
        );

        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public CourseResponse publish(UUID id) {
        Course course = findCourse(id);
        course.publish();
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public CourseResponse archive(UUID id) {
        Course course = findCourse(id);
        course.archive();
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public CourseResponse returnToDraft(UUID id) {
        Course course = findCourse(id);
        course.returnToDraft();
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public CourseResponse assignCategories(UUID id, AssignCourseCategoriesCommand command) {
        Course course = findCourse(id);
        course.assignCategories(command.categoryIds());
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public CourseResponse addModule(UUID id, AddModuleCommand command) {
        Course course = findCourse(id);
        course.addModule(command.title(), command.position());
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public CourseResponse moveModule(UUID courseId, UUID moduleId, MoveModuleCommand command) {
        Course course = findCourse(courseId);
        course.moveModule(moduleId, command.position());
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public void removeModule(UUID courseId, UUID moduleId) {
        Course course = findCourse(courseId);
        course.removeModule(moduleId);
        courseRepository.save(course);
    }

    @Transactional
    public CourseResponse addLesson(UUID courseId, UUID moduleId, AddLessonCommand command) {
        Course course = findCourse(courseId);
        course.addLesson(moduleId, command.title(), command.lessonType(), command.position());
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public CourseResponse moveLesson(UUID courseId, UUID moduleId, UUID lessonId, MoveLessonCommand command) {
        Course course = findCourse(courseId);
        course.moveLesson(moduleId, lessonId, command.position());
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public void removeLesson(UUID courseId, UUID moduleId, UUID lessonId) {
        Course course = findCourse(courseId);
        course.removeLesson(moduleId, lessonId);
        courseRepository.save(course);
    }

    @Transactional
    public CourseResponse addAsset(UUID id, AddCourseAssetCommand command) {
        Course course = findCourse(id);
        course.addAsset(command.assetType(), command.assetUrl(), command.fileName(), command.fileSize());
        return CourseResponse.from(courseRepository.save(course));
    }

    @Transactional
    public void removeAsset(UUID courseId, UUID assetId) {
        Course course = findCourse(courseId);
        course.removeAsset(assetId);
        courseRepository.save(course);
    }

    @Transactional
    public void delete(UUID id) {
        courseRepository.deleteById(id);
    }

    private Course findCourse(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Course not found: " + id));
    }

    private void ensureSlugAvailable(Slug slug, UUID currentCourseId) {
        courseRepository.findBySlug(slug)
                .filter(existing -> !existing.getId().equals(currentCourseId))
                .ifPresent(existing -> {
                    throw new ApplicationConflictException("Course slug already exists: " + slug.value());
                });
    }

    private static Money moneyOrZero(BigDecimal amount) {
        return amount == null ? Money.zero() : Money.of(amount);
    }
}
