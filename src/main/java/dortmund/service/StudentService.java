package dortmund.service;

import dortmund.dto.request.StudentRequest;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.response.StudentResponse;
import dortmund.dto.responseView.StudentResponseView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    StudentResponse saveStudent(StudentRequest studentRequest);

    StudentResponse assignStudentToCourse(Long studentId, Long courseId);

    String assignStudent(Long instructorId, Long courseId);

    List<StudentResponse> getAllStudents();

    StudentResponse getById(Long studentId);

    SimpleResponse deleteStudentById(Long studentId);

    StudentResponse updateStudentById(Long studentId, StudentRequest studentRequest);

    StudentResponseView getPaginationOfStudents(int page, int size);

}
