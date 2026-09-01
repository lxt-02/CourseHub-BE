package course.courseservice.api.dto.request;

import course.courseservice.domain.model.course.enums.LessonType;

public record AddLessonRequest(String title, LessonType lessonType, int position) {
}
