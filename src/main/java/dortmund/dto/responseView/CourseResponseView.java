package dortmund.dto.responseView;

import dortmund.dto.response.CourseResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseResponseView {

    List<CourseResponse> courseResponses;

    private int currentPage;

    private int totalPages;

}
