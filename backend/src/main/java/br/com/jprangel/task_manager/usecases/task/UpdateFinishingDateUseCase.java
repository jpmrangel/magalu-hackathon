package br.com.jprangel.task_manager.usecases.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.dto.UpdateFinishingDateDTO;
import br.com.jprangel.task_manager.exceptions.TaskNotFoundException;
import br.com.jprangel.task_manager.mapper.TaskMapper;
import br.com.jprangel.task_manager.model.TaskEntity;
import br.com.jprangel.task_manager.repository.TaskRepository;

@Service
public class UpdateFinishingDateUseCase {
  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private TaskMapper taskMapper;

  public TaskResponseDTO execute(String id, UpdateFinishingDateDTO dto) {
    TaskEntity task = taskRepository.findById(id)
      .orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada com o ID: " + id));

      if (dto.getFinishingDate() == null) task.setFinishingDate(null);
      else task.setFinishingDate(dto.getFinishingDate());

      TaskEntity updatedTask = taskRepository.save(task);
      return taskMapper.toTaskResponseDTO(updatedTask);

  }
}
