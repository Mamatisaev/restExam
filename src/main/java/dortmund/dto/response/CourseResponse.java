package dortmund.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {

    private Long courseId;

    private String courseName;

    private LocalDate dateOfStart;

    private String duration;

    private String image;

    private String description;

}