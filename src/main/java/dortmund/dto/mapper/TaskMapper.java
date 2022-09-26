package dortmund.dto.mapper;

import dortmund.dto.request.TaskRequest;
import dortmund.dto.response.TaskResponse;
import dortmund.entity.Lesson;
import dortmund.entity.Task;
import dortmund.repository.LessonRepository;
import dortmund.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TaskMapper {

    private final LessonRepository lessonRepository;

    private final TaskRepository taskRepository;

    public TaskMapper(LessonRepository lessonRepository, TaskRepository taskRepository) {
        this.lessonRepository = lessonRepository;
        this.taskRepository = taskRepository;
    }

    public Task mapToEntity(TaskRequest taskRequest) {
        Lesson lesson = lessonRepository.findById(taskRequest.getLessonId()).get();
        Task task = new Task();
        task.setTaskName(taskRequest.getTaskName());
        task.setTaskText(taskRequest.getTaskText());
        task.setDeadline(taskRequest.getDeadline());
        task.setCreated(LocalDateTime.now());
        task.setActive(true);
        task.setLesson(lesson);
        lesson.setTasks(List.of(task));
        return taskRepository.save(task);
    }

    public TaskResponse mapToResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setTaskId(task.getId());
        taskResponse.setTaskName(task.getTaskName());
        taskResponse.setTaskText(task.getTaskText());
        taskResponse.setDeadline(task.getDeadline());
        taskResponse.setCreated(LocalDateTime.now());
        taskResponse.setActive(task.isActive());
        return taskResponse;
    }

    public Task updateTask(Task task, TaskRequest taskRequest) {
        Lesson lesson = lessonRepository.findById(taskRequest.getLessonId()).get();
        task.setTaskName(taskRequest.getTaskName());
        task.setTaskText(taskRequest.getTaskText());
        task.setDeadline(taskRequest.getDeadline());
        task.setCreated(LocalDateTime.now());
        task.setActive(true);
        task.setLesson(lesson);
        return taskRepository.save(task);
    }

}