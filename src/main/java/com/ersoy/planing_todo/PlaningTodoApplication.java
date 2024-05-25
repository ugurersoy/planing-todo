package com.ersoy.planing_todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@SpringBootApplication
public class PlaningTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaningTodoApplication.class, args);
	}

}
