package br.com.alura.AluraFake.task.model;

import br.com.alura.AluraFake.course.Course;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String statement;

    @Column(nullable = false)
    private Integer order;

    @ManyToOne(optional = false)
    private Course course;

    // getters, setters, equals, hashCode
}
