package com.example.learndatastructure.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learndatastructure.R;

import com.example.learndatastructure.data.LessonRepository;
import com.example.learndatastructure.utils.FontUtil;
import com.example.learndatastructure.utils.LocaleHelper;
import com.example.learndatastructure.viewModel.HomeViewModel;
import com.example.learndatastructure.viewModel.LessonViewModel;

import java.util.List;

public class LessonActivity extends AppCompatActivity {
    private LessonViewModel viewModel;
    private TextView txtTitle, txtContent, txtPageNum;
    private Button btnPrev, btnNext;
    private int currentPage = 0;
    private List<String> pages;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        Typeface typeface = FontUtil.getFontByLanguage(this);
        FontUtil.applyFontToView(this, findViewById(android.R.id.content), typeface);


        txtTitle = findViewById(R.id.txt_title);
        txtContent = findViewById(R.id.txtContent);
        txtPageNum = findViewById(R.id.txt_page_num);
        btnPrev = findViewById(R.id.btn_prev);
        btnNext = findViewById(R.id.btn_next);

        String title = getIntent().getStringExtra("lesson_title");
        int lessonId = getIntent().getIntExtra("lesson_id",0);
        String fileName = getIntent().getStringExtra("lesson_filename");
        Log.d("LessonActivity", "Lesson filename: " + fileName);

        LessonRepository repository = new LessonRepository(this);
        viewModel = new ViewModelProvider(this).get(LessonViewModel.class);
        viewModel.init(repository, fileName);

        viewModel.getLesson().observe(this, lesson -> {
            if (lesson != null) {
                txtTitle.setText(lesson.getTitle());
                pages = lesson.getPages();
                txtPageNum.setText((currentPage+1) + "/" + pages.size());

                if (pages != null && !pages.isEmpty()) {
                    Log.d("LessonActivity", "First page content: " + pages.get(0));
                    showPage(currentPage);
                } else {
                    Log.e("LessonActivity", "Lesson has no pages");
                    txtContent.setText("No pages found in lesson file.");
                }

            } else {
                Log.e("LessonActivity", "Lesson is null");
                txtTitle.setText("Error");
                txtContent.setText("Lesson could not be loaded.");
            }
        });


        btnPrev.setOnClickListener(v -> {
            if (currentPage > 0) {
                currentPage--;
                showPage(currentPage);
                // Update page number text. Optionally add 1 for human-friendly page numbering.
                txtPageNum.setText((currentPage + 1) + "/" + pages.size());
                // Since we moved back, revert the btnNext text to "Next"
                btnNext.setText(">");
            }
        });

        btnNext.setOnClickListener(v -> {
            if (pages == null) return; // Check if pages is initialized

            // If we are not on the last page yet:
            if (currentPage < pages.size() - 1) {
                currentPage++;
                showPage(currentPage);
                txtPageNum.setText((currentPage + 1) + "/" + pages.size());
                // If we've arrived at the last page, change the button text to "Done"
                if (currentPage == pages.size() - 1) {
                    btnNext.setText(R.string.lessons_done);
                }
            }
            // Else, if we're already on the last page:
            else if (currentPage == pages.size() - 1) {
                // Finalize progress and navigate to HomeActivity
                updateLessonProgress(lessonId, true);
                Intent i = new Intent(LessonActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("tab", "home");
                startActivity(i);
                finish();
            }
        });


    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase));
    }



    private void showPage(int pageIndex) {
        if (pages != null && pageIndex < pages.size()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                txtContent.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            }
            txtContent.setText(Html.fromHtml(pages.get(pageIndex)));
        } else {
            txtContent.setText("No content found");
        }
    }

    private void updateLessonProgress(int lessonId, boolean lessonCompleted) {
        HomeViewModel lessonViewModel;
        lessonViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(HomeViewModel.class);
        lessonViewModel.updateLessonProgress(lessonId, lessonCompleted);
    }





}