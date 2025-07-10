package com.example.learndatastructure.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "task_db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "tasks";

    public TaskDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "isCompleted INTEGER," +
                "title TEXT," +
                "description TEXT," +
                "iconName TEXT)";
        db.execSQL(query);

        // default tasks
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, 'مطالعه یک درس', 'یک درس از بخش آموزش را مطالعه کن', 'task_book')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, 'کامل کردن تمام دروس', 'تمام دروس را به پایان برسان', 'task_successful')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, 'پاسخ به اولین کوییز', 'اولین آزمون چندگزینه‌ای را کامل کن', 'task_quiz')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, 'کسب نمره کامل در یک کوییز', 'در یک آزمون نمره کامل کسب کن', 'task_score')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, 'پاسخ به تمرین کدنویسی', 'یک تمرین کدنویسی را با موفقیت حل کن', 'task_code')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, 'اتمام همه کوییزها', 'همه آزمون‌های چندگزینه‌ای را کامل کن', 'task_checklist')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, 'درس خون', 'در پنج درس نمره کامل بگیر', 'task_perfection')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, 'نابغه', 'در تمام دروس نمره کامل بگیر', 'task_mind')");



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
