package dortmund.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CourseRequest {

    private String courseName;

    private LocalDate dateOfStart;

    private String duration;

    private String image;

    private String description;

    private Long companyId;

}