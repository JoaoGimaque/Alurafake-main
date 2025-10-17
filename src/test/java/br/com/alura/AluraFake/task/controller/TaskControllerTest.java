package br.com.alura.AluraFake.task.controller;

import br.com.alura.AluraFake.task.dto.NewTaskDTO;
import br.com.alura.AluraFake.task.dto.TaskListItemDTO;
import br.com.alura.AluraFake.task.dto.TaskOptionDTO;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.model.Type;
import br.com.alura.AluraFake.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void newOpenText_shouldCreateOpenTextTask() {
        NewTaskDTO dto = new NewTaskDTO();
        dto.setOrder(1);
        dto.setStatement("Explique o conceito de polimorfismo");
        dto.setCourseId(10L);

        Task expectedTask = new Task();
        expectedTask.setId(1L);
        expectedTask.setType(Type.OPEN_TEXT);
        expectedTask.setStatement(dto.getStatement());
        expectedTask.setTaskOrder(dto.getOrder());

        when(taskService.createTask(eq(dto.getCourseId()), any(Task.class))).thenReturn(expectedTask);


        ResponseEntity<Task> response = taskController.newOpenText(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTask, response.getBody());
    }

    @Test
    void newSingleChoice_shouldCreateSingleChoiceTask() {
        NewTaskDTO dto = new NewTaskDTO();
        dto.setOrder(2);
        dto.setStatement("Qual é a capital da França?");
        dto.setCourseId(20L);
        dto.setOptions(List.of(
                new TaskOptionDTO("Paris", true),
                new TaskOptionDTO("Londres", false)
        ));

        Task expectedTask = new Task();
        expectedTask.setId(2L);
        expectedTask.setType(Type.SINGLE_CHOICE);
        expectedTask.setStatement(dto.getStatement());
        expectedTask.setTaskOrder(dto.getOrder());

        when(taskService.createTask(eq(dto.getCourseId()), any(Task.class))).thenReturn(expectedTask);


        ResponseEntity<Task> response = taskController.newSingleChoice(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTask, response.getBody());
    }

    @Test
    void newMultipleChoice_shouldCreateMultipleChoiceTask() {
        NewTaskDTO dto = new NewTaskDTO();
        dto.setOrder(3);
        dto.setStatement("Quais são linguagens de programação?");
        dto.setCourseId(30L);
        dto.setOptions(List.of(
                new TaskOptionDTO("Java", true),
                new TaskOptionDTO("Python", true),
                new TaskOptionDTO("HTML", false)
        ));

        Task expectedTask = new Task();
        expectedTask.setId(3L);
        expectedTask.setType(Type.MULTIPLE_CHOICE);
        expectedTask.setStatement(dto.getStatement());
        expectedTask.setTaskOrder(dto.getOrder());

        when(taskService.createTask(eq(dto.getCourseId()), any(Task.class))).thenReturn(expectedTask);


        ResponseEntity<Task> response = taskController.newMultipleChoice(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTask, response.getBody());
    }

    @Test
    void listAllTasks_shouldReturnTaskList() {
        TaskListItemDTO item = new TaskListItemDTO(1L, 1L,"Pergunta?", Type.OPEN_TEXT,1);
        when(taskService.getAllTasks()).thenReturn(List.of(item));

        ResponseEntity<List<TaskListItemDTO>> response = taskController.listAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(item, response.getBody().get(0));
    }
}
