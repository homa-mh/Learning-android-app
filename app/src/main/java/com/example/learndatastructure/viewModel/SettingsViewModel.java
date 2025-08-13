package com.example.learndatastructure.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.learndatastructure.data.SettingsRepository;
import com.example.learndatastructure.model.SettingsModel;
import com.example.learndatastructure.notifications.ReminderHelper;

public class SettingsViewModel extends AndroidViewModel {
    private final SettingsRepository repo;

    public MutableLiveData<Boolean> darkMode = new MutableLiveData<>();
    public MutableLiveData<Boolean> reminder = new MutableLiveData<>();
    public MutableLiveData<Boolean> sound = new MutableLiveData<>();
    public MutableLiveData<String> language = new MutableLiveData<>();

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        repo = new SettingsRepository(application);
        loadSettingsFromRepo();
    }

    public void loadSettingsFromRepo() {
        SettingsModel settings = repo.getSettings();
        darkMode.setValue(settings.isDarkMode());
        reminder.setValue(settings.isReminders());
        sound.setValue(settings.isSound());
        language.setValue(settings.getLanguage());
    }

    public void toggleDarkMode(boolean value) {
        SettingsModel settings = repo.getSettings(); // تازه بگیر
        settings.setDarkMode(value);
        repo.saveSettings(settings);

        AppCompatDelegate.setDefaultNightMode(
                value ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        loadSettingsFromRepo(); // دوباره لود کن
    }

    public void toggleReminder(boolean value) {
        SettingsModel settings = repo.getSettings();
        settings.setReminders(value);
        repo.saveSettings(settings);

        reminder.setValue(value);

        int[] time = settings.getReminderTime();
        if (value) {
            ReminderHelper.setDailyReminder(getApplication(), time[0], time[1]);
        } else {
            ReminderHelper.cancelReminder(getApplication());
        }
    }

    public void updateReminderTime(int hour, int minute) {
        SettingsModel settings = repo.getSettings();
        settings.setReminderTime(new int[]{hour, minute});
        repo.saveSettings(settings);

        if (settings.isReminders()) {
            ReminderHelper.setDailyReminder(getApplication(), hour, minute);
        }
    }

    public void toggleSound(boolean value) {
        SettingsModel settings = repo.getSettings();
        settings.setSound(value);
        repo.saveSettings(settings);
        sound.setValue(value);
    }

    public void changeLanguage(String lang) {
        SettingsModel settings = repo.getSettings();
        settings.setLanguage(lang);
        repo.saveSettings(settings);
        language.setValue(lang);
    }

    public void logout() {
        repo.logout();
    }

    public String getShareMessage() {
        return "Hey! Check out this awesome app to learn data structures:\nhttps://play.google.com/store/apps/details?id=com.example.learndatastructure";
    }

    public boolean isLoggedIn() {
        return repo.isLoggedIn();
    }

    public int[] getReminderTime() {
        return repo.getSettings().getReminderTime();
    }
}
