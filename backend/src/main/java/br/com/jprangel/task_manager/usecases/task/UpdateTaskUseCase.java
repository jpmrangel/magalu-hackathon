package br.com.jprangel.task_manager.usecases.task;

import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.dto.UpdateTaskDTO;
import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.exceptions.TaskNotFoundException;
import br.com.jprangel.task_manager.mapper.TaskMapper;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.model.TaskEntity;
import br.com.jprangel.task_manager.repository.ListRepository;
import br.com.jprangel.task_manager.repository.TaskRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateTaskUseCase {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public TaskResponseDTO execute(String id, UpdateTaskDTO dto) {
        TaskEntity task = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada com o ID: " + id));

        Optional.ofNullable(dto.getName()).ifPresent(task::setName);
        Optional.ofNullable(dto.getDescription()).ifPresent(task::setDescription);
        Optional.ofNullable(dto.getPriority()).ifPresent(task::setPriority);
        Optional.ofNullable(dto.getFinishingDate()).ifPresent(task::setFinishingDate);
        Optional.ofNullable(dto.getExpectedFinishingDate()).ifPresent(task::setExpectedFinishingDate);
        Optional.ofNullable(dto.getListId()).ifPresent(listId -> {
            ListEntity list = listRepository.findById(listId)
                .orElseThrow(() -> new ListNotFoundException("Lista informada não existe: " + listId));
            task.setList(list);
        });

        TaskEntity updatedTask = taskRepository.save(task);
        return taskMapper.toTaskResponseDTO(updatedTask);
    }
}