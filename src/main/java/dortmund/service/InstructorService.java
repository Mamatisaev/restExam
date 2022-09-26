package dortmund.service;

import dortmund.dto.request.InstructorRequest;
import dortmund.dto.response.InstructorResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.InstructorResponseView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstructorService {
    InstructorResponse saveInstructor(InstructorRequest instructorRequest);

    InstructorResponse assignInstructorToCourse(Long instructorId, Long courseId);

    String assignInstructor(Long instructorId, Long courseId);

    List<InstructorResponse> getAllInstructors();

    InstructorResponse getById(Long instructorId);

    SimpleResponse deleteInstructorById(Long instructorId);

    InstructorResponse updateInstructorById(Long instructorId, InstructorRequest instructorRequest);

    InstructorResponseView getPaginationOfInstructors(int page, int size);
}
