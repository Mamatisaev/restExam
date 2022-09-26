package dortmund.serviceImpl;

import dortmund.dto.mapper.CourseMapper;
import dortmund.dto.request.CourseRequest;
import dortmund.dto.response.CourseResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.CourseResponseView;
import dortmund.entity.Course;
import dortmund.exception.NotFoundException;
import dortmund.repository.CourseRepository;
import dortmund.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseResponse saveCourse(CourseRequest courseRequest) {
        Course course = courseMapper.mapToEntity(courseRequest);
        return courseMapper.mapToResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        List<CourseResponse> courseResponses = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            courseResponses.add(courseMapper.mapToResponse(course));
        }
        return courseResponses;
    }

    @Override
    public CourseResponse getById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException(String.format("Course with id : %s courseId  is not found.",courseId)));
        return courseMapper.mapToResponse(course);
    }

    @Override
    public SimpleResponse deleteCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course with id " + courseId + " is not found."));
        courseRepository.delete(course);
        return new SimpleResponse("DELETED", "Course with id " + courseId + " is successfully deleted.");
    }

    @Override
    public CourseResponse updateCourseById(Long courseId, CourseRequest courseRequest) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course with id " + courseId + " is not found."));
        Course course1 = courseMapper.updateCourse(course, courseRequest);
        return courseMapper.mapToResponse(course1);
    }


    private List<CourseResponse> getAllCoursesPagination2(List<Course> courses) {
        List<CourseResponse> courseResponses = new ArrayList<>();
        for (Course course :courses) {
            courseResponses.add(courseMapper.mapToResponse(course));
        }
        return courseResponses;
    }

    private List<Course> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return courseRepository.searchByCourseName(text.toUpperCase(Locale.ROOT),pageable);
    }

    @Override
    public CourseResponseView getAllCoursesPagination(String text, int page , int size) {
        CourseResponseView courseResponseView = new CourseResponseView();
        Pageable pageable = PageRequest.of(page - 1 , size, Sort.by("courseName"));
        courseResponseView.setCourseResponses(getAllCoursesPagination2(search(text,pageable)));
        List<CourseResponse> courseResponses = new ArrayList<>();
        Page<Course> allCourses = courseRepository.findAll(pageable);
        for (Course course : allCourses) {
            courseResponses.add(courseMapper.mapToResponse(course));
        }
        courseResponseView.setCourseResponses(courseResponses);
        courseResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        courseResponseView.setTotalPages(allCourses.getTotalPages());
        return courseResponseView;
    }

}