package dortmund.dto.responseView;

import dortmund.dto.response.InstructorResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InstructorResponseView {

    private List<InstructorResponse> instructorResponses;

    private int currentPage;

    private int totalPages;

}
