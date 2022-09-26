package dortmund.controller;

import dortmund.dto.request.StudentRequest;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.response.StudentResponse;
import dortmund.dto.responseView.StudentResponseView;
import dortmund.serviceImpl.StudentServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("api/students")
@PreAuthorize("hasAuthority('ADMIN')")
public class StudentController {

    public final StudentServiceImpl studentServiceImpl;

    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @PostMapping("/saveStudent")
    @PreAuthorize("hasAuthority('MANAGER')")
    public StudentResponse saveStudent(@RequestBody StudentRequest studentRequest) {
        return studentServiceImpl.saveStudent(studentRequest);
    }

    @PostMapping("/{studentId}/{courseId}/assignStudentToCourse")
    @PreAuthorize("hasAuthority('MANAGER')")
    public StudentResponse assignStudentToCourse(@PathVariable Long studentId,
                                                 @PathVariable Long courseId) {
        return studentServiceImpl.assignStudentToCourse(studentId, courseId);
    }

    @PostMapping("/{studentId}/{courseId}/assignStudent")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String assignStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        return studentServiceImpl.assignStudent(studentId, courseId);
    }

    @GetMapping("/getAllStudents")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public List<StudentResponse> getStudents() {
        return studentServiceImpl.getAllStudents();
    }

    @GetMapping("/getStudent/{studentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public StudentResponse getById(@PathVariable Long studentId) {
        return studentServiceImpl.getById(studentId);
    }

    @DeleteMapping("/deleteStudent/{studentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public SimpleResponse deleteById(@PathVariable Long studentId) {
        return studentServiceImpl.deleteStudentById(studentId);
    }

    @PutMapping("/updateStudent/{studentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public StudentResponse updateStudentById(@PathVariable Long studentId,
                                             @RequestBody StudentRequest studentRequest) {
        return studentServiceImpl.updateStudentById(studentId, studentRequest);
    }

    @GetMapping("/paginationOfStudents")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public StudentResponseView getPaginationOfStudents(@RequestParam int page,
                                                       @RequestParam int size) {
        return studentServiceImpl.getPaginationOfStudents(page, size);
    }

}