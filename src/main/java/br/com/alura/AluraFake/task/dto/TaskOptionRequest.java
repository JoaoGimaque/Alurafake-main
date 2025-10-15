package br.com.alura.AluraFake.task.dto;

import jakarta.validation.constraints.*;

public class TaskOptionRequest {
    @NotBlank
    @Size(min = 4, max = 80)
    private String option;

    @NotNull
    private Boolean isCorrect;

    // getters e setters
}