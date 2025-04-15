package com.example.learndatastructure.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.learndatastructure.data.LessonRepository;
import com.example.learndatastructure.model.LessonModel;

public class LessonViewModel extends ViewModel {
    private MutableLiveData<LessonModel> lessonData = new MutableLiveData<>();
    private LessonRepository repository;

    public void init(LessonRepository repository, String fileName) {
        this.repository = repository;
        LessonModel lesson = repository.getLessonFromAssets(fileName);
        lessonData.setValue(lesson);

    }

    public LiveData<LessonModel> getLesson() {
        return lessonData;
    }
}
