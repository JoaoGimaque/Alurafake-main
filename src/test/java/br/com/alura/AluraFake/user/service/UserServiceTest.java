package br.com.alura.AluraFake.user.service;

import br.com.alura.AluraFake.course.dto.InstructorCourseReportDTO;
import br.com.alura.AluraFake.course.dto.InstructorCourseReportResponseDTO;
import br.com.alura.AluraFake.course.model.Status;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.user.model.Role;
import br.com.alura.AluraFake.user.model.User;
import br.com.alura.AluraFake.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void listCoursesByInstructor_shouldReturnReport_whenInstructorIsValid() {
        Long instructorId = 1L;
        User instructor = new User("João", "joao@alura.com", Role.INSTRUCTOR);

        List<InstructorCourseReportDTO> courses = List.of(
                new InstructorCourseReportDTO(1L, "Java", Status.PUBLISHED, LocalDateTime.now(), 3L)
        );

        when(userRepository.findById(instructorId)).thenReturn(Optional.of(instructor));
        when(courseRepository.findCoursesByInstructorId(instructorId)).thenReturn(courses);

        InstructorCourseReportResponseDTO result = userService.listCoursesByInstructor(instructorId);

        assertEquals(1, result.getTotalCourses());
        assertEquals(courses, result.getCourses());
    }

    @Test
    void listCoursesByInstructor_shouldThrow_whenInstructorNotFound() {
        Long instructorId = 99L;
        when(userRepository.findById(instructorId)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                userService.listCoursesByInstructor(instructorId));

        assertEquals("Instrutor não encontrado", ex.getMessage());
    }

    @Test
    void listCoursesByInstructor_shouldThrow_whenUserIsNotInstructor() {
        Long instructorId = 2L;
        User instructor = new User("João", "joao@alura.com", Role.STUDENT);

        when(userRepository.findById(instructorId)).thenReturn(Optional.of(instructor));

        Exception ex = assertThrows(IllegalStateException.class, () ->
                userService.listCoursesByInstructor(instructorId));

        assertEquals("Usuário não é um instrutor", ex.getMessage());
    }
}
