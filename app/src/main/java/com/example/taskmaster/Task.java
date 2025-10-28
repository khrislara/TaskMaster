package com.example.TaskMaster;

public class Task {
    public String title;
    public boolean completed;
    public int priority;

    public Task(String title, boolean completed, int priority) {
        this.title = title;
        this.completed = completed;
        this.priority = priority;
    }
}
