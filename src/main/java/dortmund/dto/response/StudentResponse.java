package dortmund.dto.response;

import dortmund.entity.enums.StudyFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentResponse {

    private Long studentId;

    private String fullName;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private StudyFormat studyFormat;

    private LocalDateTime created;

    private boolean isActive;

}
