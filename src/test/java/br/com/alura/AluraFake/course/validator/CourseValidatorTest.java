package br.com.alura.AluraFake.course.validator;

import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.model.Status;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.model.Type;
import br.com.alura.AluraFake.user.model.Role;
import br.com.alura.AluraFake.user.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseValidatorTest {

    private final CourseValidator validator = new CourseValidator();

    @Test
    void validateBeforeActivate_shouldPass_whenCourseIsValid() {
        Course course = new Course("Java", "Curso completo", new User("João", "joao@alura.com.br", Role.INSTRUCTOR));
        course.setStatus(Status.BUILDING);

        List<Task> tasks = List.of(
                createTask(1L, Type.OPEN_TEXT, 1),
                createTask(2L, Type.SINGLE_CHOICE, 2),
                createTask(3L, Type.MULTIPLE_CHOICE, 3)
        );

        assertDoesNotThrow(() -> validator.validateBeforeActivate(course, tasks));
    }

    @Test
    void validateBeforeActivate_shouldFail_whenCourseStatusIsNotBuilding() {
        Course course = new Course("Java", "Curso completo", new User("João", "joao@alura.com.br", Role.INSTRUCTOR));
        course.setStatus(Status.PUBLISHED);

        List<Task> tasks = List.of(
                createTask(1L, Type.OPEN_TEXT, 1),
                createTask(2L, Type.SINGLE_CHOICE, 2),
                createTask(3L, Type.MULTIPLE_CHOICE, 3)
        );

        Exception ex = assertThrows(IllegalStateException.class, () ->
                validator.validateBeforeActivate(course, tasks));

        assertEquals("Somente cursos em BUILDING podem ser publicados", ex.getMessage());
    }

    @Test
    void validateBeforeActivate_shouldFail_whenMissingTaskTypes() {
        Course course = new Course("Java", "Curso completo", new User("João", "joao@alura.com.br", Role.INSTRUCTOR));
        course.setStatus(Status.BUILDING);

        List<Task> tasks = List.of(
                createTask(1L, Type.OPEN_TEXT, 1),
                createTask(2L, Type.SINGLE_CHOICE, 2)
        );

        Exception ex = assertThrows(IllegalStateException.class, () ->
                validator.validateBeforeActivate(course, tasks));

        assertEquals("Curso deve ter ao menos uma atividade de cada tipo", ex.getMessage());
    }

    @Test
    void validateBeforeActivate_shouldFail_whenTaskOrderIsIncorrect() {
        Course course = new Course("Java", "Curso completo", new User("João", "joao@alura.com.br", Role.INSTRUCTOR));
        course.setStatus(Status.BUILDING);

        List<Task> tasks = List.of(
                createTask(1L, Type.OPEN_TEXT, 1),
                createTask(2L, Type.SINGLE_CHOICE, 3), // ordem incorreta
                createTask(3L, Type.MULTIPLE_CHOICE, 2)
        );

        Exception ex = assertThrows(IllegalStateException.class, () ->
                validator.validateBeforeActivate(course, tasks));

        assertTrue(ex.getMessage().contains("tem ordem incorreta"));
    }

    private Task createTask(Long id, Type type, int order) {
        Task task = new Task();
        task.setId(id);
        task.setType(type);
        task.setTaskOrder(order);
        return task;
    }
}
