package dortmund.dto.mapper;

import dortmund.dto.request.StudentRequest;
import dortmund.dto.response.StudentResponse;
import dortmund.entity.Company;
import dortmund.entity.Student;
import dortmund.repository.CompanyRepository;
import dortmund.repository.StudentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StudentMapper {

    private final CompanyRepository companyRepository;

    private final StudentRepository studentRepository;

    public StudentMapper(CompanyRepository companyRepository, StudentRepository studentRepository) {
        this.companyRepository = companyRepository;
        this.studentRepository = studentRepository;
    }

    public Student mapToEntity(StudentRequest studentRequest) {
        Company company = companyRepository.findById(studentRequest.getCompanyId()).get();
        Student student = new Student();
        student.setFullName(studentRequest.getFirstName() + " " + studentRequest.getLastName());
        student.setPhoneNumber(studentRequest.getPhoneNumber());
        student.setEmail(studentRequest.getEmail());
        student.setStudyFormat(studentRequest.getStudyFormat());
        student.setCreated(LocalDateTime.now());
        student.setActive(true);
        student.setCompany(company);
        company.setStudents(List.of(student));
        return studentRepository.save(student);
    }

    public StudentResponse mapToResponse(Student student) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setStudentId(student.getId());
        studentResponse.setFullName(student.getFullName());
        String [] array = student.getFullName().split(" ", 2);
        studentResponse.setFirstName(array[0]);
        studentResponse.setLastName(array[1]);
        studentResponse.setPhoneNumber(student.getPhoneNumber());
        studentResponse.setEmail(student.getEmail());
        studentResponse.setStudyFormat(student.getStudyFormat());
        studentResponse.setCreated(LocalDateTime.now());
        studentResponse.setActive(student.isActive());
        return studentResponse;
    }

    public Student updateStudent(Student student, StudentRequest studentRequest) {
        Company company = companyRepository.findById(studentRequest.getCompanyId()).get();
        student.setFullName(studentRequest.getFirstName() + " " + studentRequest.getLastName());
        student.setPhoneNumber(studentRequest.getPhoneNumber());
        student.setEmail(studentRequest.getEmail());
        student.setStudyFormat(studentRequest.getStudyFormat());
        student.setCreated(LocalDateTime.now());
        student.setActive(true);
        student.setCompany(company);
        return studentRepository.save(student);
    }

}