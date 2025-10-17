package br.com.alura.AluraFake.course.service;


import org.springframework.stereotype.Service;
import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.course.model.Status;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.repository.TaskRepository;
import br.com.alura.AluraFake.course.validator.CourseValidator;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TaskRepository taskRepository;
    private final CourseValidator courseValidator;

    public CourseService(CourseRepository courseRepository,
                         TaskRepository taskRepository,
                         CourseValidator courseValidator) {
        this.courseRepository = courseRepository;
        this.taskRepository = taskRepository;
        this.courseValidator = courseValidator;
    }

    public Course activateCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            throw new IllegalArgumentException("Curso não encontrado");
        }
        Course course = optionalCourse.get();

        List<Task> tasks = taskRepository.findByCourseIdOrderByTaskOrder(courseId);

        courseValidator.validateBeforeActivate(course, tasks);

        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());

        return courseRepository.save(course);
    }
}
