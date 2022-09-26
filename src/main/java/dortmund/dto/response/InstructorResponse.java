package dortmund.dto.response;

import dortmund.entity.Course;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class InstructorResponse {

    private Long instructorId;

    private String fullName;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String specialization;

    private List<CourseResponse> courseResponses;

    private LocalDateTime created;

    private boolean isActive;

}
