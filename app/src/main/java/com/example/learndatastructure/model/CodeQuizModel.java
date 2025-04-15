package com.example.learndatastructure.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CodeQuizModel implements Serializable {
    private String question;
    private String expectedOutput;
    private String language;
    private String version;

    private String hint;

    public CodeQuizModel(String question, String expectedOutput, String language, String version, String hint) {
        this.question = question;
        this.expectedOutput = expectedOutput;
        this.language = language;
        this.version = version;
        this.hint = hint;
    }
    public String getQuestion() {
        return question;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public String getLanguage() {
        return language;
    }

    public String getVersion() {
        return version;
    }
    public String getHint() {return hint;}



}
