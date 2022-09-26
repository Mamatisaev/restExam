package dortmund.serviceImpl;

import dortmund.dto.mapper.InstructorMapper;
import dortmund.dto.request.InstructorRequest;
import dortmund.dto.response.InstructorResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.InstructorResponseView;
import dortmund.entity.Course;
import dortmund.entity.Instructor;
import dortmund.exception.NotFoundException;
import dortmund.repository.CourseRepository;
import dortmund.repository.InstructorRepository;
import dortmund.service.InstructorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final CourseRepository courseRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository, InstructorMapper instructorMapper, CourseRepository courseRepository) {
        this.instructorRepository = instructorRepository;
        this.instructorMapper = instructorMapper;
        this.courseRepository = courseRepository;
    }

    @Override
    public InstructorResponse saveInstructor(InstructorRequest instructorRequest) {
        Instructor instructor = instructorMapper.mapToEntity(instructorRequest);
        return instructorMapper.mapToResponse(instructor);
    }

    @Override
    public InstructorResponse assignInstructorToCourse(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(
                () -> new NotFoundException(String.format("Instructor with id %s is not found.", instructorId)));
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException(String.format("Course with id %s is not found.", courseId)));
        instructor.addCourse(course);
        course.addInstructors(instructor);
        instructorRepository.save(instructor);
        return instructorMapper.mapToResponse(instructor);
    }

    @Override
    public String assignInstructor(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(
                () -> new NotFoundException(String.format("Instructor with id %s is not found.", instructorId)));
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException(String.format("Course with id %s is not found.", courseId)));
        instructor.addCourse(course);
        course.addInstructors(instructor);
        instructorRepository.save(instructor);
        return String.format("Instructor with id %s is assigned to course.", instructorId);
    }

    @Override
    public List<InstructorResponse> getAllInstructors() {
        List<InstructorResponse> instructorResponses = new ArrayList<>();
        for (Instructor instructor : instructorRepository.findAll()) {
            instructorResponses.add(instructorMapper.mapToResponse(instructor));
        }
        return instructorResponses;
    }

    @Override
    public InstructorResponse getById(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(
                () -> new NotFoundException("Instructor with id " + instructorId + " is not found."));
        return instructorMapper.mapToResponse(instructor);
    }

    @Override
    public SimpleResponse deleteInstructorById(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(
                () -> new NotFoundException("Instructor with id " + instructorId + " is not found."));
        instructorRepository.delete(instructor);
        return new SimpleResponse("DELETED", "Instructor with id " + instructorId + " is successfully deleted.");
    }

    @Override
    public InstructorResponse updateInstructorById(Long instructorId, InstructorRequest instructorRequest) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(
                () -> new NotFoundException("Instructor with id " + instructorId + " is not found."));
        Instructor instructor1 = instructorMapper.updateInstructor(instructor, instructorRequest);
        return instructorMapper.mapToResponse(instructor1);
    }

    @Override
    public InstructorResponseView getPaginationOfInstructors(int page, int size) {
        InstructorResponseView instructorResponseView = new InstructorResponseView();
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("fullName"));
        List<InstructorResponse> instructorResponses = new ArrayList<>();
        Page<Instructor> allInstructors = instructorRepository.findAll(pageable);
        for (Instructor instructor : allInstructors) {
            instructorResponses.add(instructorMapper.mapToResponse(instructor));
        }
        instructorResponseView.setInstructorResponses(instructorResponses);
        instructorResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        instructorResponseView.setTotalPages(allInstructors.getTotalPages());
        return instructorResponseView;
    }

}