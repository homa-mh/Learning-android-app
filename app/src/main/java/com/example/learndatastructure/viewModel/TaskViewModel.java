package com.example.learndatastructure.viewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.learndatastructure.data.TaskRepository;
import com.example.learndatastructure.model.TaskModel;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository repository;
    private MutableLiveData<List<TaskModel>> tasksLiveData;

    public TaskViewModel(Application application) {
        super(application);
        repository = new TaskRepository(application);
        tasksLiveData = new MutableLiveData<>();
        loadTasks();
    }

    private void loadTasks() {
        List<TaskModel> tasks = repository.getAllTasks();
        tasksLiveData.setValue(tasks);
    }

    public MutableLiveData<List<TaskModel>> getTasks() {
        return tasksLiveData;
    }


    public void completeTask(int taskId) {
        repository.markTaskAsCompleted(taskId);
    }

    public void refreshTasks() {
        // اینجا می‌تونی از دیتابیس بخونی یا لیست موجود رو دوباره set کنی
        List<TaskModel> currentTasks = repository.getAllTasks();  // یا هر چیزی که داری
        tasksLiveData.setValue(currentTasks);  // این باعث اجرا شدن observe توی ProfileFragment میشه
    }

    public void completeTaskIfNotDone(int taskId, Context context) {
        List<TaskModel> tasks = tasksLiveData.getValue();
        if (tasks != null) {
            for (TaskModel task : tasks) {
                if (task.getId() == taskId && !task.isCompleted()) {
                    repository.markTaskAsCompleted(taskId);
                    task.setCompleted(true);
                    tasksLiveData.setValue(tasks);

                    // ست کردن flag برای نمایش انیمیشن
                    SharedPreferences prefs = context.getSharedPreferences("gamification_flags", Context.MODE_PRIVATE);
                    prefs.edit().putBoolean("task_" + taskId + "_just_completed", true).apply();

                    break;
                }
            }
        }
    }


}
