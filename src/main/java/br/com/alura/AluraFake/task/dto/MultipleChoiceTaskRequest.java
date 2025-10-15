package br.com.alura.AluraFake.task.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class MultipleChoiceTaskRequest {
    @NotNull
    private Long courseId;

    @NotBlank
    @Size(min = 4, max = 255)
    private String statement;

    @NotNull
    @Min(1)
    private Integer order;

    @Size(min = 3, max = 5)
    @NotNull
    private List<TaskOptionRequest> options;

    // getters e setters
}