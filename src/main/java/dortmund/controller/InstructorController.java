package dortmund.controller;

import dortmund.dto.request.InstructorRequest;
import dortmund.dto.response.InstructorResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.InstructorResponseView;
import dortmund.serviceImpl.InstructorServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("api/instructors")
@PreAuthorize("hasAuthority('MANAGER')")
public class InstructorController {
    private final InstructorServiceImpl instructorServiceImpl;

    public InstructorController(InstructorServiceImpl instructorServiceImpl) {
        this.instructorServiceImpl = instructorServiceImpl;
    }

    @PostMapping("/saveInstructor")
    public InstructorResponse saveInstructor(@RequestBody InstructorRequest instructorRequest) {
        return instructorServiceImpl.saveInstructor(instructorRequest);
    }

    @PostMapping("/{instructorId}/{courseId}/assignInstructorToCourse")
    public InstructorResponse assignInstructorToCourse(@PathVariable Long instructorId,
                                                       @PathVariable Long courseId) {
        return instructorServiceImpl.assignInstructorToCourse(instructorId, courseId);
    }

    @PostMapping("{instructorId}/{courseId}/assignInstructor")
    public String assignInstructor(@PathVariable Long instructorId, @PathVariable Long courseId) {
        return instructorServiceImpl.assignInstructor(instructorId, courseId);
    }

    @GetMapping("/getAllInstructors")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public List<InstructorResponse> getInstructors() {
        return instructorServiceImpl.getAllInstructors();
    }

    @GetMapping("/getInstructor/{instructorId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public InstructorResponse getById(@PathVariable Long instructorId) {
        return instructorServiceImpl.getById(instructorId);
    }

    @DeleteMapping("/deleteInstructor/{instructorId}")
    public SimpleResponse deleteById(@PathVariable Long instructorId) {
        return instructorServiceImpl.deleteInstructorById(instructorId);
    }

    @PutMapping("/updateInstructor/{instructorId}")
    public InstructorResponse updateInstructorById(@PathVariable Long instructorId,
                                                   @RequestBody InstructorRequest instructorRequest) {
        return instructorServiceImpl.updateInstructorById(instructorId, instructorRequest);
    }

    @GetMapping("/paginationOfInstructors")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public InstructorResponseView getPaginationOfInstructors(@RequestParam int page,
                                                             @RequestParam int size) {
        return instructorServiceImpl.getPaginationOfInstructors(page, size);
    }

}