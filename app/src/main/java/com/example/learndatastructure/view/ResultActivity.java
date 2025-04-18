package com.example.learndatastructure.view;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learndatastructure.R;
import com.example.learndatastructure.data.HomeRepository;
import com.example.learndatastructure.model.CodeQuizModel;
import com.example.learndatastructure.model.MultiQuizModel;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtScore;
    private ResultAdapter adapter;
    private ArrayList<CodeQuizModel> codeQuizList;
    private ArrayList<MultiQuizModel> multiQuizList;
    private ArrayList<String> userAnswers;
    ArrayList<String> executionResults;
    private String lessonTitle;
    private int lessonId;
    private boolean isCodeQuiz;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        recyclerView = findViewById(R.id.recyclerResult);
        txtScore = findViewById(R.id.txtScore);

        userAnswers = getIntent().getStringArrayListExtra("user_answers");
        executionResults = getIntent().getStringArrayListExtra("execution_results");
        lessonId = getIntent().getIntExtra("lesson_id", 0);
        lessonTitle = getIntent().getStringExtra("lesson_title");
        isCodeQuiz = getIntent().getBooleanExtra("is_code_quiz", false);

        if (userAnswers == null) {
            userAnswers = new ArrayList<>();
        }

        int correctCount = 0;

        if (isCodeQuiz) {
            codeQuizList = (ArrayList<CodeQuizModel>) getIntent().getSerializableExtra("quiz_list");

            for (int i = 0; i < codeQuizList.size(); i++) {
                if (i < userAnswers.size() && userAnswers.get(i) != null) {
                    String expected = codeQuizList.get(i).getExpectedOutput().trim();
                    String actual = executionResults.get(i).trim();
                    if (expected.equalsIgnoreCase(actual)) {
                        correctCount++;
                    }
                }
            }

            score = (correctCount * 100) / codeQuizList.size();
            txtScore.setText("Score: " + score + "%");

            // 🔥 NEW: Use updated adapter constructor for code quiz
            adapter = new ResultAdapter(this, codeQuizList, userAnswers, executionResults);
        } else {
            multiQuizList = (ArrayList<MultiQuizModel>) getIntent().getSerializableExtra("quiz_list");

            for (int i = 0; i < multiQuizList.size(); i++) {
                if (i < userAnswers.size() && userAnswers.get(i) != null) {
                    if (multiQuizList.get(i).getCorrectAnswer().equals(userAnswers.get(i))) {
                        correctCount++;
                    }
                }
            }

            score = (correctCount * 100) / multiQuizList.size();
            txtScore.setText("Score: " + score + "%");

            adapter = new ResultAdapter(this, multiQuizList, userAnswers); // original
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        // Update progress
        HomeRepository repo = new HomeRepository(this);
        if (isCodeQuiz) {
            repo.updateCodeQuizProgress(lessonId, score);
        } else {
            repo.updateMultiQuizProgress(lessonId, score);
        }
    }
}
