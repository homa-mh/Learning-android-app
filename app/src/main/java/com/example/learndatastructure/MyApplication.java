// MyApplication.java
package com.example.learndatastructure;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.learndatastructure.data.SettingsRepository;
import com.example.learndatastructure.utils.LocaleHelper;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        // Apply locale changes here
        Context context = LocaleHelper.setLocale(base);
        super.attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Use SettingsRepository to get stored settings
        SettingsRepository repo = new SettingsRepository(this);
        boolean isDarkMode = repo.getSettings().isDarkMode();

        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

}
