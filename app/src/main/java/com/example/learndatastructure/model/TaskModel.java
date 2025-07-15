package com.example.learndatastructure.model;

public class TaskModel {
    private int id;
    private boolean isCompleted;
    private String titleEn;
    private String titleFa;
    private String iconName;

    public TaskModel(int id, boolean isCompleted, String titleEn, String titleFa, String iconName) {
        this.id = id;
        this.isCompleted = isCompleted;
        this.titleEn = titleEn;
        this.titleFa = titleFa;
        this.iconName = iconName;
    }

    public int getId() {
        return id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public String getTitleFa() {
        return titleFa;
    }

    public String getIconName() {
        return iconName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public void setTitleFa(String titleFa) {
        this.titleFa = titleFa;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
