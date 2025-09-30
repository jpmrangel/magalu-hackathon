package br.com.jprangel.task_manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jprangel.task_manager.model.ListEntity;

public interface ListRepository extends JpaRepository<ListEntity, String>{
    Optional<ListEntity> findByName(String name);
}
