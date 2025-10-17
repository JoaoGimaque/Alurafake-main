package br.com.alura.AluraFake.task.validator;

import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.model.TaskOption;
import br.com.alura.AluraFake.task.model.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskValidatorTest {

    private final TaskValidator validator = new TaskValidator();

    @Test
    void validateTask_shouldPass_forValidOpenText() {
        Task task = new Task();
        task.setStatement("Explique herança");
        task.setTaskOrder(1);
        task.setType(Type.OPEN_TEXT);

        assertDoesNotThrow(() -> validator.validateTask(task));
    }

    @Test
    void validateTask_shouldFail_forShortStatement() {
        Task task = new Task();
        task.setStatement("abc");
        task.setTaskOrder(1);
        task.setType(Type.OPEN_TEXT);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> validator.validateTask(task));
        assertEquals("O enunciado deve ter entre 4 e 255 caracteres", ex.getMessage());
    }

    @Test
    void validateTask_shouldFail_forNegativeOrder() {
        Task task = new Task();
        task.setStatement("Questão válida");
        task.setTaskOrder(0);
        task.setType(Type.OPEN_TEXT);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> validator.validateTask(task));
        assertEquals("Ordem deve ser positiva", ex.getMessage());
    }

    @Test
    void validateTask_shouldFail_forNullOptions_inChoiceType() {
        Task task = new Task();
        task.setStatement("Escolha a correta");
        task.setTaskOrder(1);
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(null);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> validator.validateTask(task));
        assertEquals("Opções não podem ser nulas", ex.getMessage());
    }

    @Test
    void validateTask_shouldFail_forSingleChoice_withNoCorrectOption() {
        Task task = new Task();
        task.setStatement("Qual é a capital da França?");
        task.setTaskOrder(1);
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(List.of(
                new TaskOption(null, "Paris", false),
                new TaskOption(null, "Londres", false)
        ));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> validator.validateTask(task));
        assertEquals("Deve ter exatamente uma alternativa correta", ex.getMessage());
    }

    @Test
    void validateTask_shouldFail_forMultipleChoice_withAllCorrect() {
        Task task = new Task();
        task.setStatement("Quais são linguagens?");
        task.setTaskOrder(1);
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(List.of(
                new TaskOption(null, "Java", true),
                new TaskOption(null, "Python", true),
                new TaskOption(null, "C#", true)
        ));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> validator.validateTask(task));
        assertEquals("Deve ter pelo menos uma alternativa incorreta", ex.getMessage());
    }

    @Test
    void validateTask_shouldFail_forOptionEqualToStatement() {
        Task task = new Task();
        task.setStatement("Escolha a correta");
        task.setTaskOrder(1);
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(List.of(
                new TaskOption(null, "Escolha a correta", true),
                new TaskOption(null, "Outra", false)
        ));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> validator.validateTask(task));
        assertEquals("Opção não pode ser igual ao enunciado", ex.getMessage());
    }

    @Test
    void validateTask_shouldFail_forRepeatedOptions() {
        Task task = new Task();
        task.setStatement("Escolha a correta");
        task.setTaskOrder(1);
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(List.of(
                new TaskOption(null, "Paris", true),
                new TaskOption(null, "Paris", false)
        ));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> validator.validateTask(task));
        assertEquals("Opções repetidas não são permitidas", ex.getMessage());
    }
}
