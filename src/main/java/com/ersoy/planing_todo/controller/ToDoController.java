package com.ersoy.planing_todo.controller;


import com.ersoy.planing_todo.common.BaseController;

import com.ersoy.planing_todo.domain.dto.request.ToDoRequestDTO;
import com.ersoy.planing_todo.domain.dto.request.ToDoUpdateRequestDTO;
import com.ersoy.planing_todo.domain.dto.response.ToDoResponseDTO;
import com.ersoy.planing_todo.domain.enums.ToDoStatus;
import com.ersoy.planing_todo.service.ToDoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
@Tag(name = "ToDo Management", description = "Operations related to ToDo items management")
public class ToDoController extends BaseController {

    private final ToDoService toDoService;

    @PostMapping
    @Operation(summary = "Create Todo Item process", description = "This endpoint allows you to create a new ToDo item. " +
            "The ToDo item details are provided in the request body as a JSON object. " +
            "The current user's information is automatically included in the creation process."
    )
    public ResponseEntity<String> createToDoItem(@RequestBody ToDoRequestDTO requestDTO) {
        log.info("Request to create todo: {}", requestDTO);
        return ResponseEntity.ok(toDoService.createToDo(requestDTO, getCurrentUser()));
    }

    @PutMapping
    @Operation(summary = "Update Todo Item process", description = "This endpoint allows you to update a ToDo item. " +
            "The ToDo item details are provided in the request body as a JSON object. " +
            "The current user's information is automatically included in the update process."
    )
    public ResponseEntity<String> updateToDoItem(@RequestBody ToDoUpdateRequestDTO requestDTO) {
        log.info("Request to update todo: {}", requestDTO);
        return ResponseEntity.ok(toDoService.updateTodo(requestDTO));
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete ToDo item by ID and user",
            description = "Deletes a specific ToDo item by its ID for the current user."
    )
    public ResponseEntity<Boolean> updateToDoItem(@PathVariable("id") String id) {
        log.info("Request to delete Todo with id: {}", id);
        toDoService.deleteById(id, getCurrentUser());
        return ResponseEntity.ok(true);
    }

    @GetMapping
    @Operation(
            summary = "Get ToDo list by user",
            description = "Fetches a paginated list of ToDo items for the current user. " +
                    "The pageable parameter allows for specifying the page number and size."
    )
    public ResponseEntity<Page<ToDoResponseDTO>> getAllToDo(@PageableDefault Pageable pageable) {
        log.info("Request to get add todo list ");
        return ResponseEntity.ok(toDoService.getToDoListAll(pageable, getCurrentUser()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get ToDo item by ID and user",
            description = "Fetches a specific ToDo item by its ID for the current user."
    )
    public ResponseEntity<ToDoResponseDTO> getById(@PathVariable("id") String id) {
        log.info("Request to get todo by id: {}", id);
        return ResponseEntity.ok(toDoService.getById(id, getCurrentUser()));
    }

    @GetMapping("/{status}")
    @Operation(
            summary = "Get ToDo list by status and user",
            description = "Fetches a paginated list of ToDo items for the current user filtered by a specific status. " +
                    "The pageable parameter allows for specifying the page number and size."
    )
    public ResponseEntity<Page<ToDoResponseDTO>> getAllToDoByStatus(@PageableDefault Pageable pageable,
                                                                    @PathVariable("status") ToDoStatus status) {
        log.info("Request to get todo list by status: {}", status.name());
        return ResponseEntity.ok(toDoService.getToDoListWithStatus(pageable, status, getCurrentUser()));
    }


}
