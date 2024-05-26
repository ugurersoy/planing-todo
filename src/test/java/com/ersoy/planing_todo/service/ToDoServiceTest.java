package com.ersoy.planing_todo.service;

import com.ersoy.planing_todo.domain.dto.request.ToDoRequestDTO;
import com.ersoy.planing_todo.domain.dto.request.ToDoUpdateRequestDTO;
import com.ersoy.planing_todo.domain.dto.response.ToDoResponseDTO;
import com.ersoy.planing_todo.domain.model.ToDo;
import com.ersoy.planing_todo.domain.enums.ToDoStatus;
import com.ersoy.planing_todo.domain.model.User;
import com.ersoy.planing_todo.repository.ToDoRepository;
import com.ersoy.planing_todo.service.ToDoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {

    @InjectMocks
    private ToDoService toDoService;

    @Mock
    private ToDoRepository toDoRepository;

    private User user;
    private ToDo toDo;


    @BeforeEach
    public void setup() {
        user = getUser();
        toDo = getTodo();
    }

    @Test
    public void createToDoTesting() {
        ToDoRequestDTO toDoRequestDTO = getTodoInstance();
        ToDo expected = ToDo.toToDo(toDoRequestDTO);
        expected.setUserId(user.getId());

        when(toDoRepository.save(expected)).thenReturn(expected);
        String actual = toDoService.createToDo(toDoRequestDTO, user);

        assertEquals(expected.getId(), actual);
    }


    @Test
    public void updateToDoTesting() {
        ToDoUpdateRequestDTO toDoRequestDTO = getToDoUpdateRequestDTO();

        ToDo expected = getTodoInstanceByToDoUpdateRequestDTO(toDoRequestDTO);
        when(toDoRepository.findById(toDoRequestDTO.getId())).thenReturn(Optional.ofNullable(expected));
        when(toDoRepository.save(expected)).thenReturn(expected);

        String actual = toDoService.updateTodo(toDoRequestDTO);
        assertEquals(expected.getId(), actual);
    }


    @Test
    public void getToDoById() {
        when(toDoRepository.findByIdAndUserId(toDo.getId(), toDo.getUserId())).thenReturn(Optional.of(toDo));
        ToDoResponseDTO actual = toDoService.getById(toDo.getId(), user);

        assertNotNull(actual);
        assertEquals(toDo.getId(), actual.getId());
    }

    @Test
    public void deleteTodoById() {
        doNothing().when(toDoRepository).deleteByIdAndUserId(toDo.getId(), toDo.getUserId());

        toDoService.deleteById(toDo.getId(), user);

        verify(toDoRepository, times(1)).deleteByIdAndUserId(toDo.getId(), toDo.getUserId());
    }

    @Test
    public void getToDoListAllTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ToDo> toDoPage = new PageImpl<>(List.of(toDo), pageable, 1);

        when(toDoRepository.findAllByUserId(toDo.getUserId(), pageable)).thenReturn(toDoPage);

        Page<ToDoResponseDTO> response = toDoService.getToDoListAll(pageable, user);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        verify(toDoRepository, times(1)).findAllByUserId(toDo.getUserId(), pageable);
    }


    @Test
    public void getUserToDoListAll() {
        Pageable pageable = PageRequest.of(0, 10);
        ToDoStatus status = ToDoStatus.DONE;
        Page<ToDo> toDoPage = new PageImpl<>(List.of(toDo), pageable, 1);

        when(toDoRepository.findAllByStatusAndUserId(status.getValue(), toDo.getUserId(), pageable)).thenReturn(toDoPage);

        Page<ToDoResponseDTO> response = toDoService.getToDoListWithStatus(pageable, status, user);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        verify(toDoRepository, times(1)).findAllByStatusAndUserId(status.getValue(), toDo.getUserId(), pageable);
    }


    private ToDoRequestDTO getTodoInstance() {
        return ToDoRequestDTO.builder()
                .status(ToDoStatus.TO_DO)
                .description(RandomStringUtils.randomAlphabetic(5))
                .title(RandomStringUtils.randomAlphabetic(5)).build();
    }

    private ToDoUpdateRequestDTO getToDoUpdateRequestDTO() {
        return ToDoUpdateRequestDTO.builder()
                .status(ToDoStatus.TO_DO)
                .description(RandomStringUtils.randomAlphabetic(5))
                .title(RandomStringUtils.randomAlphabetic(5)).build();
    }

    private ToDo getTodoInstanceByToDoUpdateRequestDTO(ToDoUpdateRequestDTO toDoUpdateRequestDTO) {
        return ToDo.builder()
                .id(RandomStringUtils.randomAlphabetic(5))
                .status(toDoUpdateRequestDTO.getStatus())
                .description(toDoUpdateRequestDTO.getDescription())
                .title(toDoUpdateRequestDTO.getTitle())
                .build();
    }

    private ToDo getTodo() {
        return ToDo.builder()
                .id(RandomStringUtils.randomAlphabetic(5))
                .status(ToDoStatus.TO_DO)
                .description(RandomStringUtils.randomAlphabetic(5))
                .title(RandomStringUtils.randomAlphabetic(5))
                .userId(user.getId())
                .build();
    }

    private User getUser() {
        return User.builder().id(RandomStringUtils.randomAlphabetic(5)).build();
    }

}
