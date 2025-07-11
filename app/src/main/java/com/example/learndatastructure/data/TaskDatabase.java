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
                "title TEXT," +
                "description TEXT," +
                "iconName TEXT)";
        db.execSQL(query);

        // default tasks
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, '" + context.getString(R.string.task_name_book) + "', '" + context.getString(R.string.task_book) + "', 'task_book')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, '" + context.getString(R.string.task_name_successful) + "', '" + context.getString(R.string.task_successful) + "', 'task_successful')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, '" + context.getString(R.string.task_name_quiz) + "', '" + context.getString(R.string.task_quiz) + "', 'task_quiz')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, '" + context.getString(R.string.task_name_score) + "', '" + context.getString(R.string.task_score) + "', 'task_score')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, '" + context.getString(R.string.task_name_code) + "', '" + context.getString(R.string.task_code) + "', 'task_code')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, '" + context.getString(R.string.task_name_checklist) + "', '" + context.getString(R.string.task_checklist) + "', 'task_checklist')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, '" + context.getString(R.string.task_name_perfection) + "', '" + context.getString(R.string.task_perfection) + "', 'task_perfection')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (isCompleted, title, description, iconName) VALUES (0, '" + context.getString(R.string.task_name_mind) + "', '" + context.getString(R.string.task_mind) + "', 'task_mind')");



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
