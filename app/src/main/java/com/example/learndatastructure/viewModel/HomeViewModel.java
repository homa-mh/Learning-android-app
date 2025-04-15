package com.example.learndatastructure.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.learndatastructure.data.HomeRepository;
import com.example.learndatastructure.model.HomeModel;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private HomeRepository homeRepository;
    private MutableLiveData<List<HomeModel>> lessonsLiveData;

    public HomeViewModel(Application application) {
        super(application);  // Pass application to the superclass
        homeRepository = new HomeRepository(application);  // Pass application to repository
        lessonsLiveData = new MutableLiveData<>();
    }

    // Get lessons and update LiveData
    public LiveData<List<HomeModel>> getLessons() {
        lessonsLiveData.setValue(homeRepository.getLessons());
        return lessonsLiveData;
    }

    // Method to update the lesson progress
    public void updateLessonProgress(int lessonId, boolean lessonCompleted) {
        homeRepository.updateLessonProgress(lessonId, lessonCompleted);
        lessonsLiveData.setValue(homeRepository.getLessons());  // Refresh LiveData after update
    }
    public void updateMultiQuizProgress(int lessonId, Integer multiQuizScore) {
        homeRepository.updateMultiQuizProgress(lessonId, multiQuizScore);
        lessonsLiveData.setValue(homeRepository.getLessons());  // Refresh LiveData after update
    }
    public void updateCodeQuizProgress(int lessonId, Integer codeQuizScore) {
        homeRepository.updateCodeQuizProgress(lessonId, codeQuizScore);
        lessonsLiveData.setValue(homeRepository.getLessons());  // Refresh LiveData after update
    }
    public void refreshLessons() {
        lessonsLiveData.setValue(homeRepository.getLessons());
    }


}
