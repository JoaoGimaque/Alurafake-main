package br.com.alura.AluraFake.task.dto;

import java.util.List;

public class NewTaskDTO {
    private Long courseId;
    private String statement;
    private int order;
    private List<TaskOptionDTO> options;

    public Long getCourseId() {
        return courseId;
    }
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getStatement() {
        return statement;
    }
    public void setStatement(String statement) {
        this.statement = statement;
    }

    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }

    public List<TaskOptionDTO> getOptions() {
        return options;
    }
    public void setOptions(List<TaskOptionDTO> options) {
        this.options = options;
    }
}
