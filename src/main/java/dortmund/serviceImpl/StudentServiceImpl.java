package dortmund.serviceImpl;

import dortmund.dto.mapper.StudentMapper;
import dortmund.dto.request.StudentRequest;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.response.StudentResponse;
import dortmund.dto.responseView.StudentResponseView;
import dortmund.entity.Course;
import dortmund.entity.Student;
import dortmund.exception.NotFoundException;
import dortmund.repository.CourseRepository;
import dortmund.repository.StudentRepository;
import dortmund.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentMapper studentMapper, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public StudentResponse saveStudent(StudentRequest studentRequest) {
        Student student = studentMapper.mapToEntity(studentRequest);
        return studentMapper.mapToResponse(student);
    }

    @Override
    public StudentResponse assignStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException(String.format("Student with id %s is not found.", studentId)));
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException(String.format("Course with id %s is not found.", courseId)));
        student.setCourse(course);
        course.addStudents(student);
        studentRepository.save(student);
        return new StudentResponse();
    }

    @Override
    public String assignStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException(String.format("Student with id %s is not found.", studentId)));
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException(String.format("Course with id %s is not found.", courseId)));
        student.setCourse(course);
        course.addStudents(student);
        studentRepository.save(student);
        return String.format("Student with id %s is assigned to course.", studentId);
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            studentResponses.add(studentMapper.mapToResponse(student));
        }
        return studentResponses;
    }

    @Override
    public StudentResponse getById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student with id " + studentId + " is not found."));
        return studentMapper.mapToResponse(student);
    }

    @Override
    public SimpleResponse deleteStudentById(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student with id " + studentId + " is not found."));
        studentRepository.delete(student);
        return new SimpleResponse("DELETED", "Student with id " + studentId + " is successfully deleted.");
    }

    @Override
    public StudentResponse updateStudentById(Long studentId, StudentRequest studentRequest) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException(String.format("Student with id %s is not found.", studentId)));
        Student student1 = studentMapper.updateStudent(student, studentRequest);
        return studentMapper.mapToResponse(student1);
    }

    @Override
    public StudentResponseView getPaginationOfStudents(int page, int size) {
        StudentResponseView studentResponseView = new StudentResponseView();
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("fullName"));
        List<StudentResponse> studentResponses = new ArrayList<>();
        Page<Student> allStudents = studentRepository.findAll(pageable);
        for (Student student: allStudents) {
            studentResponses.add(studentMapper.mapToResponse(student));
        }
        studentResponseView.setStudentResponses(studentResponses);
        studentResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        studentResponseView.setTotalPages(allStudents.getTotalPages());
        return studentResponseView;
    }

}