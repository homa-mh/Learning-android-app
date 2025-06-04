package com.example.learndatastructure.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learndatastructure.R;
import com.example.learndatastructure.data.HomeRepository;
import com.example.learndatastructure.model.CodeQuizModel;
import com.example.learndatastructure.model.MultiQuizModel;
import com.example.learndatastructure.utils.FontUtil;
import com.example.learndatastructure.utils.LocaleHelper;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtScore;
    private ResultAdapter adapter;
    private ArrayList<CodeQuizModel> codeQuizList;
    private ArrayList<MultiQuizModel> multiQuizList;
    private ArrayList<String> userAnswers;
    private ArrayList<String> executionResults;
    private String lessonTitle;
    private int lessonId;
    private boolean isCodeQuiz;
    private int score;
    private ImageButton btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Typeface typeface = FontUtil.getFontByLanguage(this);
        FontUtil.applyFontToView(this, findViewById(android.R.id.content), typeface);

        recyclerView = findViewById(R.id.recyclerResult);
        txtScore = findViewById(R.id.txtScore);
        btnExit = findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

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
            txtScore.setText(score + "%");

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
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase));
    }
}
