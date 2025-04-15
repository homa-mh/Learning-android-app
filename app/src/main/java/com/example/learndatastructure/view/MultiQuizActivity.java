package com.example.learndatastructure.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.learndatastructure.R;
import com.example.learndatastructure.model.MultiQuizModel;
import com.example.learndatastructure.viewModel.MultiQuizViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultiQuizActivity extends AppCompatActivity {
    private TextView txtQuestion, txtQuestionNum;
    private RadioGroup optionsGroup;
    private RadioButton option1, option2, option3, option4;
    private Button btnNext, btnPrev, btnSubmit;

    private MultiQuizViewModel viewModel;
    private List<MultiQuizModel> quizList = new ArrayList<>();
    private List<String> userAnswers = new ArrayList<>();
    private int currentIndex = 0, lessonId;

    private String quizFilename;
    private String lessonTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_quiz);

        txtQuestion = findViewById(R.id.txtQuestion);
        txtQuestionNum = findViewById(R.id.txt_question_num);
        optionsGroup = findViewById(R.id.options_group);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        btnSubmit = findViewById(R.id.btn_submit);

        // Receive data
        quizFilename = getIntent().getStringExtra("multi_quiz_filename");
        lessonTitle = getIntent().getStringExtra("lesson_title");
        lessonId = getIntent().getIntExtra("lesson_id",0);


        viewModel = new ViewModelProvider(this).get(MultiQuizViewModel.class);
        viewModel.getQuizList().observe(this, quiz -> {
            if (quiz != null) {
                quizList = quiz;
                userAnswers = new ArrayList<>();
                for (int i = 0; i < quizList.size(); i++) userAnswers.add(null);
                loadQuestion();
            }
        });

        viewModel.loadQuiz(quizFilename);

        btnNext.setOnClickListener(v -> {
            saveUserAnswer();
            if (currentIndex < quizList.size() - 1) {
                currentIndex++;
                loadQuestion();
            }
        });

        btnPrev.setOnClickListener(v -> {
            saveUserAnswer();
            if (currentIndex > 0) {
                currentIndex--;
                loadQuestion();
            }
        });

        btnSubmit.setOnClickListener(v -> {
            saveUserAnswer();

            Intent intent = new Intent(MultiQuizActivity.this, ResultActivity.class);
            intent.putExtra("quiz_list", (Serializable) quizList);
            intent.putExtra("lesson_title", lessonTitle);
            intent.putExtra("lesson_id", lessonId);
            intent.putStringArrayListExtra("user_answers", (ArrayList<String>) userAnswers);
            intent.putExtra("is_code_quiz", false);
            startActivity(intent);
            finish();
        });
    }

    private void loadQuestion() {
        MultiQuizModel current = quizList.get(currentIndex);
        txtQuestion.setText(current.getQuestion());
        option1.setText(current.getOptions()[0]);
        option2.setText(current.getOptions()[1]);
        option3.setText(current.getOptions()[2]);
        option4.setText(current.getOptions()[3]);

        optionsGroup.clearCheck();

        // Restore previously selected answer
        String userAnswer = userAnswers.get(currentIndex);
        if (userAnswer != null) {
            if (option1.getText().equals(userAnswer)) option1.setChecked(true);
            else if (option2.getText().equals(userAnswer)) option2.setChecked(true);
            else if (option3.getText().equals(userAnswer)) option3.setChecked(true);
            else if (option4.getText().equals(userAnswer)) option4.setChecked(true);
        }

        // Update button visibility
        btnPrev.setEnabled(currentIndex != 0 );
        btnNext.setEnabled(currentIndex != quizList.size() - 1);
        btnSubmit.setVisibility(currentIndex == quizList.size() - 1 ? View.VISIBLE : View.GONE);

        txtQuestionNum.setText((currentIndex + 1) + " / " + quizList.size());
    }

    private void saveUserAnswer() {
        int checkedId = optionsGroup.getCheckedRadioButtonId();
        if (checkedId != -1) {
            RadioButton selected = findViewById(checkedId);
            userAnswers.set(currentIndex, selected.getText().toString());
        }
    }
}
