package com.ersoy.planing_todo.service;

import com.ersoy.planing_todo.domain.dto.request.ToDoRequestDTO;
import com.ersoy.planing_todo.domain.dto.request.ToDoUpdateRequestDTO;
import com.ersoy.planing_todo.domain.dto.response.ToDoResponseDTO;
import com.ersoy.planing_todo.domain.enums.ToDoStatus;
import com.ersoy.planing_todo.domain.model.ToDo;
import com.ersoy.planing_todo.domain.model.User;
import com.ersoy.planing_todo.exception.types.DataNotFoundException;
import com.ersoy.planing_todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public String createToDo(ToDoRequestDTO toDoRequestDTO, User user) {
        log.info("create process started");
        ToDo toDo = ToDo.toToDo(toDoRequestDTO);
        toDo.setUserId(user.getId());
        return toDoRepository.save(toDo).getId();
    }

    public String updateTodo(ToDoUpdateRequestDTO toDoUpdateRequestDTO) {
        log.info("update process started");
        ToDo toDo = toDoRepository.findById(toDoUpdateRequestDTO.getId())
                .orElseThrow(() -> new DataNotFoundException("This item could not be found :"+toDoUpdateRequestDTO.getId()));
        toDo.setDescription(toDoUpdateRequestDTO.getDescription());
        toDo.setTitle(toDoUpdateRequestDTO.getTitle());
        toDo.setStatus(toDoUpdateRequestDTO.getStatus());
        return toDoRepository.save(toDo).getId();
    }

    public ToDoResponseDTO getById(String id,User user){
        log.info("getById process started");
        return toDoRepository.findByIdAndUserId(id,user.getId()).map(ToDo::toToDoResponseDTO)
                .orElseThrow(() -> new DataNotFoundException("This item could not be found by this id and this user:"));
    }

    public void deleteById(String id,User user){
        log.info("delete process started");
        toDoRepository.deleteByIdAndUserId(id,user.getId());
    }

    public Page<ToDoResponseDTO> getToDoListAll(Pageable pageable,User user) {
        log.info("get todo list all process started");
        Page<ToDo> toDoList = toDoRepository.findAllByUserId(user.getId(),pageable);
        return getToDoResponseDTOS(toDoList);
    }

    public Page<ToDoResponseDTO> getToDoListWithStatus(Pageable pageable, ToDoStatus status,User user) {
        log.info("getToDoListWithStatus process started");
        Page<ToDo> toDoList = toDoRepository.findByStatusAndUserId(status,user.getId(), pageable);
        return getToDoResponseDTOS(toDoList);
    }

    private static PageImpl<ToDoResponseDTO> getToDoResponseDTOS(Page<ToDo> toDoList) {
        List<ToDoResponseDTO> toDoResponseDTO = toDoList.stream().map(ToDo::toToDoResponseDTO).toList();
        return new PageImpl<>(toDoResponseDTO, toDoList.getPageable(), toDoList.getTotalElements());
    }


}
