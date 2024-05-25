package com.ersoy.planing_todo.repository;

import com.ersoy.planing_todo.domain.model.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import java.util.Optional;


public interface UserRepository  extends CouchbaseRepository<User, String> {
    Optional<User> findByEmail(String username);
}
