package br.com.jprangel.task_manager.usecases.list;

import br.com.jprangel.task_manager.dto.ListResponseDTO;
import br.com.jprangel.task_manager.mapper.ListMapper;
import br.com.jprangel.task_manager.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllListsUseCase {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private ListMapper listMapper;

    public List<ListResponseDTO> execute() {
        return listRepository.findAll().stream()
            .map(listMapper::toListResponseDTO)
            .collect(Collectors.toList());
    }
}