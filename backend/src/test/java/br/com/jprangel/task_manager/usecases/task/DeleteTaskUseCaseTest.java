package br.com.jprangel.task_manager.usecases.task;

import br.com.jprangel.task_manager.exceptions.TaskNotFoundException;
import br.com.jprangel.task_manager.model.TaskEntity;
import br.com.jprangel.task_manager.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteTaskUseCaseTest {

    @InjectMocks
    private DeleteTaskUseCase deleteTaskUseCase;

    @Mock
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Deve deletar uma tarefa com sucesso")
    void shouldDeleteTaskSuccessfully() {
        var taskId = "task-123";
        var taskEntity = new TaskEntity();
        taskEntity.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        doNothing().when(taskRepository).delete(taskEntity);

        assertDoesNotThrow(() -> {
            deleteTaskUseCase.execute(taskId);
        });

        verify(taskRepository, times(1)).delete(taskEntity);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar uma tarefa que não existe")
    void shouldThrowExceptionWhenDeletingNonExistentTask() {
        var taskId = "task-inexistente";
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            deleteTaskUseCase.execute(taskId);
        });
    }
}