package br.com.jprangel.task_manager.usecases.task;

import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.dto.UpdateTaskDTO;
import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.exceptions.TaskNotFoundException;
import br.com.jprangel.task_manager.mapper.TaskMapper;
import br.com.jprangel.task_manager.model.TaskEntity;
import br.com.jprangel.task_manager.model.TaskPriority;
import br.com.jprangel.task_manager.repository.ListRepository;
import br.com.jprangel.task_manager.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateTaskUseCaseTest {

    @InjectMocks
    private UpdateTaskUseCase updateTaskUseCase;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ListRepository listRepository;

    @Mock
    private TaskMapper taskMapper;

    @Test
    @DisplayName("Deve atualizar uma tarefa com sucesso")
    void shouldUpdateTaskSuccessfully() {
        var taskId = "task-123";
        var updateDTO = new UpdateTaskDTO("Nome Atualizado", "Desc Atualizada", TaskPriority.LOW, null, null, null);
        var existingTask = new TaskEntity();
        existingTask.setId(taskId);
        existingTask.setName("Nome Antigo");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(TaskEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(taskMapper.toTaskResponseDTO(any(TaskEntity.class))).thenAnswer(invocation -> {
            TaskEntity savedTask = invocation.getArgument(0);
            return new TaskResponseDTO(savedTask.getId(), savedTask.getName(), savedTask.getDescription(), savedTask.getPriority(), null, null, null);
        });

        var result = updateTaskUseCase.execute(taskId, updateDTO);

        assertNotNull(result);
        assertEquals(taskId, result.getId());
        assertEquals("Nome Atualizado", result.getName());
        assertEquals("Desc Atualizada", result.getDescription());
        assertEquals(TaskPriority.LOW, result.getPriority());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar tarefa que não existe")
    void shouldThrowExceptionWhenUpdatingNonExistentTask() {
        var taskId = "task-inexistente";
        var updateDTO = new UpdateTaskDTO(null, null, null, null, null, null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            updateTaskUseCase.execute(taskId, updateDTO);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar mover tarefa para lista que não existe")
    void shouldThrowExceptionWhenMovingTaskToNonExistentList() {
        var taskId = "task-123";
        var listId = "list-inexistente";
        var updateDTO = new UpdateTaskDTO(null, null, null, null, null, listId);
        var existingTask = new TaskEntity();
        existingTask.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(listRepository.findById(listId)).thenReturn(Optional.empty());

        assertThrows(ListNotFoundException.class, () -> {
            updateTaskUseCase.execute(taskId, updateDTO);
        });
    }
}