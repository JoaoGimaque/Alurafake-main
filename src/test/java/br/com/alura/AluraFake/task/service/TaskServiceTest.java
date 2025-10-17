package br.com.alura.AluraFake.task.service;

import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.model.Status;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.repository.TaskRepository;
import br.com.alura.AluraFake.task.validator.TaskValidator;
import br.com.alura.AluraFake.user.model.Role;
import br.com.alura.AluraFake.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TaskValidator taskValidator;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_shouldSaveTask_whenValid() {
        Long courseId = 1L;
        Course course = new Course("Java", "Curso completo", new User("João", "joao@alura.com.br", Role.INSTRUCTOR));
        course.setId(courseId);
        course.setStatus(Status.BUILDING);

        Task task = new Task();
        task.setStatement("O que é polimorfismo?");
        task.setTaskOrder(1);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(taskRepository.existsByCourseIdAndStatement(courseId, task.getStatement())).thenReturn(false);
        when(taskRepository.findByCourseIdOrderByTaskOrder(courseId)).thenReturn(List.of());
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task saved = taskService.createTask(courseId, task);

        assertEquals(course, saved.getCourse());
        assertEquals(Status.BUILDING, saved.getCourse().getStatus());
        assertNotNull(saved.getCreatedAt());
        verify(taskValidator).validateTask(task);
        verify(taskRepository).save(task);
    }

    @Test
    void createTask_shouldThrow_whenCourseNotFound() {
        Long courseId = 99L;
        Task task = new Task();
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                taskService.createTask(courseId, task));

        assertEquals("Curso não encontrado", ex.getMessage());
    }

    @Test
    void createTask_shouldThrow_whenCourseIsPublished() {
        Long courseId = 1L;
        Course course = new Course("Java", "Curso completo", new User("João", "joao@alura.com.br", Role.INSTRUCTOR));
        course.setId(courseId);
        course.setStatus(Status.PUBLISHED);

        Task task = new Task();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Exception ex = assertThrows(IllegalStateException.class, () ->
                taskService.createTask(courseId, task));

        assertEquals("Não é possível adicionar atividades em cursos publicados", ex.getMessage());
    }

    @Test
    void createTask_shouldThrow_whenStatementAlreadyExists() {
        Long courseId = 1L;
        Course course = new Course("Java", "Curso completo", new User("João", "joao@alura.com.br", Role.INSTRUCTOR));
        course.setId(courseId);
        course.setStatus(Status.BUILDING);

        Task task = new Task();
        task.setStatement("Duplicado");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(taskRepository.existsByCourseIdAndStatement(courseId, "Duplicado")).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                taskService.createTask(courseId, task));

        assertEquals("Já existe uma atividade com esse enunciado no curso", ex.getMessage());
    }

    @Test
    void createTask_shouldThrow_whenOrderSkipsSequence() {
        Long courseId = 1L;
        Course course = new Course("Java", "Curso completo", new User("João", "joao@alura.com.br", Role.INSTRUCTOR));
        course.setId(courseId);
        course.setStatus(Status.BUILDING);

        Task task = new Task();
        task.setStatement("Nova questão");
        task.setTaskOrder(5);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(taskRepository.existsByCourseIdAndStatement(courseId, "Nova questão")).thenReturn(false);
        when(taskRepository.findByCourseIdOrderByTaskOrder(courseId)).thenReturn(List.of(
                createTask(1, 1),
                createTask(2, 2)
        ));

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                taskService.createTask(courseId, task));

        assertEquals("A ordem da atividade não pode pular números", ex.getMessage());
    }

    private Task createTask(long id, int order) {
        Task task = new Task();
        task.setId(id);
        task.setTaskOrder(order);
        return task;
    }
}
