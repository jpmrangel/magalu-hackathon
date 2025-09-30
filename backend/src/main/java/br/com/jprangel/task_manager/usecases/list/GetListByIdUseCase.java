package br.com.jprangel.task_manager.usecases.list;

import br.com.jprangel.task_manager.dto.ListResponseDTO;
import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.mapper.ListMapper;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetListByIdUseCase {
    
    @Autowired
    private ListRepository listRepository;

    @Autowired
    private ListMapper listMapper;

    public ListResponseDTO execute(String id) {
        ListEntity list = listRepository.findById(id)
            .orElseThrow(() -> new ListNotFoundException("Lista não encontrada com o ID: " + id));
        return listMapper.toListResponseDTO(list);
    }
}
