package com.ersoy.planing_todo.repository;

import com.ersoy.planing_todo.domain.enums.ToDoStatus;
import com.ersoy.planing_todo.domain.model.ToDo;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ToDoRepository extends CouchbaseRepository<ToDo, String>{
    Page<ToDo> findAllByStatusAndUserId(String status,String id, Pageable pageable);

    Page<ToDo> findAllByUserId(String id, Pageable pageable);

    Optional<ToDo> findByIdAndUserId(String id, String userId);

    void deleteByIdAndUserId(String id, String userId);
}
