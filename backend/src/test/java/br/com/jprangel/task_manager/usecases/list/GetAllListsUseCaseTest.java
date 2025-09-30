package br.com.jprangel.task_manager.usecases.list;

import br.com.jprangel.task_manager.dto.ListResponseDTO;
import br.com.jprangel.task_manager.mapper.ListMapper;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.repository.ListRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllListsUseCaseTest {

    @InjectMocks
    private GetAllListsUseCase getAllListsUseCase;

    @Mock
    private ListRepository listRepository;

    @Mock
    private ListMapper listMapper;

    @Test
    @DisplayName("Deve retornar uma lista de DTOs com sucesso")
    void shouldReturnListOfDTOsSuccessfully() {
        var listEntity1 = new ListEntity();
        listEntity1.setId("1");
        listEntity1.setName("Lista 1");

        var listEntity2 = new ListEntity();
        listEntity2.setId("2");
        listEntity2.setName("Lista 2");
        
        var listResponseDTO1 = new ListResponseDTO("1", "Lista 1", null, null);
        var listResponseDTO2 = new ListResponseDTO("2", "Lista 2", null, null);

        when(listRepository.findAll()).thenReturn(List.of(listEntity1, listEntity2));
        when(listMapper.toListResponseDTO(listEntity1)).thenReturn(listResponseDTO1);
        when(listMapper.toListResponseDTO(listEntity2)).thenReturn(listResponseDTO2);

        var result = getAllListsUseCase.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Lista 1", result.get(0).getName());
        assertEquals("Lista 2", result.get(1).getName());
        verify(listRepository, times(1)).findAll();
        verify(listMapper, times(2)).toListResponseDTO(any(ListEntity.class));
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver listas")
    void shouldReturnEmptyListWhenNoListsExist() {
        when(listRepository.findAll()).thenReturn(Collections.emptyList());

        var result = getAllListsUseCase.execute();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(listRepository, times(1)).findAll();
        verify(listMapper, never()).toListResponseDTO(any());
    }
}