package br.com.alura.AluraFake.user.service;

import br.com.alura.AluraFake.course.dto.InstructorCourseReportDTO;
import br.com.alura.AluraFake.course.dto.InstructorCourseReportResponseDTO;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.user.model.Role;
import br.com.alura.AluraFake.user.model.User;
import br.com.alura.AluraFake.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public InstructorCourseReportResponseDTO listCoursesByInstructor(Long instructorId) {
        Optional<User> optionalInstructor = userRepository.findById(instructorId);
        if (optionalInstructor.isEmpty()) {
            throw new IllegalArgumentException("Instrutor não encontrado");
        }
        User instructor = optionalInstructor.get();

        if (!Role.INSTRUCTOR.equals(instructor.getRole())) {
            throw new IllegalStateException("Usuário não é um instrutor");
        }

        List<InstructorCourseReportDTO> courses = courseRepository.findCoursesByInstructorId(instructorId);

        long totalCourses = courses.size();

        return new InstructorCourseReportResponseDTO(totalCourses, courses);
    }
}
