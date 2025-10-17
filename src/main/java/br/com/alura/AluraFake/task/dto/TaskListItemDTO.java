package br.com.alura.AluraFake.task.dto;

import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.model.Type;

public class TaskListItemDTO {
    private Long id;
    private Long courseId;
    private String statement;
    private Type type;
    private int taskOrder;

    public TaskListItemDTO(Long id, Long courseId, String statement, Type type, int taskOrder) {
        this.id = id;
        this.courseId = courseId;
        this.statement = statement;
        this.type = type;
        this.taskOrder = taskOrder;
    }

    public TaskListItemDTO(Task task) {
        this.id = task.getId();
        this.courseId = task.getCourse().getId();
        this.statement = task.getStatement();
        this.type = task.getType();
        this.taskOrder = task.getTaskOrder();
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getStatement() {
        return statement;
    }

    public Type getType() {
        return type;
    }

    public int getTaskOrder() {
        return taskOrder;

    }
}