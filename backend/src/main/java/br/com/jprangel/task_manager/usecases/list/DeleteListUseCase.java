package br.com.jprangel.task_manager.usecases.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.repository.ListRepository;

@Service
public class DeleteListUseCase {

    @Autowired
    private ListRepository listRepository;

    public void execute(String id) {
        ListEntity list = listRepository.findById(id)
             .orElseThrow(() -> new ListNotFoundException("Lista não encontrada com o ID: " + id));
        listRepository.delete(list);
    }
}