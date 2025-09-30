package br.com.jprangel.task_manager.repository;

import br.com.jprangel.task_manager.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, String> {
    List<FileEntity> findByTaskId(String taskId);
}
