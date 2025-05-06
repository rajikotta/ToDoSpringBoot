package com.raji.todo.service;

import com.raji.todo.models.db.TaskEntity;
import com.raji.todo.models.dto.TaskDto;
import com.raji.todo.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TaskEntity createOrUpdateTask(TaskDto taskDto) {
        TaskEntity taskEntity = modelMapper.map(taskDto, TaskEntity.class);
        return taskRepository.save(taskEntity);
    }

    @Override
    public Map<String, List<TaskDto>> findAll(Long id, String status) {
        boolean isCompleted = false;
        if (status != null) {
            isCompleted = status.equalsIgnoreCase("completed");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        List<TaskEntity> tasks = taskRepository.findByOwnerIdAndIsCompletedOrderByCreatedAtDesc(id, isCompleted);
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.groupingBy(taskDto -> taskDto.getCreatedAt().format(formatter)));

    }

    @Override
    public Optional<TaskEntity> findOne(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        taskRepository.delete(task);

    }


}
