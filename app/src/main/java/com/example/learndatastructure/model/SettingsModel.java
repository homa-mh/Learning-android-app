package com.example.learndatastructure.model;

import java.sql.Time;

public class SettingsModel {
    private boolean darkMode;
    private boolean reminders;
    private boolean sound;
    private String language;
    private int[] reminderTime;

    public SettingsModel(boolean darkMode, boolean reminders, boolean sound, String language, int[] reminderTime) {
        this.darkMode = darkMode;
        this.reminders = reminders;
        this.sound = sound;
        this.language = language;
        this.reminderTime = reminderTime;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public boolean isReminders() {
        return reminders;
    }

    public void setReminders(boolean reminders) {
        this.reminders = reminders;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



    public int[] getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(int[] reminderTime) {
        this.reminderTime = reminderTime;
    }

}
