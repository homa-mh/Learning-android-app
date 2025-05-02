package com.example.learndatastructure.view;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

import com.example.learndatastructure.R;
import com.example.learndatastructure.utils.FontUtil;
import com.example.learndatastructure.utils.LocaleHelper;
import com.example.learndatastructure.viewModel.SettingsViewModel;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {}
    private SettingsViewModel viewModel;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch darkModeSwitch, soundSwitch, reminderSwitch;
    private Spinner spinnerLanguage;
    private CardView cardViewShare, cardViewLogout, cardViewDelete;
    private LinearLayout linearSetTime;
    private TextView txtReminderTime;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // گرفتن کانتکست ایمن
        Context context = requireContext();
        // گرفتن فونت با توجه به زبان
        Typeface typeface = FontUtil.getFontByLanguage(context);
        // اعمال فونت به کل ویوی فرگمنت
        FontUtil.applyFontToView(context, view, typeface);

        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        darkModeSwitch = view.findViewById(R.id.switch_dark_mode);
        soundSwitch = view.findViewById(R.id.switch_sound);
        reminderSwitch = view.findViewById(R.id.switch_reminder);
        spinnerLanguage = view.findViewById(R.id.spinner_language);
        cardViewShare = view.findViewById(R.id.cardViewShare);
        cardViewLogout = view.findViewById(R.id.cardViewLogout);
        cardViewDelete = view.findViewById(R.id.cardViewDelete);
        linearSetTime = view.findViewById(R.id.linearSetReminderTime);
        txtReminderTime = view.findViewById(R.id.txtReminderTime);

        // Set initial time
        int[] time = viewModel.getReminderTime();
        String formatted = String.format("%02d:%02d", time[0], time[1]);
        txtReminderTime.setText(formatted);


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

        linearSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] currentTime = viewModel.getReminderTime(); // e.g., [8, 0]

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



//        language:
// 🟡 Setup language spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.language_options, // defined in res/values/strings.xml
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                if (!selectedLanguage.equals(viewModel.language.getValue())) {
                    // ذخیره زبان انتخاب‌شده در SharedPreferences از طریق ViewModel
                    viewModel.changeLanguage(selectedLanguage);

                    // اعمال locale جدید از SharedPreferences
                    LocaleHelper.setLocale(requireContext());

                    // ریستارت activity برای اعمال تغییر زبان
                    requireActivity().recreate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

//        share
        cardViewShare.setOnClickListener(v -> {
            String shareMessage = viewModel.getShareMessage();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

// logout
        // چک کردن وضعیت ورود
        if (viewModel.isLoggedIn()) {
            cardViewLogout.setVisibility(View.VISIBLE);
            cardViewDelete.setVisibility(View.VISIBLE); // فعلاً GONE بمونه اگر delete کامل نشده
        } else {
            cardViewLogout.setVisibility(View.GONE);
            cardViewDelete.setVisibility(View.GONE);
        }

        cardViewLogout.setOnClickListener(v -> {

                new LogoutDialogFragment().show(getParentFragmentManager(), "LogoutDialog");
        });




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
