package com.alaa.todolistapp.models;

public class ToDoList {
    private String id = "1";
    private String name = "Home";
    private String tasksNumber = "3 tasks";

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

    public String getTasksNumber() {
        return tasksNumber;
    }

    public void setTasksNumber(String tasksNumber) {
        this.tasksNumber = tasksNumber;
    }
}
