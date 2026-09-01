package course.courseservice.application.usecase.course;

import course.courseservice.application.command.course.AddCourseAssetCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
import course.courseservice.application.service.CourseApplicationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record AddCourseAssetUseCase(CourseApplicationService courseApplicationService) {

    public ApiResponse<CourseResponse> execute(UUID id, AddCourseAssetCommand command) {
        return ApiResponse.success("Course asset added successfully", courseApplicationService.addAsset(id, command));
    }
}
