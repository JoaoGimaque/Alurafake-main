package br.com.alura.AluraFake.course.service;

import br.com.alura.AluraFake.user.model.User;
import org.springframework.stereotype.Service;
import br.com.alura.AluraFake.course.dto.InstructorCourseReportDTO;
import br.com.alura.AluraFake.course.dto.InstructorCourseReportResponseDTO;
import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.course.model.Status;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.repository.TaskRepository;
import br.com.alura.AluraFake.course.validator.CourseValidator;
import br.com.alura.AluraFake.user.model.Role;
import br.com.alura.AluraFake.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final TaskRepository taskRepository;
    private final CourseValidator courseValidator;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository,
                         TaskRepository taskRepository,
                         CourseValidator courseValidator,
                         UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.taskRepository = taskRepository;
        this.courseValidator = courseValidator;
        this.userRepository = userRepository;
    }

    @Transactional
    public Course publishCourse(Long courseId) {
        // Busca o curso pelo ID
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            throw new IllegalArgumentException("Curso não encontrado");
        }
        Course course = optionalCourse.get();

        // Busca as tarefas do curso
        List<Task> tasks = taskRepository.findByCourseIdOrderByTaskOrder(courseId);

        // Valida se o curso pode ser publicado
        courseValidator.validateBeforePublish(course, tasks);

        // Atualiza o status e data de publicação
        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());

        // Salva e retorna o curso atualizado
        return courseRepository.save(course);
    }

    public InstructorCourseReportResponseDTO getCoursesByInstructor(Long instructorId) {
        // Busca o instrutor pelo ID
        Optional<User> optionalInstructor = userRepository.findById(instructorId);
        if (optionalInstructor.isEmpty()) {
            throw new IllegalArgumentException("Instrutor não encontrado");
        }
        User instructor = optionalInstructor.get();

        // Verifica se o usuário é realmente um instrutor
        if (!Role.INSTRUCTOR.equals(instructor.getRole())) {
            throw new IllegalStateException("Usuário não é um instrutor");
        }

        // Busca os cursos do instrutor
        List<InstructorCourseReportDTO> courses = courseRepository.findCoursesByInstructorId(instructorId);

        // Conta o total de cursos
        long totalCourses = courses.size();

        // Retorna o relatório
        return new InstructorCourseReportResponseDTO(totalCourses, courses);
    }
}
