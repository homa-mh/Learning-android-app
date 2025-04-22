package com.example.learndatastructure.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontUtil {
    public static void applyFontToView(Context context, View view, Typeface typeface) {
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(typeface);
        } else if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                applyFontToView(context, ((ViewGroup) view).getChildAt(i), typeface);
            }
        }
    }

    public static Typeface getFontByLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE);
        String lang = prefs.getString("language", "English");

        if (lang.equals("Persian")) {
            return Typeface.createFromAsset(context.getAssets(), "fonts/sans.ttf");
        } else {
            return Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
        }
    }
}
