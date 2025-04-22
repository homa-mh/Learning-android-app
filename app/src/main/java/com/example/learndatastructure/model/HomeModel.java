package com.example.learndatastructure.model;

import com.google.gson.annotations.SerializedName;

public class HomeModel {

    private int id;
    private String title;
    private  String title_fa;
    private boolean expanded = false;
    private String category;

    @SerializedName("lesson_filename")
    private String lessonFilename;
    @SerializedName("lesson_filename_fa")
    private String lessonFilename_fa;
    @SerializedName("lesson_completed")
    private boolean lessonCompleted;

    @SerializedName("multi_quiz_filename")
    private String multiQuizFilename;
    @SerializedName("multi_quiz_filename_fa")
    private String multiQuizFilename_fa;
    @SerializedName("multi_quiz_score")
    private Integer multiQuizScore;

    @SerializedName("code_quiz_filename")
    private String codeQuizFilename;
    @SerializedName("code_quiz_filename_fa")
    private String codeQuizFilename_fa;
    @SerializedName("code_quiz_score")
    private Integer codeQuizScore;

    public HomeModel() {
    }

    // Getters and setters for all fields...

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }

    public String getTitle_fa() { return title_fa; }

    public String getLessonFilename() { return lessonFilename; }
    public String getLessonFilename_fa() { return lessonFilename_fa; }

    public boolean isLessonCompleted() { return lessonCompleted; }

    public String getMultiQuizFilename() { return multiQuizFilename; }
    public String getMultiQuizFilename_fa() { return multiQuizFilename_fa; }

    public Integer getMultiQuizScore() { return multiQuizScore; }

    public String getCodeQuizFilename() { return codeQuizFilename; }
    public String getCodeQuizFilename_fa() { return codeQuizFilename_fa; }

    public Integer getCodeQuizScore() { return codeQuizScore; }

    public void setId(int id) { this.id = id; }
    public void setCategory(String category) { this.category = category; }

    public void setTitle(String title) { this.title = title; }
    public void setTitle_fa(String title_fa) { this.title_fa = title_fa; }


    public void setLessonFilename(String lessonFilename) { this.lessonFilename = lessonFilename; }
    public void setLessonFilename_fa(String lessonFilename_fa) { this.lessonFilename_fa = lessonFilename_fa; }

    public void setLessonCompleted(boolean lessonCompleted) { this.lessonCompleted = lessonCompleted; }

    public void setMultiQuizFilename(String multiQuizFilename) { this.multiQuizFilename = multiQuizFilename; }
    public void setMultiQuizFilename_fa(String multiQuizFilename_fa) { this.multiQuizFilename_fa = multiQuizFilename_fa; }

    public void setMultiQuizScore(Integer multiQuizScore) { this.multiQuizScore = multiQuizScore; }

    public void setCodeQuizFilename(String codeQuizFilename) { this.codeQuizFilename = codeQuizFilename; }
    public void setCodeQuizFilename_fa(String codeQuizFilename_fa) { this.codeQuizFilename_fa = codeQuizFilename_fa; }

    public void setCodeQuizScore(Integer codeQuizScore) { this.codeQuizScore = codeQuizScore; }
    public boolean isExpanded() {return expanded;}
    public void setExpanded(boolean expanded) {this.expanded = expanded;}

}
