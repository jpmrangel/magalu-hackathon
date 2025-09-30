package br.com.jprangel.task_manager.usecases.task;

import br.com.jprangel.task_manager.dto.CreateTaskDTO;
import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.exceptions.ListNotFoundException;
import br.com.jprangel.task_manager.mapper.TaskMapper;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.model.TaskEntity;
import br.com.jprangel.task_manager.model.TaskPriority;
import br.com.jprangel.task_manager.repository.ListRepository;
import br.com.jprangel.task_manager.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTaskUseCaseTest {

    @InjectMocks
    private CreateTaskUseCase createTaskUseCase;

    @Mock
    private ListRepository listRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Test
    @DisplayName("Deve criar uma tarefa com sucesso")
    void shouldCreateTaskSuccessfully() {
        var listId = "list-123";
        var createTaskDTO = new CreateTaskDTO("Nova Tarefa", "Descrição", TaskPriority.HIGH, LocalDateTime.now().plusDays(1), listId);
        var listEntity = new ListEntity();
        listEntity.setId(listId);

        when(listRepository.findById(listId)).thenReturn(Optional.of(listEntity));
        when(taskRepository.save(any(TaskEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(taskMapper.toTaskResponseDTO(any(TaskEntity.class))).thenReturn(new TaskResponseDTO(null, "Nova Tarefa", null, null, null, null, null));

        var result = createTaskUseCase.execute(createTaskDTO);

        assertNotNull(result);
        assertEquals("Nova Tarefa", result.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar tarefa para uma lista que não existe")
    void shouldThrowExceptionWhenCreatingTaskForNonExistentList() {
        var listId = "list-inexistente";
        var createTaskDTO = new CreateTaskDTO("Nova Tarefa", "Descrição", TaskPriority.HIGH, LocalDateTime.now().plusDays(1), listId);

        when(listRepository.findById(listId)).thenReturn(Optional.empty());

        assertThrows(ListNotFoundException.class, () -> {
            createTaskUseCase.execute(createTaskDTO);
        });
    }
}