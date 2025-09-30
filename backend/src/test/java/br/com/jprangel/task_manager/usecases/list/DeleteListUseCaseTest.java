package br.com.jprangel.task_manager.usecases.list;

import br.com.jprangel.task_manager.exceptions.ListDeletionNotAllowedException;
import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.repository.ListRepository;
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
class DeleteListUseCaseTest {

    @InjectMocks
    private DeleteListUseCase deleteListUseCase;

    @Mock
    private ListRepository listRepository;

    @Mock
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Deve deletar uma lista vazia com sucesso")
    void shouldDeleteEmptyListSuccessfully() {
        var listId = "list-vazia";
        var listEntity = new ListEntity();
        listEntity.setId(listId);

        when(listRepository.findById(listId)).thenReturn(Optional.of(listEntity));
        when(taskRepository.existsByListId(listId)).thenReturn(false);
        doNothing().when(listRepository).delete(listEntity);

        assertDoesNotThrow(() -> {
            deleteListUseCase.execute(listId);
        });

        verify(listRepository, times(1)).delete(listEntity);
        verify(taskRepository, times(1)).existsByListId(listId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar uma lista que não existe")
    void shouldThrowExceptionWhenDeletingNonExistentList() {
        var listId = "list-inexistente";
        when(listRepository.findById(listId)).thenReturn(Optional.empty());

        assertThrows(ListNotFoundException.class, () -> {
            deleteListUseCase.execute(listId);
        });

        verify(taskRepository, never()).existsByListId(any());
        verify(listRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar uma lista com tarefas")
    void shouldThrowExceptionWhenDeletingListWithTasks() {
        var listId = "list-com-tarefas";
        var listEntity = new ListEntity();
        listEntity.setId(listId);

        when(listRepository.findById(listId)).thenReturn(Optional.of(listEntity));
        when(taskRepository.existsByListId(listId)).thenReturn(true);

        assertThrows(ListDeletionNotAllowedException.class, () -> {
            deleteListUseCase.execute(listId);
        });

        verify(listRepository, never()).delete(any());
        verify(taskRepository, times(1)).existsByListId(listId);
    }
}