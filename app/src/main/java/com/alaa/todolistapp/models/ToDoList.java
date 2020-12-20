package com.alaa.todolistapp.models;

import java.util.ArrayList;

public class ToDoList {

    private String name;
    private ArrayList<Task> tasks = new ArrayList<>();

    public ToDoList() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
