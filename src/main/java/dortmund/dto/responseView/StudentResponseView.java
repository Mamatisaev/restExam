package dortmund.dto.responseView;

import dortmund.dto.response.StudentResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentResponseView {

    private List<StudentResponse> studentResponses;

    private int currentPage;

    private int totalPages;

}
