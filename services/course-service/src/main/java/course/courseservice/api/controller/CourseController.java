package course.courseservice.api.controller;

import course.courseservice.api.dto.request.AddCourseAssetRequest;
import course.courseservice.api.dto.request.AddLessonRequest;
import course.courseservice.api.dto.request.AddModuleRequest;
import course.courseservice.api.dto.request.AssignCourseCategoriesRequest;
import course.courseservice.api.dto.request.CreateCourseRequest;
import course.courseservice.api.dto.request.MoveLessonRequest;
import course.courseservice.api.dto.request.MoveModuleRequest;
import course.courseservice.api.dto.request.UpdateCourseRequest;
import course.courseservice.api.dto.response.CourseApiResponse;
import course.courseservice.application.command.course.AddCourseAssetCommand;
import course.courseservice.application.command.course.AddLessonCommand;
import course.courseservice.application.command.course.AddModuleCommand;
import course.courseservice.application.command.course.AssignCourseCategoriesCommand;
import course.courseservice.application.command.course.CreateCourseCommand;
import course.courseservice.application.command.course.MoveLessonCommand;
import course.courseservice.application.command.course.MoveModuleCommand;
import course.courseservice.application.command.course.UpdateCourseCommand;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.usecase.course.AddCourseAssetUseCase;
import course.courseservice.application.usecase.course.AddCourseLessonUseCase;
import course.courseservice.application.usecase.course.AddCourseModuleUseCase;
import course.courseservice.application.usecase.course.ArchiveCourseUseCase;
import course.courseservice.application.usecase.course.AssignCourseCategoriesUseCase;
import course.courseservice.application.usecase.course.CreateCourseUseCase;
import course.courseservice.application.usecase.course.DeleteCourseUseCase;
import course.courseservice.application.usecase.course.GetCourseByIdUseCase;
import course.courseservice.application.usecase.course.GetCourseBySlugUseCase;
import course.courseservice.application.usecase.course.GetCoursesByManagerUseCase;
import course.courseservice.application.usecase.course.MoveCourseLessonUseCase;
import course.courseservice.application.usecase.course.MoveCourseModuleUseCase;
import course.courseservice.application.usecase.course.PublishCourseUseCase;
import course.courseservice.application.usecase.course.RemoveCourseAssetUseCase;
import course.courseservice.application.usecase.course.RemoveCourseLessonUseCase;
import course.courseservice.application.usecase.course.RemoveCourseModuleUseCase;
import course.courseservice.application.usecase.course.ReturnCourseToDraftUseCase;
import course.courseservice.application.usecase.course.UpdateCourseUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPath.COURSE)
public class CourseController {

    private final CreateCourseUseCase createCourseUseCase;
    private final GetCourseByIdUseCase getCourseByIdUseCase;
    private final GetCourseBySlugUseCase getCourseBySlugUseCase;
    private final GetCoursesByManagerUseCase getCoursesByManagerUseCase;
    private final UpdateCourseUseCase updateCourseUseCase;
    private final PublishCourseUseCase publishCourseUseCase;
    private final ArchiveCourseUseCase archiveCourseUseCase;
    private final ReturnCourseToDraftUseCase returnCourseToDraftUseCase;
    private final AssignCourseCategoriesUseCase assignCourseCategoriesUseCase;
    private final AddCourseModuleUseCase addCourseModuleUseCase;
    private final MoveCourseModuleUseCase moveCourseModuleUseCase;
    private final RemoveCourseModuleUseCase removeCourseModuleUseCase;
    private final AddCourseLessonUseCase addCourseLessonUseCase;
    private final MoveCourseLessonUseCase moveCourseLessonUseCase;
    private final RemoveCourseLessonUseCase removeCourseLessonUseCase;
    private final AddCourseAssetUseCase addCourseAssetUseCase;
    private final RemoveCourseAssetUseCase removeCourseAssetUseCase;
    private final DeleteCourseUseCase deleteCourseUseCase;

