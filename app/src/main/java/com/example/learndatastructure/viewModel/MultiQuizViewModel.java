package com.example.learndatastructure.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.learndatastructure.data.MultiQuizRepository;
import com.example.learndatastructure.model.MultiQuizModel;
import java.util.List;

public class MultiQuizViewModel extends AndroidViewModel {
    private MultiQuizRepository repository;
    private MutableLiveData<List<MultiQuizModel>> quizList;

    public MultiQuizViewModel(@NonNull Application application) {
        super(application);
        repository = new MultiQuizRepository(application);
        quizList = new MutableLiveData<>();
    }

    public LiveData<List<MultiQuizModel>> getQuizList() { return quizList; }

    public void loadQuiz(String filename) {
        quizList.setValue(repository.loadQuizFromJson(filename));
    }
}