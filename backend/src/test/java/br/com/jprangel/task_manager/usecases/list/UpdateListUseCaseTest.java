package br.com.jprangel.task_manager.usecases.list;

import br.com.jprangel.task_manager.dto.CreateListDTO;
import br.com.jprangel.task_manager.dto.ListResponseDTO;
import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.mapper.ListMapper;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.repository.ListRepository;
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
class UpdateListUseCaseTest {

    @InjectMocks
    private UpdateListUseCase updateListUseCase;

    @Mock
    private ListRepository listRepository;

    @Mock
    private ListMapper listMapper;

    @Test
    @DisplayName("Deve atualizar uma lista com sucesso")
    void shouldUpdateListSuccessfully() {
        var listId = "list-123";
        var existingList = new ListEntity();
        var updateDTO = new CreateListDTO("Nome Atualizado");
        existingList.setId(listId);
        existingList.setName("Nome Antigo");

        when(listRepository.findById(listId)).thenReturn(Optional.of(existingList));
        when(listRepository.save(any(ListEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(listMapper.toListResponseDTO(any(ListEntity.class))).thenAnswer(invocation -> {
            ListEntity savedList = invocation.getArgument(0);
            return new ListResponseDTO(savedList.getId(), savedList.getName(), null, null);
        });

        var result = updateListUseCase.execute(listId, updateDTO);

        assertNotNull(result);
        assertEquals(listId, result.getId());
        assertEquals("Nome Atualizado", result.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar uma lista que não existe")
    void shouldThrowExceptionWhenUpdatingNonExistentList() {
        var listId = "list-inexistente";
        var updateDTO = new CreateListDTO("Nome Atualizado");

        when(listRepository.findById(listId)).thenReturn(Optional.empty());

        assertThrows(ListNotFoundException.class, () -> {
            updateListUseCase.execute(listId, updateDTO);
        });
    }
}