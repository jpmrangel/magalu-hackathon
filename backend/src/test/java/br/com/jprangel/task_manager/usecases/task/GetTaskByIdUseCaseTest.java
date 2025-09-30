package br.com.jprangel.task_manager.usecases.task;

import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.exceptions.TaskNotFoundException;
import br.com.jprangel.task_manager.mapper.TaskMapper;
import br.com.jprangel.task_manager.model.TaskEntity;
import br.com.jprangel.task_manager.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTaskByIdUseCaseTest {

    @InjectMocks
    private GetTaskByIdUseCase getTaskByIdUseCase;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Test
    @DisplayName("Deve retornar uma tarefa por ID com sucesso")
    void shouldReturnTaskByIdSuccessfully() {
        var taskId = "task-123";
        var taskEntity = new TaskEntity();
        taskEntity.setId(taskId);
        var taskResponseDTO = new TaskResponseDTO(taskId, "Tarefa Teste", null, null, null, null, null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.toTaskResponseDTO(taskEntity)).thenReturn(taskResponseDTO);

        var result = getTaskByIdUseCase.execute(taskId);

        assertNotNull(result);
        assertEquals(taskId, result.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar uma tarefa com ID que não existe")
    void shouldThrowExceptionWhenTaskIdNotFound() {
        var taskId = "task-inexistente";
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            getTaskByIdUseCase.execute(taskId);
        });
    }
}