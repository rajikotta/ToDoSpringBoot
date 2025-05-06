package com.raji.todo.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raji.todo.models.db.UserEntity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    @JsonIgnore
    private Long id;
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    private String remark;
    private boolean isCompleted;
    private LocalDateTime createdAt;
    private UserEntity owner;

}
