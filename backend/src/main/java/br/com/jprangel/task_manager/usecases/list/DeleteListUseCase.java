package br.com.jprangel.task_manager.usecases.list;

import br.com.jprangel.task_manager.exceptions.ListDeletionNotAllowedException;
import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.repository.ListRepository;
import br.com.jprangel.task_manager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteListUseCase {

    private final ListRepository listRepository;
    private final TaskRepository taskRepository;

    public void execute(String id) {
        ListEntity list = listRepository.findById(id)
                .orElseThrow(() -> new ListNotFoundException("Lista não encontrada com o ID: " + id));

        boolean hasTasks = taskRepository.existsByListId(list.getId());
        if (hasTasks) {
            throw new ListDeletionNotAllowedException("Não é possível deletar uma lista que possui tarefas");
        }

        listRepository.delete(list);
    }
}
