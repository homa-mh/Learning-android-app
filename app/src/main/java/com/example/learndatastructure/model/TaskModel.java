package com.example.learndatastructure.model;

public class TaskModel {
    private int id;
    private boolean isCompleted;
    private String title;
    private String description;
    private String iconName;

    public TaskModel(int id, boolean isCompleted, String title, String description, String iconName) {
        this.id = id;
        this.isCompleted = isCompleted;
        this.title = title;
        this.description = description;
        this.iconName = iconName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
