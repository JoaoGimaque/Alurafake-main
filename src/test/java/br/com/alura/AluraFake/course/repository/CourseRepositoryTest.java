package br.com.alura.AluraFake.course.repository;

import br.com.alura.AluraFake.course.dto.InstructorCourseReportDTO;
import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.model.Status;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.model.Type;
import br.com.alura.AluraFake.task.repository.TaskRepository;
import br.com.alura.AluraFake.user.model.Role;
import br.com.alura.AluraFake.user.model.User;
import br.com.alura.AluraFake.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void findCoursesByInstructorId_shouldReturnReportWithTaskCount() {
        User instructor = new User("João", "joao@alura.com.br", Role.INSTRUCTOR);
        userRepository.save(instructor);

        Course course = new Course("Spring Boot", "Curso completo", instructor);
        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());
        courseRepository.save(course);

        Task task1 = new Task();
        task1.setStatement("task 1");
        task1.setType(Type.OPEN_TEXT);
        task1.setTaskOrder(1);
        task1.setCourse(course);
        task1.setCreatedAt(LocalDateTime.now());

        Task task2 = new Task();
        task2.setStatement("task 2");
        task2.setType(Type.OPEN_TEXT);
        task2.setTaskOrder(2);
        task2.setCourse(course);
        task2.setCreatedAt(LocalDateTime.now());
        taskRepository.saveAll(List.of(task1, task2));

        List<InstructorCourseReportDTO> report = courseRepository.findCoursesByInstructorId(instructor.getId());

        assertEquals(1, report.size());
        InstructorCourseReportDTO dto = report.get(0);
        assertEquals(course.getId(), dto.getId());
        assertEquals("Spring Boot", dto.getTitle());
        assertEquals(Status.PUBLISHED, dto.getStatus());
        assertEquals(2, dto.getTaskCount());
    }
}