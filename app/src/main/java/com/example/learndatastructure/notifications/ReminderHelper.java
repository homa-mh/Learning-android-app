package com.example.learndatastructure.notifications;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class ReminderHelper {

    @SuppressLint("ScheduleExactAlarm")
    public static void setDailyReminder(Context context, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // اگه ساعت امروز گذشته، بزارش برای فردا
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getPendingIntent(context);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @SuppressLint("ScheduleExactAlarm")
    public static void scheduleNextReminder(Context context) {
        Calendar next = Calendar.getInstance();

        next.set(Calendar.HOUR_OF_DAY, 19);   // ساعت مورد نظر برای فردا (مثلاً 9 صبح)
        next.set(Calendar.MINUTE, 59);
        next.set(Calendar.SECOND, 0);

        if (next.getTimeInMillis() < System.currentTimeMillis()) {
            next.add(Calendar.DAY_OF_YEAR, 1);
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getPendingIntent(context);

        if (alarmManager != null && pendingIntent != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, next.getTimeInMillis(), pendingIntent);
        }

    }

    private static PendingIntent getPendingIntent(Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        return PendingIntent.getBroadcast(context, 1001, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getPendingIntent(context);
        alarmManager.cancel(pendingIntent);
    }
}
