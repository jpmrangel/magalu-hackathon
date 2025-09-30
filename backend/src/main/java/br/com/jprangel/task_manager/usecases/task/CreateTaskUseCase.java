package br.com.jprangel.task_manager.usecases.task;

import br.com.jprangel.task_manager.dto.CreateTaskDTO;
import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.mapper.TaskMapper;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.model.TaskEntity;
import br.com.jprangel.task_manager.repository.ListRepository;
import br.com.jprangel.task_manager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTaskUseCase {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public TaskResponseDTO execute(CreateTaskDTO dto) {
        ListEntity list = listRepository.findById(dto.getListId())
            .orElseThrow(() -> new ListNotFoundException("Lista não encontrada com o ID: " + dto.getListId()));

        TaskEntity newTask = new TaskEntity();
        newTask.setName(dto.getName());
        newTask.setDescription(dto.getDescription());
        newTask.setPriority(dto.getPriority());
        newTask.setExpectedFinishingDate(dto.getExpectedFinishingDate());
        newTask.setList(list);

        TaskEntity savedTask = taskRepository.save(newTask);
        return taskMapper.toTaskResponseDTO(savedTask);
    }
}
