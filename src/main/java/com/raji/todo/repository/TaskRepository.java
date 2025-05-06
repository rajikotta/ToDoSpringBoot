package com.raji.todo.repository;

import com.raji.todo.models.db.TaskEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    public List<TaskEntity> findAllByOwnerId(Long userId);
    public List<TaskEntity> findByOwnerIdAndIsCompletedOrderByCreatedAtDesc(Long userId, boolean isCompleted);
    public void deleteTaskEntitiesById(Long id);
}
