package com.example.learndatastructure.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.learndatastructure.R;

public class TaskDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "task_db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "tasks";
    private final Context context;


    public TaskDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "isCompleted INTEGER," +
                "title_en TEXT," +
                "title_fa TEXT," +
                "iconName TEXT)";
        db.execSQL(query);

        // default tasks
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title_en, title_fa, iconName) VALUES (0, 'Study a Lesson', 'مطالعه‌ی یک درس', 'task_book')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title_en, title_fa, iconName) VALUES (0, 'Complete All Lessons', 'اتمام همه‌ی درس‌ها', 'task_successful')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title_en, title_fa, iconName) VALUES (0, 'Answer the First Quiz', 'پاسخ به اولین آزمون', 'task_quiz')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title_en, title_fa, iconName) VALUES (0, 'Get a Perfect Score on a Quiz', 'کسب نمره کامل در آزمون', 'task_score')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title_en, title_fa, iconName) VALUES (0, 'Solve a Coding Exercise', 'حل یک تمرین کدنویسی', 'task_code')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title_en, title_fa, iconName) VALUES (0, 'Complete All Quizzes', 'تکمیل همه‌ی آزمون‌ها', 'task_checklist')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title_en, title_fa, iconName) VALUES (0, 'Top Student', 'دانشجوی برتر', 'task_perfection')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title_en, title_fa, iconName) VALUES (0, 'Genius', 'نابغه', 'task_mind')");


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
