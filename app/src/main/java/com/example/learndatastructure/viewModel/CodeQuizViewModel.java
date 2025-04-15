package com.example.learndatastructure.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.learndatastructure.data.CodeQuizRepository;
import com.example.learndatastructure.model.CodeQuizModel;
import com.example.learndatastructure.model.PistonResponse;
import com.google.gson.Gson;

import java.util.List;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CodeQuizViewModel extends AndroidViewModel {
    private CodeQuizRepository repository;
    private MutableLiveData<List<CodeQuizModel>> quizzes = new MutableLiveData<>();
    private MutableLiveData<String> executionResult = new MutableLiveData<>();

    public CodeQuizViewModel(@NonNull Application application) {
        super(application);
        repository = new CodeQuizRepository(application);
    }

    public MutableLiveData<List<CodeQuizModel>> getQuizzes() {
        return quizzes;
    }

    public MutableLiveData<String> getExecutionResult() {
        return executionResult;
    }

    public void loadQuizzes(String filename) {
        List<CodeQuizModel> quizList = repository.loadCodeQuizzes(filename);
        quizzes.setValue(quizList);
    }

    public void executeCode(String code, String language) {
        repository.executeCode(code, language, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace(); // دیباگ روی Logcat
                executionResult.postValue("Execution failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String rawResponse = response.body().string();
                    Log.d("PistonAPI", "Raw response: " + rawResponse);


                    try {
                        PistonResponse pistonResponse = new Gson().fromJson(rawResponse, PistonResponse.class);

                        if (pistonResponse != null &&
                                pistonResponse.getRun() != null &&
                                pistonResponse.getRun().getOutput() != null) {

                            executionResult.postValue(pistonResponse.getRun().getOutput());
                        } else {
                            executionResult.postValue("No output received. Please check your code or try again.");
                        }

                    } catch (Exception e) {
                        Log.e("PistonAPI", "Failed to parse JSON", e);
                        executionResult.postValue("Error parsing response. Please try again.");
                    }
                } else {
                    Log.e("PistonAPI", "Error response: " + response.message());
                    executionResult.postValue("Execution error: " + response.message());
                }
            }


        });
    }
}
