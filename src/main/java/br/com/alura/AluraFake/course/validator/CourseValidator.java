package br.com.alura.AluraFake.course.validator;

import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.model.Status;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.model.Type;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CourseValidator {

    public void validateBeforePublish(Course course, List<Task> tasks) {
        validateCourseStatus(course);
        validateTaskTypes(tasks);
        validateTaskOrder(tasks);
    }

    private void validateCourseStatus(Course course) {
        if (course.getStatus() != Status.BUILDING) {
            throw new IllegalStateException("Somente cursos em BUILDING podem ser publicados");
        }
    }

    private void validateTaskTypes(List<Task> tasks) {
        Set<Type> foundTypes = new HashSet<>();

        for (Task task : tasks) {
            foundTypes.add(task.getType());
        }

        if (!foundTypes.contains(Type.OPEN_TEXT)
                || !foundTypes.contains(Type.SINGLE_CHOICE)
                || !foundTypes.contains(Type.MULTIPLE_CHOICE)) {
            throw new IllegalStateException("Curso deve ter ao menos uma atividade de cada tipo");
        }
    }

    private void validateTaskOrder(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            int expectedOrder = i + 1;

            if (task.getTaskOrder() != expectedOrder) {
                throw new IllegalStateException("Atividade com id " + task.getId()
                        + " tem ordem incorreta: " + task.getTaskOrder());
            }
        }
    }
}
