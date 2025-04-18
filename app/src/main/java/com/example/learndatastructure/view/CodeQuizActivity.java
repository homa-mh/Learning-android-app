package com.example.learndatastructure.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.learndatastructure.R;
import com.example.learndatastructure.model.CodeQuizModel;
import com.example.learndatastructure.viewModel.CodeQuizViewModel;

import java.util.ArrayList;
import java.util.List;

public class CodeQuizActivity extends AppCompatActivity {

    private CodeQuizViewModel viewModel;
    private TextView txtQuestion, txtQuestionNum;
    private EditText edtCode;
    private Button btnCheck, btnNext;
    private ImageButton btnHint;
    private int currentIndex = 0;
    private List<CodeQuizModel> quizList;
    private List<Boolean> answerStatusList;
    private List<String> userAnswers = new ArrayList<>();
    private List<String> executionResults = new ArrayList<>();
    private Switch switchTheme;
    private boolean isAnswerChecked = false;
    private int lessonId;
    private String lessonTitle;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_quiz);

        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No Internet connection!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtQuestion = findViewById(R.id.txtQuestion);
        edtCode = findViewById(R.id.edtCode);
        txtQuestionNum = findViewById(R.id.txt_question_num);
        btnCheck = findViewById(R.id.btn_execute);
        btnNext = findViewById(R.id.btn_done); // btn_done acts as "Next"
        btnHint = findViewById(R.id.btn_hint);
        switchTheme = findViewById(R.id.switchTheme);

        viewModel = new ViewModelProvider(this).get(CodeQuizViewModel.class);

        String filename = getIntent().getStringExtra("code_quiz_filename");
        lessonTitle = getIntent().getStringExtra("lesson_title");
        lessonId = getIntent().getIntExtra("lesson_id", 0);

        viewModel.loadQuizzes(filename);

        viewModel.getQuizzes().observe(this, quizzes -> {
            if (quizzes != null && !quizzes.isEmpty()) {
                quizList = quizzes;
                answerStatusList = new ArrayList<>();
                for (int i = 0; i < quizzes.size(); i++) {
                    answerStatusList.add(false);
                }
                displayQuestion();
            }
        });

        viewModel.getExecutionResult().observe(this, result -> {
            Toast.makeText(this, "Output:\n" + result, Toast.LENGTH_LONG).show();

            String expected = quizList.get(currentIndex).getExpectedOutput().trim();
            String actual = result.trim();

            if (actual.equalsIgnoreCase(expected)) {
                edtCode.setBackgroundResource(R.drawable.code_editor_correct);
                answerStatusList.set(currentIndex, true);
            } else {
                edtCode.setBackgroundResource(R.drawable.code_editor_wrong);
            }

            isAnswerChecked = true;

            // Update execution result list
            if (executionResults.size() > currentIndex) {
                executionResults.set(currentIndex, result);
            } else {
                executionResults.add(result);
            }

            if (btnNext.getVisibility() != View.VISIBLE)
                showNextButton();
        });



        btnCheck.setOnClickListener(v -> {
            if (!isNetworkAvailable(this)) {
                Toast.makeText(this, "Please connect to the internet to submit", Toast.LENGTH_SHORT).show();
                return;
            }

            String userCode = edtCode.getText().toString().trim();
            if (userCode.isEmpty()) {
                Toast.makeText(this, "Write some code first", Toast.LENGTH_SHORT).show();
                return;
            }

            CodeQuizModel currentQuiz = quizList.get(currentIndex);
            viewModel.executeCode(userCode, currentQuiz.getLanguage());

            // Save user's code to userAnswers
            if (userAnswers.size() > currentIndex) {
                userAnswers.set(currentIndex, userCode);
            } else {
                userAnswers.add(userCode);
            }
        });

        btnNext.setOnClickListener(v -> {
            if (!isAnswerChecked) {
                Toast.makeText(this, "Please check your answer first", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentIndex < quizList.size() - 1) {
                currentIndex++;
                displayQuestion();
            } else {
                Intent intent = new Intent(CodeQuizActivity.this, ResultActivity.class);
                intent.putExtra("quiz_list", new ArrayList<CodeQuizModel>(quizList)); // Serializable
                intent.putExtra("user_answers", new ArrayList<>(userAnswers));
                intent.putExtra("execution_results", new ArrayList<>(executionResults));
                intent.putExtra("lesson_id", lessonId);
                intent.putExtra("lesson_title", lessonTitle);
                intent.putExtra("is_code_quiz", true);
                startActivity(intent);
                finish();
            }

        });


        btnHint.setOnClickListener(v -> {
            String hint = quizList.get(currentIndex).getHint();
            Toast toast = Toast.makeText(getApplicationContext(), hint != null ? hint : "No hint available.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
            toast.show();
        });

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edtCode.setBackgroundResource(R.drawable.code_editor_dark);
                edtCode.setTextColor(Color.parseColor("#DCDCDC"));
                edtCode.setHintTextColor(Color.parseColor("#888888"));
            } else {
                edtCode.setBackgroundResource(R.drawable.code_editor_light);
                edtCode.setTextColor(Color.parseColor("#000000"));
                edtCode.setHintTextColor(Color.parseColor("#999999"));
            }
        });
    }

    private void displayQuestion() {
        if (quizList != null && !quizList.isEmpty()) {
            CodeQuizModel quiz = quizList.get(currentIndex);

            // Fade out views
            txtQuestion.animate().alpha(0f).setDuration(150).withEndAction(() -> {
                // Update content when fade out is done
                txtQuestion.setText(quiz.getQuestion());
                edtCode.setText("");
                edtCode.setBackgroundResource(R.drawable.code_editor_dark);
                txtQuestionNum.setText((currentIndex + 1) + " / " + quizList.size());

                isAnswerChecked = false;
                hideNextButton();

                if (currentIndex == quizList.size() - 1) {
                    btnNext.setText("Done");
                } else {
                    btnNext.setText("Next");
                }

                // Fade in views
                txtQuestion.animate().alpha(1f).setDuration(200).start();

            }).start();
        }
    }



    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    private void showNextButton() {
        btnNext.setVisibility(View.VISIBLE);
        btnNext.setScaleX(0f);
        btnNext.setScaleY(0f);

        btnNext.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .setInterpolator(new OvershootInterpolator()) // Optional bounce feel
                .start();
    }
    private void hideNextButton() {
        btnNext.animate()
                .scaleX(0f)
                .scaleY(0f)
                .setDuration(200)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(() -> btnNext.setVisibility(View.INVISIBLE))
                .start();
    }


}
