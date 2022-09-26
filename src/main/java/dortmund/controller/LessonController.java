package dortmund.controller;

import dortmund.dto.request.LessonRequest;
import dortmund.dto.response.LessonResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.LessonResponseView;
import dortmund.serviceImpl.LessonServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("api/lessons")
@PreAuthorize("hasAuthority('ADMIN')")
public class LessonController {

    private final LessonServiceImpl lessonServiceImpl;

    public LessonController(LessonServiceImpl lessonServiceImpl) {
        this.lessonServiceImpl = lessonServiceImpl;
    }

    @PostMapping("/saveLesson")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'INSTRUCTOR')")
    public LessonResponse saveLesson(@RequestBody LessonRequest lessonRequest) {
        return lessonServiceImpl.saveLesson(lessonRequest);
    }

    @GetMapping("/getAllLessons")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public List<LessonResponse> getLessons() {
        return lessonServiceImpl.getAllLessons();
    }

    @GetMapping("/getLesson/{lessonId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public LessonResponse getById(@PathVariable Long lessonId) {
        return lessonServiceImpl.getById(lessonId);
    }

    @DeleteMapping("/deleteLesson/{lessonId}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'INSTRUCTOR')")
    public SimpleResponse deleteById(@PathVariable Long lessonId) {
        return lessonServiceImpl.deleteLessonById(lessonId);
    }

    @PutMapping("/updateLesson/{lessonId}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'INSTRUCTOR')")
    public LessonResponse updateLessonById(@PathVariable Long lessonId,
                                           @RequestBody LessonRequest lessonRequest) {
        return lessonServiceImpl.updateLessonById(lessonId, lessonRequest);
    }

    @GetMapping("/paginationOfLessons")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public LessonResponseView getPaginationOfLessons(@RequestParam int page,
                                                     @RequestParam int size) {
        return lessonServiceImpl.getPaginationOfLessons(page, size);
    }

}