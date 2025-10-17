package br.com.alura.AluraFake.task.repository;

import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.model.Status;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.model.Type;
import br.com.alura.AluraFake.user.model.Role;
import br.com.alura.AluraFake.user.model.User;
import br.com.alura.AluraFake.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindTasksOrderedByCourseId() {
        User instructor = new User("João", "joao@email.com", Role.INSTRUCTOR);
        userRepository.save(instructor);

        Course course = new Course("Java", "Curso completo", instructor);
        course.setStatus(Status.BUILDING);
        courseRepository.save(course);

        Task task1 = new Task();
        task1.setCourse(course);
        task1.setStatement("teste1");
        task1.setTaskOrder(2);
        task1.setType(Type.SINGLE_CHOICE);
        task1.setCreatedAt(LocalDateTime.now());

        Task task2 = new Task();
        task2.setCourse(course);
        task2.setStatement("teste2");
        task2.setTaskOrder(2);
        task2.setType(Type.OPEN_TEXT);
        task2.setCreatedAt(LocalDateTime.now());

        taskRepository.saveAll(List.of(task1, task2));

        List<Task> result = taskRepository.findByCourseIdOrderByTaskOrder(course.getId());

        assertEquals(2, result.size());
        assertEquals("teste1", result.get(0).getStatement());
        assertEquals("teste2", result.get(1).getStatement());
    }
}

