package br.com.jprangel.task_manager.usecases.task;

import br.com.jprangel.task_manager.exceptions.TaskNotFoundException;
import br.com.jprangel.task_manager.model.TaskEntity;
import br.com.jprangel.task_manager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteTaskUseCase {

    @Autowired
    private TaskRepository taskRepository;

    public void execute(String id) {
        TaskEntity task = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada com o ID: " + id));
        taskRepository.delete(task);
    }
}
