package com.nguyendinhkien.todolist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TodoList {
    @SerializedName("content")
    private List<Todo> todoList;

    public List<Todo> getTodoList() {
        return todoList;
    }
}
