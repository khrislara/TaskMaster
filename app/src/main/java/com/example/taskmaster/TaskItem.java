package com.example.taskmaster;

public class TaskItem {
    private String description;
    private int iconResourceId;

    public TaskItem(String description, int iconResourceId) {
        this.description = description;
        this.iconResourceId = iconResourceId;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }
}
