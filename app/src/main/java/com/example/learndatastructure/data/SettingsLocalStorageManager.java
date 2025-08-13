package com.example.learndatastructure.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsLocalStorageManager {
    private static final String PREF_NAME = "app_settings";
    private final SharedPreferences prefs;

    public SettingsLocalStorageManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Settings
    public void setDarkMode(boolean enabled) {
        prefs.edit().putBoolean("dark_mode", enabled).commit();
    }

    public boolean isDarkMode() {
        return prefs.getBoolean("dark_mode", false);
    }

    public void setReminderEnabled(boolean enabled) {
        prefs.edit().putBoolean("reminder", enabled).apply();
    }
    public boolean isReminderEnabled() {
        return prefs.getBoolean("reminder", true);
    }


    public void setReminderTime(int hour, int minute) {
        int totalMinutes = hour * 60 + minute;
        prefs.edit().putInt("reminder_time_minutes", totalMinutes).apply();
    }

    public int[] getReminderTime() {
        int totalMinutes = prefs.getInt("reminder_time_minutes", 8 * 60); // default: 08:00
        return new int[]{ totalMinutes / 60, totalMinutes % 60 };
    }



    public void setSoundEnabled(boolean enabled) {
        prefs.edit().putBoolean("sound", enabled).apply();
    }

    public boolean isSoundEnabled() {
        return prefs.getBoolean("sound", true);
    }

    public void setLanguage(String lang) {
        prefs.edit().putString("language", lang).apply();
    }

    public String getLanguage() {
        return prefs.getString("language", "English");
    }

    // Auth (for later updates)
    public void saveAuthToken(String token) {
        prefs.edit().putString("auth_token", token).apply();
    }

    public String getAuthToken() {
        return prefs.getString("auth_token", null);
    }

    public void saveUsername(String username) {
        prefs.edit().putString("username", username).apply();
    }

    public String getUsername() {
        return prefs.getString("username", null);
    }

    public void clearAuthData() {
        prefs.edit().remove("auth_token").remove("username").apply();
    }
}
