package com.example.learndatastructure.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.learndatastructure.R;
import com.example.learndatastructure.utils.FontUtil;
import com.example.learndatastructure.utils.LocaleHelper;
import com.example.learndatastructure.viewModel.SettingsViewModel;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {}
    private SettingsViewModel viewModel;
    private Switch darkModeSwitch, soundSwitch, reminderSwitch;
    private Spinner spinnerLanguage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // گرفتن کانتکست ایمن
        Context context = requireContext();  // یا getContext() اگر مطمئنی نال نیست
        // گرفتن فونت با توجه به زبان
        Typeface typeface = FontUtil.getFontByLanguage(context);
        // اعمال فونت به کل ویوی فرگمنت
        FontUtil.applyFontToView(context, view, typeface);

        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        darkModeSwitch = view.findViewById(R.id.switch_dark_mode);
        soundSwitch = view.findViewById(R.id.switch_sound);
        reminderSwitch = view.findViewById(R.id.switch_reminder);
        spinnerLanguage = view.findViewById(R.id.spinner_language);


        viewModel.darkMode.observe(getViewLifecycleOwner(), value -> darkModeSwitch.setChecked(value));
        viewModel.sound.observe(getViewLifecycleOwner(), value -> soundSwitch.setChecked(value));
        viewModel.reminder.observe(getViewLifecycleOwner(), value -> reminderSwitch.setChecked(value));
        viewModel.language.observe(getViewLifecycleOwner(), this::updateLanguageSpinnerSelection);

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.toggleDarkMode(isChecked));
        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.toggleSound(isChecked));
        reminderSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> viewModel.toggleReminder(isChecked));

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
