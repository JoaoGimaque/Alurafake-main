package br.com.alura.AluraFake.task.dto;

import jakarta.validation.constraints.*;

public class TaskOptionDTO {
    private String option;
    private boolean correct;

    public TaskOptionDTO() {}

    public TaskOptionDTO(String option, boolean correct) {
        this.option = option;
        this.correct = correct;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean isCorrect) {
        this.correct = isCorrect;
    }
}