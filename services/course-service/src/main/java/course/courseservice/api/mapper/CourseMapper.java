package course.courseservice.api.mapper;

import course.courseservice.api.dto.request.AddCourseAssetRequest;
import course.courseservice.api.dto.request.AddLessonRequest;
import course.courseservice.api.dto.request.AddModuleRequest;
import course.courseservice.api.dto.request.AssignCourseCategoriesRequest;
import course.courseservice.api.dto.request.CreateCourseRequest;
import course.courseservice.api.dto.request.MoveLessonRequest;
import course.courseservice.api.dto.request.MoveModuleRequest;
import course.courseservice.api.dto.request.UpdateCourseRequest;
import course.courseservice.application.command.course.AddCourseAssetCommand;
import course.courseservice.application.command.course.AddLessonCommand;
import course.courseservice.application.command.course.AddModuleCommand;
import course.courseservice.application.command.course.AssignCourseCategoriesCommand;
import course.courseservice.application.command.course.CreateCourseCommand;
import course.courseservice.application.command.course.MoveLessonCommand;
import course.courseservice.application.command.course.MoveModuleCommand;
import course.courseservice.application.command.course.UpdateCourseCommand;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CreateCourseCommand toCommand(CreateCourseRequest request) {
        return new CreateCourseCommand(
                request.getManagerId(),
                request.getTitle(),
                request.getShortDescription(),
                request.getDescription(),
                request.getPrice(),
                request.getDifficultyLevel()
        );
    }

    public UpdateCourseCommand toCommand(UpdateCourseRequest request) {
        return new UpdateCourseCommand(
                request.getTitle(),
                request.getSlug(),
                request.getShortDescription(),
                request.getDescription(),
                request.getThumbnailUrl(),
                request.getPrice(),
                request.getDifficultyLevel()
        );
    }

    public AssignCourseCategoriesCommand toCommand(AssignCourseCategoriesRequest request) {
        return new AssignCourseCategoriesCommand(request.getCategoryIds());
    }

    public AddModuleCommand toCommand(AddModuleRequest request) {
        return new AddModuleCommand(request.getTitle(), request.getPosition());
    }

    public MoveModuleCommand toCommand(MoveModuleRequest request) {
        return new MoveModuleCommand(request.getPosition());
    }

    public AddLessonCommand toCommand(AddLessonRequest request) {
        return new AddLessonCommand(
                request.getTitle(),
                request.getLessonType(),
                request.getPosition()
        );
    }

    public MoveLessonCommand toCommand(MoveLessonRequest request) {
        return new MoveLessonCommand(request.getPosition());
    }

    public AddCourseAssetCommand toCommand(AddCourseAssetRequest request) {
        return new AddCourseAssetCommand(
                request.getAssetType(),
                request.getAssetUrl(),
                request.getFileName(),
                request.getFileSize()
        );
    }
}
