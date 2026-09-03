package course.courseservice.application.port;

import course.courseservice.domain.model.category.aggregate.Category;

public interface CategoryEventPublisherPort {
    void publishCreated(Category category);
    void publishUpdated(Category category);
    void publishDeleted(String categoryId);
}
