package course.courseservice.application.command.course;

import course.courseservice.domain.model.course.enums.LessonType;

public record AddLessonCommand(String title, LessonType lessonType, int position) {
}
