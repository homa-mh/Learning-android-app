package com.example.learndatastructure.model;

import java.util.List;

public class LessonModel {
    private int id;
    private String title;
    private List<String> pages;

    public LessonModel(int id, String title, List<String> pages) {
        this.id = id;
        this.title = title;
        this.pages = pages;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getPages() {
        return pages;
    }
}
