package br.com.jprangel.task_manager.usecases.list;

import br.com.jprangel.task_manager.dto.CreateListDTO;
import br.com.jprangel.task_manager.dto.ListResponseDTO;
import br.com.jprangel.task_manager.exceptions.ListAlreadyExistsException;
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
class CreateListUseCaseTest {

    @InjectMocks
    private CreateListUseCase createListUseCase;

    @Mock
    private ListRepository listRepository;

    @Mock
    private ListMapper listMapper;

    @Test
    @DisplayName("Deve criar uma lista com sucesso")
    void shouldCreateListSuccessfully() {
        var createListDTO = new CreateListDTO("Nova Lista");

        when(listRepository.findByName("Nova Lista")).thenReturn(Optional.empty());
        when(listRepository.save(any(ListEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(listMapper.toListResponseDTO(any(ListEntity.class))).thenReturn(new ListResponseDTO(null, "Nova Lista", null, null));

        var result = createListUseCase.execute(createListDTO);

        assertNotNull(result);
        assertEquals("Nova Lista", result.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar uma lista com nome que já existe")
    void shouldThrowExceptionWhenListNameAlreadyExists() {
        var createListDTO = new CreateListDTO("Lista Existente");

        when(listRepository.findByName("Lista Existente")).thenReturn(Optional.of(new ListEntity()));

        assertThrows(ListAlreadyExistsException.class, () -> {
            createListUseCase.execute(createListDTO);
        });
    }
}
