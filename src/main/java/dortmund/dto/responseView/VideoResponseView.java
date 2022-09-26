package dortmund.dto.responseView;

import dortmund.dto.response.VideoResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VideoResponseView {

    private List<VideoResponse> videoResponses;

    private int currentPage;

    private int totalPages;

}