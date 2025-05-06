package com.raji.todo.service;

import com.raji.todo.models.db.TaskEntity;
import com.raji.todo.models.dto.TaskDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskService {


    TaskEntity createOrUpdateTask(TaskDto taskDto);

    Map<String, List<TaskDto>> findAll(Long id, String status);


    Optional<TaskEntity> findOne(Long id);

    void delete(Long id);
}
