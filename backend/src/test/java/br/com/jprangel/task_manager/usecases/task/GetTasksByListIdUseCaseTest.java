package br.com.jprangel.task_manager.usecases.task;

import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.mapper.TaskMapper;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.model.TaskEntity;
import br.com.jprangel.task_manager.repository.ListRepository;
import br.com.jprangel.task_manager.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTasksByListIdUseCaseTest {

    @InjectMocks
    private GetTasksByListIdUseCase getTasksByListIdUseCase;

    @Mock
    private ListRepository listRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Test
    @DisplayName("Deve retornar tarefas de uma lista com sucesso")
    void shouldReturnTasksByListIdSuccessfully() {
        var listId = "list-123";
        when(listRepository.findById(listId)).thenReturn(Optional.of(new ListEntity()));
        when(taskRepository.findByListId(listId)).thenReturn(List.of(new TaskEntity(), new TaskEntity()));
        when(taskMapper.toTaskResponseDTO(any(TaskEntity.class))).thenReturn(new TaskResponseDTO(null, null, null, null, null, null, null));

        var result = getTasksByListIdUseCase.execute(listId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskMapper, times(2)).toTaskResponseDTO(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar tarefas de uma lista que não existe")
    void shouldThrowExceptionWhenListIsNotFound() {
        var listId = "list-inexistente";
        when(listRepository.findById(listId)).thenReturn(Optional.empty());

        assertThrows(ListNotFoundException.class, () -> {
            getTasksByListIdUseCase.execute(listId);
        });
    }
}