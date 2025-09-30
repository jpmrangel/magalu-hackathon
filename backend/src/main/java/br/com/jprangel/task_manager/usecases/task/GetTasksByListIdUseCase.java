package br.com.jprangel.task_manager.usecases.task;

import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.mapper.TaskMapper;
import br.com.jprangel.task_manager.repository.ListRepository;
import br.com.jprangel.task_manager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetTasksByListIdUseCase {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public List<TaskResponseDTO> execute(String listId) {
        listRepository.findById(listId)
            .orElseThrow(() -> new ListNotFoundException("Lista não encontrada com o ID: " + listId));
        return taskRepository.findByListId(listId).stream()
            .map(taskMapper::toTaskResponseDTO)
            .collect(Collectors.toList());
    }
}
