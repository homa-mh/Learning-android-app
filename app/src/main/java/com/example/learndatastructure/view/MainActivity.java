package com.example.learndatastructure.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.learndatastructure.R;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearHome, linearProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

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

}
