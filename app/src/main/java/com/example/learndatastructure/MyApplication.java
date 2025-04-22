// MyApplication.java
package com.example.learndatastructure;

import android.app.Application;
import android.content.Context;

import com.example.learndatastructure.utils.LocaleHelper;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        Context context = LocaleHelper.setLocale(base); // کانتکست با زبان جدید
        super.attachBaseContext(context);               // این خط حیاتی بود!
    }
}
