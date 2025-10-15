package br.com.alura.AluraFake.task.dto;

import jakarta.validation.constraints.*;

public class OpenTextTaskRequest {
    @NotNull
    private Long courseId;

    @NotBlank
    @Size(min = 4, max = 255)
    private String statement;

    @NotNull
    @Min(1)
    private Integer order;

    // getters e setters
}
