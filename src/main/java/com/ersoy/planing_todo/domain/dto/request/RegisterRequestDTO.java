package com.ersoy.planing_todo.domain.dto.request;

import com.ersoy.planing_todo.domain.dto.request.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    private String fistName;
    private String lastName;
    @ValidEmail
    private String email;
    @NotNull(message = "Password must not be null")
    private String password;
}
