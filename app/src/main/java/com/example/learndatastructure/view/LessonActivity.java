package com.example.learndatastructure.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.webkit.WebView;
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
    private WebView webContent;

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

        webContent = findViewById(R.id.webContent);
        webContent.getSettings().setJavaScriptEnabled(false);
        webContent.setBackgroundColor(Color.TRANSPARENT);


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

            String rawHtml = pages.get(pageIndex);

            // اطمینان از اضافه شدن کلاس زبان برای highlight.js
            rawHtml = rawHtml.replaceAll("<pre><code>", "<pre class='code-block'><code class='language-python'>");

            // HTML کامل با مسیر لوکال
            String styledHtml =
                    "<html>" +
                            "<head>" +
                            "<meta charset='utf-8' />" +
                            "<link rel='stylesheet' href='file:///android_asset/highlight/monokai.min.css'>" +
                            "<style>" +
                            "body { font-family: sans-serif; line-height: 1.6; padding: 8px; }" +
                            ".text-block { direction: rtl; text-align: justify; margin-bottom: 12px; }" +
                            ".text-block.eng { direction: ltr; text-align: left; margin-bottom: 12px; }" +
                            ".code-block {"  +
                            "color: #ffffff;" +
                            "border-radius: 6px;" +
                            "display: block;" +
                            "overflow-x: auto;" +
                            "font-family: monospace;" +
                            "direction: ltr !important;" +
                            "text-align: left;" +
                            "white-space: pre-wrap;" +
                            "margin-bottom: 12px;" +
                            "}" +
                            "@media (prefers-color-scheme: dark) {\n" +
                            "    .text-block,\n" +
                            "    .text-block.eng, table {\n" +
                            "        color: white;\n" +
                            "    }\n" +
                            "}" +
                            "table { border-collapse: collapse; width: 100%; margin-bottom: 12px; text-align:center;}" +
                            "table, th, td { border: 1px solid #ccc; }" +
                            "th, td { padding: 6px; text-align: left; }" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            rawHtml +
                            "<script src='file:///android_asset/highlight/highlight.min.js'></script>" +
                            "<script>hljs.highlightAll();</script>" +
                            "</body>" +
                            "</html>";

            webContent.getSettings().setJavaScriptEnabled(true);
            webContent.loadDataWithBaseURL(null, styledHtml, "text/html", "UTF-8", null);

        } else {
            webContent.loadData("<p>No content found</p>", "text/html", "UTF-8");
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