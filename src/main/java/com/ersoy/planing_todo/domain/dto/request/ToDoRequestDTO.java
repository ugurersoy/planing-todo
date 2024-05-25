package com.ersoy.planing_todo.domain.dto.request;


import com.ersoy.planing_todo.domain.enums.ToDoStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDoRequestDTO {
    @NotNull(message = "title must not be null")
    private String title;
    private String description;
    private ToDoStatus status;
}
