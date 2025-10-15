package br.com.alura.AluraFake.task.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class SingleChoiceTask extends Task {
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskOption> options;

    // getters e setters
}
