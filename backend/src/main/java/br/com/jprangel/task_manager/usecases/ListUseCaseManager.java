package br.com.jprangel.task_manager.usecases;

import br.com.jprangel.task_manager.dto.CreateListDTO;
import br.com.jprangel.task_manager.dto.ListResponseDTO;
import br.com.jprangel.task_manager.usecases.list.CreateListUseCase;
import br.com.jprangel.task_manager.usecases.list.DeleteListUseCase;
import br.com.jprangel.task_manager.usecases.list.GetAllListsUseCase;
import br.com.jprangel.task_manager.usecases.list.GetListByIdUseCase;
import br.com.jprangel.task_manager.usecases.list.UpdateListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListUseCaseManager {

    private final CreateListUseCase createListUseCase;
    private final GetAllListsUseCase getAllListsUseCase;
    private final GetListByIdUseCase getListByIdUseCase;
    private final UpdateListUseCase updateListUseCase;
    private final DeleteListUseCase deleteListUseCase;

    public ListResponseDTO createList(CreateListDTO dto) {
        return createListUseCase.execute(dto);
    }

    public List<ListResponseDTO> getAllLists() {
        return getAllListsUseCase.execute();
    }

    public ListResponseDTO getListById(String id) {
        return getListByIdUseCase.execute(id);
    }

    public ListResponseDTO updateList(String id, CreateListDTO dto) {
        return updateListUseCase.execute(id, dto);
    }

    public void deleteList(String id) {
        deleteListUseCase.execute(id);
    }
}
