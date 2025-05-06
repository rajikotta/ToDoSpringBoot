package com.raji.todo.models.db;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
    private Long id;
    private String title;
    private String remark;
    private boolean isCompleted;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

}
