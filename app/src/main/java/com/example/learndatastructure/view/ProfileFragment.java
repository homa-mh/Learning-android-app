package com.example.learndatastructure.view;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learndatastructure.R;
import com.example.learndatastructure.model.TaskModel;
import com.example.learndatastructure.utils.FontUtil;
import com.example.learndatastructure.utils.LocaleHelper;
import com.example.learndatastructure.viewModel.SettingsViewModel;
import com.example.learndatastructure.viewModel.TaskViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {}
    private SettingsViewModel viewModel;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch darkModeSwitch, soundSwitch, reminderSwitch;
    private Spinner spinnerLanguage;
    private CardView cardViewShare, cardLanguage, cardContact, cardSound, cardTheme;
    private LinearLayout linearSetTime;
    private TextView txtReminderTime, txtVersion;
    private AdView adView;

    // for gamifiction
    private RecyclerView recyclerView;
    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // getting context (since this is not an activity)
        Context context = requireContext();
        // getting font face
        Typeface typeface = FontUtil.getFontByLanguage(context);
        // applying the font
        FontUtil.applyFontToView(context, view, typeface);

        // settings view model
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        // ui
        darkModeSwitch = view.findViewById(R.id.switch_dark_mode);
        soundSwitch = view.findViewById(R.id.switch_sound);
        reminderSwitch = view.findViewById(R.id.switch_reminder);
        spinnerLanguage = view.findViewById(R.id.spinner_language);
        cardViewShare = view.findViewById(R.id.cardViewShare);
        linearSetTime = view.findViewById(R.id.linearSetReminderTime);
        txtReminderTime = view.findViewById(R.id.txtReminderTime);
        cardLanguage = view.findViewById(R.id.cardViewLanguage);
        txtVersion = view.findViewById(R.id.txtAppVersion);
        adView = view.findViewById(R.id.adView);
        cardContact = view.findViewById(R.id.cardViewContact);
        cardSound = view.findViewById(R.id.cardViewSound);
        cardTheme = view.findViewById(R.id.cardViewTheme);




        // GAMIFICTION (TASK)

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new TaskAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            adapter = new TaskAdapter(requireContext(), tasks);
            recyclerView.setAdapter(adapter);
        });




        // google ads:
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        MobileAds.initialize(requireContext());


        // Set alarm time
        int[] time = viewModel.getReminderTime();
        String formatted = String.format("%02d:%02d", time[0], time[1]);
        txtReminderTime.setText(formatted);


        // default value for settings (gets data from view model)
        viewModel.darkMode.observe(getViewLifecycleOwner(), value -> {
            darkModeSwitch.setChecked(value);
            darkModeSwitch.setText(value ? R.string.settings_on : R.string.settings_off);
        });
        viewModel.sound.observe(getViewLifecycleOwner(), value -> {
            soundSwitch.setChecked(value);
            soundSwitch.setText(value ? R.string.settings_on : R.string.settings_off);
        });
        viewModel.reminder.observe(getViewLifecycleOwner(), value -> {
            reminderSwitch.setChecked(value);
            reminderSwitch.setText(value ? R.string.settings_on : R.string.settings_off);
        });
        viewModel.language.observe(getViewLifecycleOwner(), this::updateLanguageSpinnerSelection);


        // OnCheckedChange listeners for switches
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.toggleDarkMode(isChecked);
            darkModeSwitch.setText(isChecked ? R.string.settings_on : R.string.settings_off);
        });
        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.toggleSound(isChecked);
            soundSwitch.setText(isChecked ? R.string.settings_on : R.string.settings_off);
        });
        reminderSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.toggleReminder(isChecked);
            reminderSwitch.setText(isChecked ? R.string.settings_on : R.string.settings_off);
        });


        // on click listeners for card views
        cardSound.setOnClickListener(v -> {
            soundSwitch.setChecked(!soundSwitch.isChecked());
        });
        cardTheme.setOnClickListener(v -> {
            darkModeSwitch.setChecked(!darkModeSwitch.isChecked());
        });
        cardLanguage.setOnClickListener(v -> {
            spinnerLanguage.performClick();
        });
        linearSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] currentTime = viewModel.getReminderTime();

                TimePickerDialog dialog = new TimePickerDialog(getContext(),
                        (timePicker, hour, minute) -> {
                            viewModel.updateReminderTime(hour, minute);
                            String formatted = String.format("%02d:%02d", hour, minute);
                            Toast.makeText(getContext(), "Reminder set to " + formatted, Toast.LENGTH_SHORT).show();
                            txtReminderTime.setText( formatted);
                        },
                        currentTime[0],
                        currentTime[1],
                        true
                );
                dialog.show();
            }
        });



        // language:
        // Setup language spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.language_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                if (!selectedLanguage.equals(viewModel.language.getValue())) {
                    // saving new language in SharedPreferences
                    viewModel.changeLanguage(selectedLanguage);

                    // set the application language (locale)
                    LocaleHelper.setLocale(requireContext());

                    // restarting activity to apply changes
                    requireActivity().recreate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // share
        cardViewShare.setOnClickListener(v -> {
            String shareMessage = viewModel.getShareMessage();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)));
        });

        // email contact
        cardContact.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:homa.mwr@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, R.string.contact_subject);
            intent.putExtra(Intent.EXTRA_TEXT, "" );

            try {
                startActivity(Intent.createChooser(intent, getString(R.string.email_via)));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), getString(R.string.no_email), Toast.LENGTH_SHORT).show();
            }
        });






        // set application version to txt version
        try {
            String version = requireContext()
                    .getPackageManager()
                    .getPackageInfo(requireContext().getPackageName(), 0)
                    .versionName;

            txtVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }




        return view;
    }

    private void updateLanguageSpinnerSelection(String language) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinnerLanguage.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(language);
            if (position >= 0) {
                spinnerLanguage.setSelection(position);
            }
        }
    }


}
