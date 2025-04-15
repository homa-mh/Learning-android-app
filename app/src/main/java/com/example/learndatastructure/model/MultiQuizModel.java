package com.example.learndatastructure.model;

import java.io.Serializable;

public class MultiQuizModel implements Serializable {
    private String question;
    private String[] options;
    private int correctAnswerIndex;

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        if (options != null && correctAnswerIndex >= 0 && correctAnswerIndex < options.length) {
            return options[correctAnswerIndex];
        } else {
            return "";
        }
    }
}
