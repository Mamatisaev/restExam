package dortmund.controller;

import dortmund.dto.request.TaskRequest;
import dortmund.dto.response.TaskResponse;
import dortmund.dto.responseView.TaskResponseView;
import dortmund.serviceImpl.TaskServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("api/tasks")
@PreAuthorize("hasAuthority('ADMIN')")
public class TaskController {

    private final TaskServiceImpl taskServiceImpl;

    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @PostMapping("/saveTask")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public TaskResponse saveTask(@RequestBody TaskRequest taskRequest) {
        return taskServiceImpl.saveTask(taskRequest);
    }

    @GetMapping("/getAllTasks")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public List<TaskResponse> getTasks() {
        return taskServiceImpl.getAllTasks();
    }

    @GetMapping("/getTask/{taskId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public TaskResponse getById(@PathVariable Long taskId) {
        return taskServiceImpl.getById(taskId);
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @DeleteMapping("/deleteTask/{taskId}")
    public TaskResponse deleteTask(@PathVariable Long taskId) {
        return taskServiceImpl.deleteTask(taskId);
    }

    @PutMapping("/updateTask/{taskId}")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public TaskResponse updateTaskById(@PathVariable Long taskId,
                                       @RequestBody TaskRequest taskRequest) {
        return taskServiceImpl.updateTaskById(taskId, taskRequest);
    }

    @GetMapping("/paginationOfTasks")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public TaskResponseView getPaginationOfTasks(@RequestParam int page,
                                                 @RequestParam int size) {
        return taskServiceImpl.getPaginationOfTasks(page, size);
    }

}