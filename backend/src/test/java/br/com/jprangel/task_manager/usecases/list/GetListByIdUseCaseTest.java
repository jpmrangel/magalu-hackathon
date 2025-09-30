package br.com.jprangel.task_manager.usecases.list;

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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetListByIdUseCaseTest {

    @InjectMocks
    private GetListByIdUseCase getListByIdUseCase;

    @Mock
    private ListRepository listRepository;

    @Mock
    private ListMapper listMapper;

    @Test
    @DisplayName("Deve retornar uma lista por ID com sucesso")
    void shouldReturnListByIdSuccessfully() {
        var listId = "list-123";
        var listEntity = new ListEntity();
        listEntity.setId(listId);
        var listResponseDTO = new ListResponseDTO(listId, "Lista Teste", null, null);

        when(listRepository.findById(listId)).thenReturn(Optional.of(listEntity));
        when(listMapper.toListResponseDTO(listEntity)).thenReturn(listResponseDTO);

        var result = getListByIdUseCase.execute(listId);

        assertNotNull(result);
        assertEquals(listId, result.getId());
        assertEquals("Lista Teste", result.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar uma lista com ID que não existe")
    void shouldThrowExceptionWhenListIdNotFound() {
        var listId = "list-inexistente";
        when(listRepository.findById(listId)).thenReturn(Optional.empty());

        assertThrows(ListNotFoundException.class, () -> {
            getListByIdUseCase.execute(listId);
        });
    }
}