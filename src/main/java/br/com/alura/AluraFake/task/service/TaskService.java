package br.com.alura.AluraFake.task.service;

import br.com.alura.AluraFake.task.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    @Transactional
    public void createOpenTextTask(OpenTextTaskRequest request) {
        // TODO: Implementar validações e lógica de criação
    }

    @Transactional
    public void createSingleChoiceTask(SingleChoiceTaskRequest request) {
        // TODO: Implementar validações e lógica de criação
    }

    @Transactional
    public void createMultipleChoiceTask(MultipleChoiceTaskRequest request) {
        // TODO: Implementar validações e lógica de criação
    }
}