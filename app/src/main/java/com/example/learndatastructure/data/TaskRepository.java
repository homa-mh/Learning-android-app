package com.example.learndatastructure.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.learndatastructure.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private final TaskDatabase dbHelper;
    private final SQLiteDatabase db;

    public TaskRepository(Context context) {
        dbHelper = new TaskDatabase(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<TaskModel> getAllTasks() {
        List<TaskModel> taskList = new ArrayList<>();
        Cursor cursor = db.query(TaskDatabase.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") boolean isCompleted = cursor.getInt(cursor.getColumnIndex("isCompleted")) == 1;
            @SuppressLint("Range") String titleEn = cursor.getString(cursor.getColumnIndex("title_en"));
            @SuppressLint("Range") String titleFa = cursor.getString(cursor.getColumnIndex("title_fa"));
            @SuppressLint("Range") String icon = cursor.getString(cursor.getColumnIndex("iconName"));

            taskList.add(new TaskModel(id, isCompleted, titleEn, titleFa, icon));
        }

        cursor.close();
        return taskList;
    }

    public void markTaskAsCompleted(int taskId) {
        String query = "UPDATE " + TaskDatabase.TABLE_NAME + " SET isCompleted = 1 WHERE id = ?";
        db.execSQL(query, new Object[]{taskId});
    }
}
