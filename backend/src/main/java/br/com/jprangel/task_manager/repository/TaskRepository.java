package br.com.jprangel.task_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jprangel.task_manager.model.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, String>{
    List<TaskEntity> findByListId(String listId);
}
