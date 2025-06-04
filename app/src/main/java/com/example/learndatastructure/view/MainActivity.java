package com.example.learndatastructure.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.learndatastructure.R;
import com.example.learndatastructure.utils.FontUtil;
import com.example.learndatastructure.utils.LocaleHelper;
import com.example.learndatastructure.viewModel.SettingsViewModel;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearHome, linearProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayoutDirection();



        Typeface typeface = FontUtil.getFontByLanguage(this);
        FontUtil.applyFontToView(this, findViewById(android.R.id.content), typeface);


        linearHome = findViewById(R.id.linear_home);
        linearProfile = findViewById(R.id.linear_profile);

        String tab = getIntent().getStringExtra("tab");
        if ("profile".equals(tab)) {
            loadFragment(new ProfileFragment(), true);
            highlightSelectedTab(false);
        } else {
            loadFragment(new HomeFragment(), false);
            highlightSelectedTab(true);
        }

        linearHome.setOnClickListener(v -> {
            loadFragment(new HomeFragment(), false);  // coming from right to left
            highlightSelectedTab(true);
        });

        linearProfile.setOnClickListener(v -> {
            loadFragment(new ProfileFragment(), true);  // coming from left to right
            highlightSelectedTab(false);
        });

    }

    private void loadFragment(Fragment fragment, boolean toRight) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        // Prevent reloading the same fragment
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (toRight) {
            transaction.setCustomAnimations(
                    R.anim.slide_in_right,  // enter
                    R.anim.slide_out_left   // exit
            );
        } else {
            transaction.setCustomAnimations(
                    R.anim.slide_in_left,   // enter
                    R.anim.slide_out_right  // exit
            );
        }

        transaction.replace(R.id.fragment_container, fragment).commit();
    }




    private void highlightSelectedTab(boolean isHomeSelected) {
        // Set background or icon tint to indicate selection
        if (isHomeSelected) {
            linearHome.setBackgroundResource(R.color.menu);
            linearProfile.setBackgroundResource(R.color.menu_unselected);
        } else {
            linearHome.setBackgroundResource(R.color.menu_unselected);
            linearProfile.setBackgroundResource(R.color.menu);
        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase));
    }
    private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tab again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000); // 2 seconds
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLayoutDirection();
    }

    public  void setLayoutDirection(){
        SharedPreferences prefs = getSharedPreferences("app_settings", MODE_PRIVATE);
        String lang = prefs.getString("language", "English");

        if (lang.equals("English")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }



}
