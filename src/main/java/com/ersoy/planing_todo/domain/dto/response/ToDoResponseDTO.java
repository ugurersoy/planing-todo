package com.ersoy.planing_todo.domain.dto.response;

import com.ersoy.planing_todo.domain.enums.ToDoStatus;
import lombok.*;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDoResponseDTO implements Serializable {
    private String id;
    private String title;
    private String description;
    private ToDoStatus status;
}
