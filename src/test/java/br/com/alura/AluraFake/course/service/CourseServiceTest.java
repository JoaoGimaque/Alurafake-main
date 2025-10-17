package br.com.alura.AluraFake.course.service;

import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.model.Status;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.course.validator.CourseValidator;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.model.Type;
import br.com.alura.AluraFake.task.repository.TaskRepository;
import br.com.alura.AluraFake.user.model.Role;
import br.com.alura.AluraFake.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CourseValidator courseValidator;

    @InjectMocks
    private CourseService courseService;

    @Test
    void activateCourse_shouldPublishCourse_whenValid() {
        Long courseId = 1L;
        Course course = new Course("Java", "Curso completo", new User("João", "joao@alura.com.br", Role.INSTRUCTOR));
        course.setId(courseId);

        Task task1 = new Task();
        task1.setStatement("task 1");
        task1.setType(Type.OPEN_TEXT);
        task1.setTaskOrder(1);
        task1.setCourse(course);
        task1.setCreatedAt(LocalDateTime.now());

        List<Task> tasks = List.of(task1);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(taskRepository.findByCourseIdOrderByTaskOrder(courseId)).thenReturn(tasks);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseService.activateCourse(courseId);

        assertEquals(Status.PUBLISHED, result.getStatus());
        assertNotNull(result.getPublishedAt());
        verify(courseValidator).validateBeforeActivate(course, tasks);
        verify(courseRepository).save(course);
    }

    @Test
    void activateCourse_shouldThrowException_whenCourseNotFound() {
        Long courseId = 99L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.activateCourse(courseId);
        });

        assertEquals("Curso não encontrado", exception.getMessage());
    }
}