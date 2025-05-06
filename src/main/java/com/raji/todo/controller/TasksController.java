package com.raji.todo.controller;

import com.raji.todo.models.db.UserEntity;
import com.raji.todo.models.dto.TaskDto;
import com.raji.todo.service.AuthService;
import com.raji.todo.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@Validated
public class TasksController {

    private final TaskService taskService;
    private final AuthService authService;

    public TasksController(TaskService taskService, AuthService authService) {
        this.taskService = taskService;
        this.authService = authService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto saveTask(@Valid @RequestBody TaskDto taskDto) {
        String userId = getCurrentUserId();
        UserEntity user = authService.getUser(userId);
        taskDto.setOwner(user);
        taskDto.setCreatedAt(LocalDateTime.now());
        taskService.createOrUpdateTask(taskDto);

        return taskDto;
    }

    @GetMapping
    public Map<String, List<TaskDto>> findAllTasks(@RequestParam(required = false) String status) {
        String userId = getCurrentUserId();
        return taskService.findAll(Long.parseLong(userId), status);
    }

    private static String getCurrentUserId() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(required = true) String id) {

        taskService.delete(Long.parseLong(id));

    }
}
