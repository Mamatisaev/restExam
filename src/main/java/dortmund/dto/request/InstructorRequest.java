package dortmund.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InstructorRequest {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String specialization;

    private Long companyId;

}
