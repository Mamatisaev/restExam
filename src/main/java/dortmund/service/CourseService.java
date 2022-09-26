package dortmund.service;

import dortmund.dto.request.CourseRequest;
import dortmund.dto.response.CourseResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.CourseResponseView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    CourseResponse saveCourse(CourseRequest courseRequest);

    List<CourseResponse> getAllCourses();

    CourseResponse getById(Long id);

    SimpleResponse deleteCourseById(Long courseId);

    CourseResponse updateCourseById(Long courseId, CourseRequest courseRequest);

    CourseResponseView getAllCoursesPagination(String text, int page , int size);

}
