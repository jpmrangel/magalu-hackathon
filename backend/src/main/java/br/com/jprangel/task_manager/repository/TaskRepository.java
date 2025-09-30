package br.com.jprangel.task_manager.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.jprangel.task_manager.model.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, String>{
    List<TaskEntity> findByListId(String listId);

    @Transactional(readOnly = true)
    @Query("SELECT t FROM TaskEntity t " +
            "WHERE t.finishingDate IS NOT NULL " +
            "AND t.reminderTime IS NOT NULL " +
            "AND t.expectedFinishingDate <= :limitTime")
    Stream<TaskEntity> findTasksForReminder(@Param("limitTime") LocalDateTime limitTime);
}
