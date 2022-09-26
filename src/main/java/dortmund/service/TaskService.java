package dortmund.service;

import dortmund.dto.request.TaskRequest;
import dortmund.dto.response.TaskResponse;
import dortmund.dto.responseView.TaskResponseView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    TaskResponse saveTask(TaskRequest taskRequest);

    List<TaskResponse> getAllTasks();

    TaskResponse getById(Long taskId);

    TaskResponse deleteTask(Long taskId);

    TaskResponse updateTaskById(Long taskId, TaskRequest taskRequest);

    TaskResponseView getPaginationOfTasks(int page, int size);

}
