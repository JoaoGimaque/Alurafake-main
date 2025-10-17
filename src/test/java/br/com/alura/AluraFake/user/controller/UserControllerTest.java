package br.com.alura.AluraFake.user.controller;

import br.com.alura.AluraFake.course.dto.InstructorCourseReportResponseDTO;
import br.com.alura.AluraFake.user.dto.NewUserDTO;
import br.com.alura.AluraFake.user.dto.UserListItemDTO;
import br.com.alura.AluraFake.user.model.Role;
import br.com.alura.AluraFake.user.model.User;
import br.com.alura.AluraFake.user.repository.UserRepository;
import br.com.alura.AluraFake.user.service.UserService;
import br.com.alura.AluraFake.util.ErrorItemDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void newStudent_shouldCreateUser_whenEmailIsUnique() {
        NewUserDTO dto = new NewUserDTO();
        dto.setName("João");
        dto.setEmail("joao@alura.com");
        dto.setRole(Role.STUDENT);
        User user = dto.toModel();

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);

        ResponseEntity response = userController.newStudent(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void newStudent_shouldReturnBadRequest_whenEmailExists() {
        NewUserDTO dto = new NewUserDTO();
        dto.setName("João");
        dto.setEmail("joao@alura.com");
        dto.setRole(Role.STUDENT);

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        ResponseEntity response = userController.newStudent(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorItemDTO);
    }

    @Test
    void listAllUsers_shouldReturnUserList() {
        User user = new User();
        user.setName("Maria");
        user.setEmail("maria@email.com");

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserListItemDTO> result = userController.listAllUsers();

        assertEquals(1, result.size());
        assertEquals("Maria", result.get(0).getName());
    }

    @Test
    void fetchInstructorTrackOverview_shouldReturnReport_whenValid() {
        Long instructorId = 1L;
        InstructorCourseReportResponseDTO report = new InstructorCourseReportResponseDTO(1L,List.of());

        when(userService.listCoursesByInstructor(instructorId)).thenReturn(report);

        ResponseEntity<InstructorCourseReportResponseDTO> response = userController.fetchInstructorTrackOverview(instructorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
    }

    @Test
    void fetchInstructorTrackOverview_shouldReturnNotFound_whenIllegalArgument() {
        Long instructorId = 1L;
        when(userService.listCoursesByInstructor(instructorId)).thenThrow(new IllegalArgumentException());

        ResponseEntity<InstructorCourseReportResponseDTO> response = userController.fetchInstructorTrackOverview(instructorId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void fetchInstructorTrackOverview_shouldReturnBadRequest_whenIllegalState() {
        Long instructorId = 1L;
        when(userService.listCoursesByInstructor(instructorId)).thenThrow(new IllegalStateException());

        ResponseEntity<InstructorCourseReportResponseDTO> response = userController.fetchInstructorTrackOverview(instructorId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
