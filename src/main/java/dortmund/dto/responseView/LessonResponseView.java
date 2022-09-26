package dortmund.dto.responseView;

import dortmund.dto.response.LessonResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LessonResponseView {

    private List<LessonResponse> lessonResponses;

    private int currentPage;

    private int totalPages;

}
