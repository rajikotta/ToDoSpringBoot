package com.raji.todo.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDto {

    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    @Size(min = 4, max = 15, message = "Name must be between 4 and 15 characters")
    private String name;
}
