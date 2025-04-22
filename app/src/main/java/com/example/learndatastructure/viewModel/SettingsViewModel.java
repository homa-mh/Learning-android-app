package com.example.learndatastructure.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.learndatastructure.data.SettingsRepository;
import com.example.learndatastructure.model.SettingsModel;

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
    }

    public void toggleReminder(boolean value) {
        settings.setReminders(value);
        reminder.setValue(value);
        repo.saveSettings(settings);
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


}
