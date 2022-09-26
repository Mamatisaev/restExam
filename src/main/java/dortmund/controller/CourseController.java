package dortmund.controller;

import dortmund.dto.request.CourseRequest;
import dortmund.dto.response.CourseResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.CourseResponseView;
import dortmund.serviceImpl.CourseServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("api/courses")
@PreAuthorize("hasAuthority('ADMIN')")
public class CourseController {
    private final CourseServiceImpl courseServiceImpl;

    public CourseController(CourseServiceImpl courseServiceImpl) {
        this.courseServiceImpl = courseServiceImpl;
    }

    @PostMapping("/saveCourse")
    public CourseResponse saveCourse(@RequestBody CourseRequest courseRequest) {
        return courseServiceImpl.saveCourse(courseRequest);
    }

    @GetMapping("/getAllCourses")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public List<CourseResponse> getCourses() {
        return courseServiceImpl.getAllCourses();
    }

    @GetMapping("/getCourse/{courseId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public CourseResponse getById(@PathVariable Long courseId) {
        return courseServiceImpl.getById(courseId);
    }

    @DeleteMapping("/deleteCourse/{courseId}")
    public SimpleResponse deleteById(@PathVariable Long courseId) {
        return courseServiceImpl.deleteCourseById(courseId);
    }

    @PutMapping("/updateCourse/{courseId}")
    public CourseResponse updateCourseById(@PathVariable Long courseId,
                                           @RequestBody CourseRequest courseRequest) {
        return courseServiceImpl.updateCourseById(courseId, courseRequest);
    }

    @GetMapping("/coursePagination")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public CourseResponseView paginationResponse(@RequestParam(name = "text", required = false)
                                                 String text,
                                                 @RequestParam int page,
                                                 @RequestParam int size) {
        return courseServiceImpl.getAllCoursesPagination(text, page, size);
    }

}