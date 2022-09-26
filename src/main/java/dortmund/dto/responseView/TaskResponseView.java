package dortmund.dto.responseView;

import dortmund.dto.response.TaskResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskResponseView {

    private List<TaskResponse> taskResponses;

    private int currentPage;

    private int totalPages;

}
