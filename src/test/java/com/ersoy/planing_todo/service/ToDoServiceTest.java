package com.ersoy.planing_todo.service;

import com.ersoy.planing_todo.domain.dto.request.ToDoRequestDTO;
import com.ersoy.planing_todo.domain.model.ToDo;
import com.ersoy.planing_todo.domain.enums.ToDoStatus;
import com.ersoy.planing_todo.domain.model.User;
import com.ersoy.planing_todo.repository.ToDoRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {

    @InjectMocks
    ToDoService toDoService;

    @Mock
    ToDoRepository toDoRepository;

    @Test
    public void createToDoTesting(){

        User user = getUser();
        ToDoRequestDTO toDoRequestDTO = getTodoInstance();
        ToDo expected =ToDo.toToDo(toDoRequestDTO);
        expected.setUserId(user.getId());

        when(toDoRepository.save(expected)).thenReturn(expected);
        String actual = toDoService.createToDo(toDoRequestDTO,user);

        Assertions.assertEquals(expected.getId(),actual);
    }


    @Test
    public void updateToDoTesting(){


    }

    @Test
    public void getToDoListAllTest(){

    }

    @Test
    public void getToDoById(){

    }

    @Test
    public void deleteTodoById(){

    }



    @Test
    public void getUserToDoListAll(){

    }


     private ToDoRequestDTO getTodoInstance(){
         return ToDoRequestDTO.builder()
                 .status(ToDoStatus.TO_DO)
                 .description(RandomStringUtils.randomAlphabetic(5))
                 .title(RandomStringUtils.randomAlphabetic(5)).build();
     }

     private User getUser(){
        return User.builder().id(RandomStringUtils.randomAlphabetic(5)).build();
     }

}
