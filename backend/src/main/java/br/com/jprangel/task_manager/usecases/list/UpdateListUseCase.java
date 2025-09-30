package br.com.jprangel.task_manager.usecases.list;

import br.com.jprangel.task_manager.dto.CreateListDTO;
import br.com.jprangel.task_manager.dto.ListResponseDTO;
import br.com.jprangel.task_manager.exceptions.ListAlreadyExistsException;
import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.mapper.ListMapper;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.repository.ListRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateListUseCase {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private ListMapper listMapper;

    public ListResponseDTO execute(String id, CreateListDTO dto) {
        ListEntity list = listRepository.findById(id)
             .orElseThrow(() -> new ListNotFoundException("Lista não encontrada com o ID: " + id));  
             
        Optional<ListEntity> existingList = listRepository.findByName(dto.getName());
        if (existingList.isPresent() && !existingList.get().getId().equals(id)) {
            throw new ListAlreadyExistsException("Já existe uma lista com o nome: " + dto.getName());
        }

        list.setName(dto.getName());
        ListEntity updatedList = listRepository.save(list);
        return listMapper.toListResponseDTO(updatedList);
    }
}
