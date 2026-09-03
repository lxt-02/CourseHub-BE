package course.courseservice.application.port;

import course.courseservice.domain.model.course.aggregate.Course;

public interface CourseEventPublisherPort {
    void publishCreated(Course course);
    void publishUpdated(Course course);
    void publishDeleted(String courseId);
}
