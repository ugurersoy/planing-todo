package com.ersoy.planing_todo.domain.model;

import com.ersoy.planing_todo.domain.dto.request.ToDoRequestDTO;
import com.ersoy.planing_todo.domain.dto.response.ToDoResponseDTO;
import com.ersoy.planing_todo.domain.enums.ToDoStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;

import static org.springframework.data.couchbase.core.mapping.id.GenerationStrategy.UNIQUE;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDo {

    @Id
    @GeneratedValue(strategy = UNIQUE)
    private String id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ToDoStatus status;
    private String userId;


    public static ToDo toToDo(ToDoRequestDTO toDoRequestDTO) {
        return ToDo.builder()
                .status(toDoRequestDTO.getStatus())
                .description(toDoRequestDTO.getDescription())
                .title(toDoRequestDTO.getTitle())
                .build();
    }

    public static ToDoResponseDTO toToDoResponseDTO(ToDo toDo) {
        return ToDoResponseDTO.builder()
                .id(toDo.getId())
                .description(toDo.getDescription())
                .status(toDo.getStatus())
                .title(toDo.getTitle())
                .build();
    }

}
