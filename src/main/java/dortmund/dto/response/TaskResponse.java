package dortmund.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskResponse {

    private Long taskId;

    private String taskName;

    private String taskText;

    private LocalDate deadline;

    private LocalDateTime created;

    private boolean isActive;

}
