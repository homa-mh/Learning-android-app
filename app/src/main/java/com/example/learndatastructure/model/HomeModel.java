package com.example.learndatastructure.model;

import com.google.gson.annotations.SerializedName;

public class HomeModel {

    private int id;
    private String title;
    private boolean expanded = false;

    @SerializedName("lesson_filename")
    private String lessonFilename;

    @SerializedName("lesson_completed")
    private boolean lessonCompleted;

    @SerializedName("multi_quiz_filename")
    private String multiQuizFilename;

    @SerializedName("multi_quiz_score")
    private Integer multiQuizScore;

    @SerializedName("code_quiz_filename")
    private String codeQuizFilename;

    @SerializedName("code_quiz_score")
    private Integer codeQuizScore;

    // Getters and setters for all fields...

    public int getId() { return id; }
    public String getTitle() { return title; }

    public String getLessonFilename() { return lessonFilename; }
    public boolean isLessonCompleted() { return lessonCompleted; }

    public String getMultiQuizFilename() { return multiQuizFilename; }
    public Integer getMultiQuizScore() { return multiQuizScore; }

    public String getCodeQuizFilename() { return codeQuizFilename; }
    public Integer getCodeQuizScore() { return codeQuizScore; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }

    public void setLessonFilename(String lessonFilename) { this.lessonFilename = lessonFilename; }
    public void setLessonCompleted(boolean lessonCompleted) { this.lessonCompleted = lessonCompleted; }

    public void setMultiQuizFilename(String multiQuizFilename) { this.multiQuizFilename = multiQuizFilename; }
    public void setMultiQuizScore(Integer multiQuizScore) { this.multiQuizScore = multiQuizScore; }

    public void setCodeQuizFilename(String codeQuizFilename) { this.codeQuizFilename = codeQuizFilename; }
    public void setCodeQuizScore(Integer codeQuizScore) { this.codeQuizScore = codeQuizScore; }
    public boolean isExpanded() {return expanded;}
    public void setExpanded(boolean expanded) {this.expanded = expanded;}

}
