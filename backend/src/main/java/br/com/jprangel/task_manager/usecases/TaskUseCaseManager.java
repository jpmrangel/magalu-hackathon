package br.com.jprangel.task_manager.usecases;

import br.com.jprangel.task_manager.dto.CreateTaskDTO;
import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.dto.UpdateFinishingDateDTO;
import br.com.jprangel.task_manager.dto.UpdateTaskDTO;
import br.com.jprangel.task_manager.usecases.task.CreateTaskUseCase;
import br.com.jprangel.task_manager.usecases.task.DeleteTaskUseCase;
import br.com.jprangel.task_manager.usecases.task.GetTaskByIdUseCase;
import br.com.jprangel.task_manager.usecases.task.GetTasksByListIdUseCase;
import br.com.jprangel.task_manager.usecases.task.UpdateTaskUseCase;
import br.com.jprangel.task_manager.usecases.task.UpdateFinishingDateUseCase;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskUseCaseManager {

    @Autowired
    private final CreateTaskUseCase createTaskUseCase;
    @Autowired
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    @Autowired
    private final GetTasksByListIdUseCase getTasksByListIdUseCase;
    @Autowired
    private final UpdateTaskUseCase updateTaskUseCase;
    @Autowired
    private final UpdateFinishingDateUseCase updateFinishingDateUseCase;
    @Autowired
    private final DeleteTaskUseCase deleteTaskUseCase;

    public TaskResponseDTO createTask(CreateTaskDTO dto) {
        return createTaskUseCase.execute(dto);
    }

    public TaskResponseDTO getTaskById(String id) {
        return getTaskByIdUseCase.execute(id);
    }

    public List<TaskResponseDTO> getTasksByListId(String listId) {
        return getTasksByListIdUseCase.execute(listId);
    }

    public TaskResponseDTO updateTask(String id, UpdateTaskDTO dto) {
        return updateTaskUseCase.execute(id, dto);
    }

    public TaskResponseDTO updateFinishingTaskDate(String id, UpdateFinishingDateDTO dto) {
        return updateFinishingDateUseCase.execute(id, dto);
    }

    public void deleteTask(String id) {
        deleteTaskUseCase.execute(id);
    }
}
