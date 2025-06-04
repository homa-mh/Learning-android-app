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
    private SettingsModel settings;

    public MutableLiveData<Boolean> darkMode = new MutableLiveData<>();
    public MutableLiveData<Boolean> reminder = new MutableLiveData<>();
    public MutableLiveData<Boolean> sound = new MutableLiveData<>();
    public MutableLiveData<String> language = new MutableLiveData<>();


    public SettingsViewModel(@NonNull Application application) {
        super(application);
        repo = new SettingsRepository(application);
        settings = repo.getSettings();

        // Load initial values
        darkMode.setValue(settings.isDarkMode());
        reminder.setValue(settings.isReminders());
        sound.setValue(settings.isSound());
        language.setValue(settings.getLanguage());
    }

    public void toggleDarkMode(boolean value) {
        settings.setDarkMode(value);
        darkMode.setValue(value);
        repo.saveSettings(settings);
        AppCompatDelegate.setDefaultNightMode(
                value ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }


    public void toggleReminder(boolean value) {
        settings.setReminders(value);
        reminder.setValue(value);
        repo.saveSettings(settings);

        int[] time = settings.getReminderTime();
        if (value) {
            ReminderHelper.setDailyReminder(getApplication(), time[0], time[1]);
        } else {
            ReminderHelper.cancelReminder(getApplication());
        }
    }

    public void updateReminderTime(int hour, int minute) {
        settings.setReminderTime(new int[]{hour, minute});
        repo.saveSettings(settings);

        if (settings.isReminders()) {
            ReminderHelper.setDailyReminder(getApplication(), hour, minute);
        }
    }



    public void toggleSound(boolean value) {
        settings.setSound(value);
        sound.setValue(value);
        repo.saveSettings(settings);
    }

    public void changeLanguage(String lang) {
        settings.setLanguage(lang);
        language.setValue(lang);
        repo.saveSettings(settings);
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
        return settings.getReminderTime();
    }



}
