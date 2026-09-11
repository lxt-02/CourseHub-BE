package course.courseservice.api.controller;

import course.courseservice.api.dto.request.AddCourseAssetRequest;
import course.courseservice.api.dto.request.AddLessonRequest;
import course.courseservice.api.dto.request.AddModuleRequest;
import course.courseservice.api.dto.request.AssignCourseCategoriesRequest;
import course.courseservice.api.dto.request.CreateCourseRequest;
import course.courseservice.api.dto.request.MoveLessonRequest;
import course.courseservice.api.dto.request.MoveModuleRequest;
import course.courseservice.api.dto.request.UpdateCourseRequest;
import course.courseservice.api.mapper.CourseMapper;
import course.courseservice.application.dto.ApiResponse;
import course.courseservice.application.dto.course.CourseResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final CourseMapper courseMapper;

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
                            DeleteCourseUseCase deleteCourseUseCase,
                            CourseMapper courseMapper) {
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
        this.courseMapper = courseMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> create(@RequestBody CreateCourseRequest request) {
        return ResponseEntity.ok(createCourseUseCase.execute(courseMapper.toCommand(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(getCourseByIdUseCase.execute(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<CourseResponse>> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(getCourseBySlugUseCase.execute(slug));
    }

    @GetMapping("/managers/{managerId}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getByManagerId(@PathVariable UUID managerId) {
        return ResponseEntity.ok(getCoursesByManagerUseCase.execute(managerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> update(@PathVariable UUID id,
                                                              @RequestBody UpdateCourseRequest request) {
        return ResponseEntity.ok(updateCourseUseCase.execute(id, courseMapper.toCommand(request)));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<ApiResponse<CourseResponse>> publish(@PathVariable UUID id) {
        return ResponseEntity.ok(publishCourseUseCase.execute(id));
    }

    @PostMapping("/{id}/archive")
    public ResponseEntity<ApiResponse<CourseResponse>> archive(@PathVariable UUID id) {
        return ResponseEntity.ok(archiveCourseUseCase.execute(id));
    }

    @PostMapping("/{id}/draft")
    public ResponseEntity<ApiResponse<CourseResponse>> returnToDraft(@PathVariable UUID id) {
        return ResponseEntity.ok(returnCourseToDraftUseCase.execute(id));
    }

    @PutMapping("/{id}/categories")
    public ResponseEntity<ApiResponse<CourseResponse>> assignCategories(@PathVariable UUID id,
                                                                        @RequestBody AssignCourseCategoriesRequest request) {
        return ResponseEntity.ok(assignCourseCategoriesUseCase.execute(id, courseMapper.toCommand(request)));
    }

    @PostMapping("/{id}/modules")
    public ResponseEntity<ApiResponse<CourseResponse>> addModule(@PathVariable UUID id,
                                                                 @RequestBody AddModuleRequest request) {
        return ResponseEntity.ok(addCourseModuleUseCase.execute(id, courseMapper.toCommand(request)));
    }

    @PatchMapping("/{courseId}/modules/{moduleId}/position")
    public ResponseEntity<ApiResponse<CourseResponse>> moveModule(@PathVariable UUID courseId,
                                                                  @PathVariable UUID moduleId,
                                                                  @RequestBody MoveModuleRequest request) {
        return ResponseEntity.ok(moveCourseModuleUseCase.execute(courseId, moduleId, courseMapper.toCommand(request)));
    }

    @DeleteMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<ApiResponse<Void>> removeModule(@PathVariable UUID courseId, @PathVariable UUID moduleId) {
        return ResponseEntity.ok(removeCourseModuleUseCase.execute(courseId, moduleId));
    }

    @PostMapping("/{courseId}/modules/{moduleId}/lessons")
    public ResponseEntity<ApiResponse<CourseResponse>> addLesson(@PathVariable UUID courseId,
                                                                 @PathVariable UUID moduleId,
                                                                 @RequestBody AddLessonRequest request) {
        return ResponseEntity.ok(addCourseLessonUseCase.execute(
                courseId,
                moduleId,
                courseMapper.toCommand(request)
        ));
    }

    @PatchMapping("/{courseId}/modules/{moduleId}/lessons/{lessonId}/position")
    public ResponseEntity<ApiResponse<CourseResponse>> moveLesson(@PathVariable UUID courseId,
                                                                  @PathVariable UUID moduleId,
                                                                  @PathVariable UUID lessonId,
                                                                  @RequestBody MoveLessonRequest request) {
        return ResponseEntity.ok(moveCourseLessonUseCase.execute(courseId, moduleId, lessonId, courseMapper.toCommand(request)));
    }

    @DeleteMapping("/{courseId}/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<ApiResponse<Void>> removeLesson(@PathVariable UUID courseId,
                                                          @PathVariable UUID moduleId,
                                                          @PathVariable UUID lessonId) {
        return ResponseEntity.ok(removeCourseLessonUseCase.execute(courseId, moduleId, lessonId));
    }

    @PostMapping("/{id}/assets")
    public ResponseEntity<ApiResponse<CourseResponse>> addAsset(@PathVariable UUID id,
                                                                @RequestBody AddCourseAssetRequest request) {
        return ResponseEntity.ok(addCourseAssetUseCase.execute(
                id,
                courseMapper.toCommand(request)
        ));
    }

    @DeleteMapping("/{courseId}/assets/{assetId}")
    public ResponseEntity<ApiResponse<Void>> removeAsset(@PathVariable UUID courseId, @PathVariable UUID assetId) {
        return ResponseEntity.ok(removeCourseAssetUseCase.execute(courseId, assetId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(deleteCourseUseCase.execute(id));
    }
}
