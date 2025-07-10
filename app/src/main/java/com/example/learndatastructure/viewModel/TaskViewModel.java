package com.example.learndatastructure.viewModel;

import android.app.Application;

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
}