    public CourseController(CreateCourseUseCase createCourseUseCase,
                            GetCourseByIdUseCase getCourseByIdUseCase,
                            GetCourseBySlugUseCase getCourseBySlugUseCase,
                            GetCoursesByManagerUseCase getCoursesByManagerUseCase,
                            UpdateCourseUseCase updateCourseUseCase,
                            PublishCourseUseCase publishCourseUseCase,
                            ArchiveCourseUseCase archiveCourseUseCase,
                            ReturnCourseToDraftUseCase returnCourseToDraftUseCase,
                            AssignCourseCategoriesUseCase assignCourseCategoriesUseCase,
                            AddCourseModuleUseCase addCourseModuleUseCase,
                            MoveCourseModuleUseCase moveCourseModuleUseCase,
                            RemoveCourseModuleUseCase removeCourseModuleUseCase,
                            AddCourseLessonUseCase addCourseLessonUseCase,
                            MoveCourseLessonUseCase moveCourseLessonUseCase,
                            RemoveCourseLessonUseCase removeCourseLessonUseCase,
                            AddCourseAssetUseCase addCourseAssetUseCase,
                            RemoveCourseAssetUseCase removeCourseAssetUseCase,
                            DeleteCourseUseCase deleteCourseUseCase) {
        this.createCourseUseCase = createCourseUseCase;
        this.getCourseByIdUseCase = getCourseByIdUseCase;
        this.getCourseBySlugUseCase = getCourseBySlugUseCase;
        this.getCoursesByManagerUseCase = getCoursesByManagerUseCase;
        this.updateCourseUseCase = updateCourseUseCase;
        this.publishCourseUseCase = publishCourseUseCase;
        this.archiveCourseUseCase = archiveCourseUseCase;
        this.returnCourseToDraftUseCase = returnCourseToDraftUseCase;
        this.assignCourseCategoriesUseCase = assignCourseCategoriesUseCase;
        this.addCourseModuleUseCase = addCourseModuleUseCase;
        this.moveCourseModuleUseCase = moveCourseModuleUseCase;
        this.removeCourseModuleUseCase = removeCourseModuleUseCase;
        this.addCourseLessonUseCase = addCourseLessonUseCase;
        this.moveCourseLessonUseCase = moveCourseLessonUseCase;
        this.removeCourseLessonUseCase = removeCourseLessonUseCase;
        this.addCourseAssetUseCase = addCourseAssetUseCase;
        this.removeCourseAssetUseCase = removeCourseAssetUseCase;
        this.deleteCourseUseCase = deleteCourseUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CourseApiResponse> create(@RequestBody CreateCourseRequest request) {
        return createCourseUseCase.execute(new CreateCourseCommand(
                request.managerId(),
                request.title(),
                request.shortDescription(),
                request.description(),
                request.price(),
                request.difficultyLevel()
        )).map(CourseApiResponse::from);
    }

    @GetMapping("/{id}")
    public ApiResponse<CourseApiResponse> getById(@PathVariable UUID id) {
        return getCourseByIdUseCase.execute(id).map(CourseApiResponse::from);
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse<CourseApiResponse> getBySlug(@PathVariable String slug) {
        return getCourseBySlugUseCase.execute(slug).map(CourseApiResponse::from);
    }

    @GetMapping("/managers/{managerId}")
    public ApiResponse<List<CourseApiResponse>> getByManagerId(@PathVariable UUID managerId) {
        return getCoursesByManagerUseCase.execute(managerId)
                .map(courses -> courses.stream().map(CourseApiResponse::from).toList());
    }

    @PutMapping("/{id}")
    public ApiResponse<CourseApiResponse> update(@PathVariable UUID id, @RequestBody UpdateCourseRequest request) {
        return updateCourseUseCase.execute(id, new UpdateCourseCommand(
                request.title(),
                request.slug(),
                request.shortDescription(),
                request.description(),
                request.thumbnailUrl(),
                request.price(),
                request.difficultyLevel()
        )).map(CourseApiResponse::from);
    }

    @PostMapping("/{id}/publish")
    public ApiResponse<CourseApiResponse> publish(@PathVariable UUID id) {
        return publishCourseUseCase.execute(id).map(CourseApiResponse::from);
    }

    @PostMapping("/{id}/archive")
    public ApiResponse<CourseApiResponse> archive(@PathVariable UUID id) {
        return archiveCourseUseCase.execute(id).map(CourseApiResponse::from);
    }

    @PostMapping("/{id}/draft")
    public ApiResponse<CourseApiResponse> returnToDraft(@PathVariable UUID id) {
        return returnCourseToDraftUseCase.execute(id).map(CourseApiResponse::from);
    }

    @PutMapping("/{id}/categories")
    public ApiResponse<CourseApiResponse> assignCategories(@PathVariable UUID id,
                                                           @RequestBody AssignCourseCategoriesRequest request) {
        return assignCourseCategoriesUseCase.execute(id, new AssignCourseCategoriesCommand(request.categoryIds()))
                .map(CourseApiResponse::from);
    }

    @PostMapping("/{id}/modules")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CourseApiResponse> addModule(@PathVariable UUID id, @RequestBody AddModuleRequest request) {
        return addCourseModuleUseCase.execute(id, new AddModuleCommand(request.title(), request.position()))
                .map(CourseApiResponse::from);
    }

    @PatchMapping("/{courseId}/modules/{moduleId}/position")
    public ApiResponse<CourseApiResponse> moveModule(@PathVariable UUID courseId,
                                                     @PathVariable UUID moduleId,
                                                     @RequestBody MoveModuleRequest request) {
        return moveCourseModuleUseCase.execute(courseId, moduleId, new MoveModuleCommand(request.position()))
                .map(CourseApiResponse::from);
    }

    @DeleteMapping("/{courseId}/modules/{moduleId}")
    public ApiResponse<Void> removeModule(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
        return removeCourseModuleUseCase.execute(courseId, moduleId);
    }

    @PostMapping("/{courseId}/modules/{moduleId}/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CourseApiResponse> addLesson(@PathVariable UUID courseId,
                                                    @PathVariable UUID moduleId,
                                                    @RequestBody AddLessonRequest request) {
        return addCourseLessonUseCase.execute(
                courseId,
                moduleId,
                new AddLessonCommand(request.title(), request.lessonType(), request.position())
        ).map(CourseApiResponse::from);
    }

    @PatchMapping("/{courseId}/modules/{moduleId}/lessons/{lessonId}/position")
    public ApiResponse<CourseApiResponse> moveLesson(@PathVariable UUID courseId,
                                                     @PathVariable UUID moduleId,
                                                     @PathVariable UUID lessonId,
                                                     @RequestBody MoveLessonRequest request) {
        return moveCourseLessonUseCase.execute(courseId, moduleId, lessonId, new MoveLessonCommand(request.position()))
                .map(CourseApiResponse::from);
    }

    @DeleteMapping("/{courseId}/modules/{moduleId}/lessons/{lessonId}")
    public ApiResponse<Void> removeLesson(@PathVariable UUID courseId,
                                          @PathVariable UUID moduleId,
                                          @PathVariable UUID lessonId) {
        return removeCourseLessonUseCase.execute(courseId, moduleId, lessonId);
    }

    @PostMapping("/{id}/assets")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CourseApiResponse> addAsset(@PathVariable UUID id, @RequestBody AddCourseAssetRequest request) {
        return addCourseAssetUseCase.execute(
                id,
                new AddCourseAssetCommand(request.assetType(), request.assetUrl(), request.fileName(), request.fileSize())
        ).map(CourseApiResponse::from);
    }

    @DeleteMapping("/{courseId}/assets/{assetId}")
    public ApiResponse<Void> removeAsset(@PathVariable UUID courseId, @PathVariable UUID assetId) {
        return removeCourseAssetUseCase.execute(courseId, assetId);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        return deleteCourseUseCase.execute(id);
    }
}
