package br.com.jprangel.task_manager.usecases.list;

import br.com.jprangel.task_manager.dto.CreateListDTO;
import br.com.jprangel.task_manager.dto.ListResponseDTO;
import br.com.jprangel.task_manager.exceptions.ListAlreadyExistsException;
import br.com.jprangel.task_manager.mapper.ListMapper;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateListUseCase {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private ListMapper listMapper;

    public ListResponseDTO execute(CreateListDTO dto) {
        listRepository.findByName(dto.getName()).ifPresent(list -> {
            throw new ListAlreadyExistsException("Já existe uma lista com o nome: " + dto.getName());
        });

        ListEntity newList = new ListEntity();
        newList.setName(dto.getName());
        ListEntity savedList = listRepository.save(newList);
        return listMapper.toListResponseDTO(savedList);
    }
}