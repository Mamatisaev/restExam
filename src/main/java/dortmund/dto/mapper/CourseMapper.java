package dortmund.dto.mapper;

import dortmund.dto.request.CourseRequest;
import dortmund.dto.response.CourseResponse;
import dortmund.entity.Company;
import dortmund.entity.Course;
import dortmund.repository.CompanyRepository;
import dortmund.repository.CourseRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {

    private final CompanyRepository companyRepository;

    private final CourseRepository courseRepository;

    public CourseMapper(CompanyRepository companyRepository, CourseRepository courseRepository) {
        this.companyRepository = companyRepository;
        this.courseRepository = courseRepository;
    }

    public Course mapToEntity(CourseRequest courseRequest) {
        Company company = companyRepository.findById(courseRequest.getCompanyId()).get();
        Course course = new Course();
        course.setCourseName(courseRequest.getCourseName());
        course.setDateOfStart(courseRequest.getDateOfStart());
        course.setDescription(courseRequest.getDescription());
        course.setImage(courseRequest.getImage());
        course.setDuration(courseRequest.getDuration());
        course.setCompany(company);
        company.setCourses(List.of(course));
        return courseRepository.save(course);
    }

    public CourseResponse mapToResponse(Course course) {
    CourseResponse courseResponse = new CourseResponse();
    courseResponse.setCourseId(course.getId());
    courseResponse.setCourseName(course.getCourseName());
    courseResponse.setDateOfStart(course.getDateOfStart());
    courseResponse.setDescription(course.getDescription());
    courseResponse.setDuration(course.getDuration());
    courseResponse.setImage(course.getImage());
    return courseResponse;
    }

    public Course updateCourse(Course course, CourseRequest courseRequest) {
        Company company = companyRepository.findById(courseRequest.getCompanyId()).get();
        course.setCourseName(courseRequest.getCourseName());
        course.setDateOfStart(courseRequest.getDateOfStart());
        course.setDuration(courseRequest.getDuration());
        course.setImage(courseRequest.getImage());
        course.setDescription(courseRequest.getDescription());
        course.setCompany(company);
        company.setCourses(List.of(course));
        return courseRepository.save(course);
    }

}