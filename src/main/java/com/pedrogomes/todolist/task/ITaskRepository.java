package com.pedrogomes.todolist.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;


public interface ITaskRepository extends JpaRepository<TaskModel, UUID>, CrudRepository<TaskModel, UUID> {
    List<TaskModel> findByUserId(UUID userId);
}
