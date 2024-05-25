package com.ersoy.planing_todo.domain.enums;


import lombok.Getter;


@Getter
public enum ToDoStatus {
    TO_DO("TO_DO"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private final String value;


    ToDoStatus(String value) {
        this.value = value;
    }
}
