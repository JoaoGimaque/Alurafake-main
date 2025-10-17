package br.com.alura.AluraFake.user.repository;

import br.com.alura.AluraFake.user.model.Role;
import br.com.alura.AluraFake.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void existsByEmail_shouldReturnTrue_whenEmailExists() {
        User user = new User("João", "joao@alura.com", Role.STUDENT);
        userRepository.save(user);

        boolean exists = userRepository.existsByEmail("joao@alura.com");

        assertTrue(exists);
    }

    @Test
    void existsByEmail_shouldReturnFalse_whenEmailDoesNotExist() {
        boolean exists = userRepository.existsByEmail("naoexiste@email.com");

        assertFalse(exists);
    }

    @Test
    void findByEmail_shouldReturnUser_whenEmailExists() {
        User user = new User("João", "joao@alura.com", Role.STUDENT);
        userRepository.save(user);

        Optional<User> result = userRepository.findByEmail("joao@alura.com");

        assertTrue(result.isPresent());
        assertEquals("João", result.get().getName());
    }

    @Test
    void findByEmail_shouldReturnEmpty_whenEmailDoesNotExist() {
        Optional<User> result = userRepository.findByEmail("inexistente@email.com");

        assertTrue(result.isEmpty());
    }
}
