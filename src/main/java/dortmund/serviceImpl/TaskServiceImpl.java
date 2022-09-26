package dortmund.serviceImpl;

import dortmund.dto.mapper.TaskMapper;
import dortmund.dto.request.TaskRequest;
import dortmund.dto.response.TaskResponse;
import dortmund.dto.responseView.TaskResponseView;
import dortmund.entity.Task;
import dortmund.exception.NotFoundException;
import dortmund.repository.TaskRepository;
import dortmund.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskMapper taskMapper, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse saveTask(TaskRequest taskRequest) {
        Task task = taskMapper.mapToEntity(taskRequest);
        return taskMapper.mapToResponse(task);
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : taskRepository.findAll()) {
            taskResponses.add(taskMapper.mapToResponse(task));
        }
        return taskResponses;
    }

    @Override
    public TaskResponse getById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new NotFoundException("Task with id " + taskId + " is not found."));
        return taskMapper.mapToResponse(task);
    }

    @Override
    public TaskResponse deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new NotFoundException("Task with id " + taskId + " doesn't exist."));
        task.setLesson(null);
        taskRepository.delete(task);
        return taskMapper.mapToResponse(task);
    }

    @Override
    public TaskResponse updateTaskById(Long taskId, TaskRequest taskRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new NotFoundException("Task with id " + taskId + " is ont found."));
        Task task1 = taskMapper.updateTask(task, taskRequest);
        return taskMapper.mapToResponse(task1);
    }

    @Override
    public TaskResponseView getPaginationOfTasks(int page, int size) {
        TaskResponseView taskResponseView = new TaskResponseView();
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("taskName"));
        List<TaskResponse> taskResponses = new ArrayList<>();
        Page<Task> allTasks = taskRepository.findAll(pageable);
        for (Task task : allTasks) {
            taskResponses.add(taskMapper.mapToResponse(task));
        }
        taskResponseView.setTaskResponses(taskResponses);
        taskResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        taskResponseView.setTotalPages(allTasks.getTotalPages());
        return taskResponseView;
    }

}