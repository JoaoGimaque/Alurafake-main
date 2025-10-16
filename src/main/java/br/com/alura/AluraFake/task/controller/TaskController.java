package br.com.alura.AluraFake.task.controller;

import br.com.alura.AluraFake.task.dto.NewTaskDTO;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.model.TaskOption;
import br.com.alura.AluraFake.task.model.Type;
import br.com.alura.AluraFake.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private Task buildTaskFromDto(NewTaskDTO dto, Type type) {
        Task task = new Task();
        task.setTaskOrder(dto.getOrder());
        task.setStatement(dto.getStatement());
        task.setType(type);

        if (dto.getOptions() != null && task.getType() != Type.OPEN_TEXT) {
            List<TaskOption> options = dto.getOptions().stream()
                    .map(o -> new TaskOption(task, o.getOption(), o.isCorrect()))
                    .toList();
            task.setOptions(options);
        }

        return task;
    }


    @PostMapping("/new/opentext")
    public ResponseEntity<Task> newOpenText(@RequestBody NewTaskDTO dto) {
        Task task = buildTaskFromDto(dto, Type.OPEN_TEXT);
        Task saved = taskService.createTask(dto.getCourseId(), task);

        return ResponseEntity.ok(saved);
    }

    @PostMapping("/new/singlechoice")
    public ResponseEntity<Task> newSingleChoice(@RequestBody NewTaskDTO dto) {
        Task task = buildTaskFromDto(dto, Type.SINGLE_CHOICE);
        Task saved = taskService.createTask(dto.getCourseId(), task);

        return ResponseEntity.ok(saved);
    }

    @PostMapping("/new/multiplechoice")
    public ResponseEntity<Task> newMultipleChoice(@RequestBody NewTaskDTO dto) {
        Task task = buildTaskFromDto(dto, Type.MULTIPLE_CHOICE);
        Task saved = taskService.createTask(dto.getCourseId(), task);

        return ResponseEntity.ok(saved);
    }

}