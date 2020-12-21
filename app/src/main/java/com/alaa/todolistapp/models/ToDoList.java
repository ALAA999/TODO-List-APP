package com.alaa.todolistapp.models;

import java.util.ArrayList;
import java.util.HashMap;

public class ToDoList {

    private String id;
    private String name;
    private HashMap<String, Task> tasks = new HashMap<>();

    public ToDoList() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Task> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<String, Task> tasks) {
        this.tasks = tasks;
    }
}
