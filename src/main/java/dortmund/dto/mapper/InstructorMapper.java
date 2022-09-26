package dortmund.dto.mapper;

import dortmund.dto.request.InstructorRequest;
import dortmund.dto.response.CourseResponse;
import dortmund.dto.response.InstructorResponse;
import dortmund.entity.Company;
import dortmund.entity.Course;
import dortmund.entity.Instructor;
import dortmund.repository.CompanyRepository;
import dortmund.repository.InstructorRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InstructorMapper {

    private final CompanyRepository companyRepository;

    private final InstructorRepository instructorRepository;

    public InstructorMapper(CompanyRepository companyRepository, InstructorRepository instructorRepository) {
        this.companyRepository = companyRepository;
        this.instructorRepository = instructorRepository;
    }

    public Instructor mapToEntity(InstructorRequest instructorRequest) {
        Company company = companyRepository.findById(instructorRequest.getCompanyId()).get();
        Instructor instructor = new Instructor();
        instructor.setFullName(instructorRequest.getFirstName() + " " + instructorRequest.getLastName());
        instructor.setPhoneNumber(instructorRequest.getPhoneNumber());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setSpecialization(instructorRequest.getSpecialization());
        instructor.setCreated(LocalDateTime.now());
        instructor.setActive(true);
        instructor.setCompany(company);
        company.setInstructors(List.of(instructor));
        return instructorRepository.save(instructor);
    }

    public InstructorResponse mapToResponse(Instructor instructor) {
        InstructorResponse instructorResponse = new InstructorResponse();
        instructorResponse.setInstructorId(instructor.getId());
        instructorResponse.setFullName(instructor.getFullName());
        String[] array = instructor.getFullName().split(" ", 2);
        instructorResponse.setFirstName(array[0]);
        instructorResponse.setLastName(array[1]);
        instructorResponse.setPhoneNumber(instructor.getPhoneNumber());
        instructorResponse.setEmail(instructor.getEmail());
        instructorResponse.setSpecialization(instructor.getSpecialization());

        List<CourseResponse> courseResponses = new ArrayList<>();
        for (Course c : instructor.getCourses()) {
            CourseResponse course = new CourseResponse(c.getId(), c.getCourseName(),
                    c.getDateOfStart(), c.getDuration(), c.getDescription(), c.getImage());
            courseResponses.add(course);

            instructorResponse.setCreated(LocalDateTime.now());
            instructorResponse.setActive(instructor.isActive());
        }
        instructorResponse.setCourseResponses(courseResponses);
        return instructorResponse;
    }

    public Instructor updateInstructor(Instructor instructor, InstructorRequest instructorRequest) {
        Company company = companyRepository.findById(instructorRequest.getCompanyId()).get();
        instructor.setFullName(instructorRequest.getFirstName() + " " + instructorRequest.getLastName());
        instructor.setPhoneNumber(instructorRequest.getPhoneNumber());
        instructor.setEmail(instructorRequest.getEmail());
        instructor.setSpecialization(instructorRequest.getSpecialization());
        instructor.setCreated(LocalDateTime.now());
        instructor.setActive(true);
        instructor.setCompany(company);
        return instructorRepository.save(instructor);
    }

}