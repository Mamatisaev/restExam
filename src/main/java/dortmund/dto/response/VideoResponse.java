package dortmund.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoResponse {

    private Long videoId;

    private String videoName;

    private String link;

    private LocalDateTime created;

    private boolean isActive;

}
