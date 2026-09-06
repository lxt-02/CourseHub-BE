package course.courseservice.api.dto.request;

import course.courseservice.domain.model.course.enums.LessonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddLessonRequest {
    private String title;
    private LessonType lessonType;
    private int position;
}
