package com.example.learndatastructure.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LogoutDialogFragment extends DialogFragment {

    public interface LogoutDialogListener {
        void onConfirmLogout();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireActivity())
                .setTitle("خروج از حساب")
                .setMessage("آیا مطمئن هستید که می‌خواهید خارج شوید؟")
                .setPositiveButton("بله", (dialog, which) -> {
                    if (getActivity() instanceof LogoutDialogListener) {
                        ((LogoutDialogListener) getActivity()).onConfirmLogout();
                    }
                })
                .setNegativeButton("خیر", null)
                .create();
    }
}
