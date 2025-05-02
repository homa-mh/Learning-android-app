package com.example.learndatastructure.data;

import android.content.Context;
import com.example.learndatastructure.model.SettingsModel;

public class SettingsRepository {
    private final SettingsLocalStorageManager storage;

    public SettingsRepository(Context context) {
        storage = new SettingsLocalStorageManager(context);
    }

    public SettingsModel getSettings() {
        return new SettingsModel(
                storage.isDarkMode(),
                storage.isReminderEnabled(),
                storage.isSoundEnabled(),
                storage.getLanguage(),
                storage.getReminderTime() // returns int[]
        );
    }

    public void saveSettings(SettingsModel settings) {
        storage.setDarkMode(settings.isDarkMode());
        storage.setReminderEnabled(settings.isReminders());
        storage.setSoundEnabled(settings.isSound());
        storage.setLanguage(settings.getLanguage());
        int[] time = settings.getReminderTime();
        storage.setReminderTime(time[0], time[1]);
    }


    public void saveAuth(String token, String username) {
        storage.saveAuthToken(token);
        storage.saveUsername(username);
    }

    public void logout() {
        storage.clearAuthData();
    }
    public boolean isLoggedIn() {
        return storage.getAuthToken() != null && !storage.getAuthToken().isEmpty();
    }

}
