package com.example.learndatastructure.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.learndatastructure.R;
import com.example.learndatastructure.model.HomeModel;
import com.example.learndatastructure.viewModel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonViewHolder> {

    private Context context;
    private List<HomeModel> lessons;
    private List<HomeModel> allLessons;  // برای ذخیره تمام درس‌ها


    public LessonsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the lesson card layout
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_lessons_items, parent, false);
        return new LessonViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(LessonViewHolder holder, int position) {
        HomeModel lesson = lessons.get(position);

        // get the language from sharedPreferences (needed for image rotation and loading lessons, ...)
        SharedPreferences prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE);
        String lang = prefs.getString("language", "English");

        // Expand/collapse section
        boolean isExpanded = lesson.isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // to rotate the image if language was farsi
        if (lang.equals("English")) {
            holder.imgArrow.setScaleX(1f);
        } else {
            holder.imgArrow.setScaleX(-1f);
        }


        // Click to expand/collapse
        holder.linearLayoutTitle.setOnClickListener(v -> {
            boolean expand = !lesson.isExpanded();
            lesson.setExpanded(expand);

            // Smooth expand/collapse layout
            TransitionManager.beginDelayedTransition((ViewGroup) holder.itemView);
            notifyItemChanged(position, lesson);

            // Rotate arrow
            float rotationAngle = expand
                    ? (lang.equals("English") ? 90f : -90f)
                    : 0f;

            holder.imgArrow.animate().rotation(rotationAngle).setDuration(300).start();
        });




        // Check if the lesson is completed
        if (lesson.isLessonCompleted()) {
            holder.iconLesson.setImageResource(R.drawable.completed);
        }
        // Update multi-quiz score if available. and change the color based on the score.
        if (lesson.getMultiQuizScore() != null) {
            holder.txtMultiQuizScore.setText(lesson.getMultiQuizScore() + " %");

            if (lesson.getMultiQuizScore() == 100) {
                int color = ContextCompat.getColor(holder.txtMultiQuizScore.getContext(), R.color.full_score);
                holder.txtMultiQuizScore.setTextColor(color);

            } else if (lesson.getMultiQuizScore() > 70) {
                int color = ContextCompat.getColor(holder.txtMultiQuizScore.getContext(), R.color.good_score);
                holder.txtMultiQuizScore.setTextColor(color);

            } else if (lesson.getMultiQuizScore() > 40) {
                int color = ContextCompat.getColor(holder.txtMultiQuizScore.getContext(), R.color.bad_score);
                holder.txtMultiQuizScore.setTextColor(color);

            } else {
                int color = ContextCompat.getColor(holder.txtMultiQuizScore.getContext(), R.color.awful_score);
                holder.txtMultiQuizScore.setTextColor(color);

            }
            holder.iconMultiQuiz.setImageResource(R.drawable.completed);  // Change icon if score is available
        }

        // does the same for multi-quiz score
        if (lesson.getCodeQuizScore() != null) {
            holder.txtCodeQuizScore.setText(lesson.getCodeQuizScore() + " %");
            if (lesson.getCodeQuizScore() == 100) {
                int color = ContextCompat.getColor(holder.txtCodeQuizScore.getContext(), R.color.full_score);
                holder.txtCodeQuizScore.setTextColor(color);
            } else if (lesson.getCodeQuizScore() > 70) {
                int color = ContextCompat.getColor(holder.txtCodeQuizScore.getContext(), R.color.good_score);
                holder.txtCodeQuizScore.setTextColor(color);
            } else if (lesson.getCodeQuizScore() > 40) {
                int color = ContextCompat.getColor(holder.txtCodeQuizScore.getContext(), R.color.bad_score);
                holder.txtCodeQuizScore.setTextColor(color);
            } else {
                int color = ContextCompat.getColor(holder.txtCodeQuizScore.getContext(), R.color.awful_score);
                holder.txtCodeQuizScore.setTextColor(color);

            }
            holder.iconCodeQuiz.setImageResource(R.drawable.completed);  // Change icon if score is available
        }


        String lesson_title, lesson_filename, multi_quiz_filename, code_quiz_filename;
        int lesson_id = lesson.getId() ;

        if (lang.equals("English")){
            lesson_title = lesson.getTitle();
            lesson_filename = lesson.getLessonFilename();
            multi_quiz_filename = lesson.getMultiQuizFilename();
            code_quiz_filename = lesson.getCodeQuizFilename();
        }else {
            lesson_title = lesson.getTitle_fa();
            lesson_filename = lesson.getLessonFilename_fa();
            multi_quiz_filename = lesson.getMultiQuizFilename_fa();
            code_quiz_filename = lesson.getCodeQuizFilename_fa();
        }
        // hide code quiz if not exists
        if(lesson.getCodeQuizFilename() == null){
            holder.cardCode.setVisibility(View.GONE);
        }


        holder.txtTitle.setText(lesson_title);

        // OnClickListeners for each card (lesson, quiz1, quiz2):
        holder.cardLesson.setOnClickListener(v -> {
            Intent intent = new Intent(context, LessonActivity.class);

            intent.putExtra("lesson_title", lesson_title);
            intent.putExtra("lesson_filename", lesson_filename);
            intent.putExtra("lesson_id", lesson_id);

            context.startActivity(intent);
        });
        holder.cardMulti.setOnClickListener(v -> {
            Intent intent = new Intent(context, MultiQuizActivity.class);

            intent.putExtra("lesson_title", lesson_title);
            intent.putExtra("multi_quiz_filename", multi_quiz_filename);
            intent.putExtra("lesson_id", lesson_id);
            context.startActivity(intent);
        });
        holder.cardCode.setOnClickListener(v -> {
            Intent intent = new Intent(context, CodeQuizActivity.class);

            intent.putExtra("lesson_title", lesson_title);
            intent.putExtra("code_quiz_filename", code_quiz_filename);
            intent.putExtra("lesson_id", lesson_id);
            context.startActivity(intent);
        });


        // gamifiction conditions
        // بخش گیمیفیکیشن
        TaskViewModel taskViewModel = new ViewModelProvider((FragmentActivity) context).get(TaskViewModel.class);

        // 1. شمارش درس‌های کامل‌شده
        int completedLessonsCount = 0;
        int fullScoreCount = 0;
        int multiQuizCompletedCount = 0;
        boolean hasCompletedCodeQuiz = false;
        boolean hasAnyMultiQuiz = false;

        for (HomeModel item : lessons) {
            if (item.isLessonCompleted()) completedLessonsCount++;
            if (item.getMultiQuizScore() != null) {
                hasAnyMultiQuiz = true;
                multiQuizCompletedCount++;
                if (item.getMultiQuizScore() == 100) fullScoreCount++;
            }
            if (item.getCodeQuizScore() != null && item.getCodeQuizScore() >= 50) {
                hasCompletedCodeQuiz = true;
            }
        }

        // 2. شروط گیمیفیکیشن
        checkTaskCondition(taskViewModel, 1, completedLessonsCount >= 1);                        // مطالعه یک درس
        checkTaskCondition(taskViewModel, 2, completedLessonsCount == lessons.size());          // تمام دروس
        checkTaskCondition(taskViewModel, 3, hasAnyMultiQuiz);                                   // اولین کوییز
        checkTaskCondition(taskViewModel, 4, fullScoreCount >= 1);                                // نمره کامل در یک کوییز
        checkTaskCondition(taskViewModel, 5, hasCompletedCodeQuiz);                              // تمرین کدنویسی
        checkTaskCondition(taskViewModel, 6, multiQuizCompletedCount == lessons.size());         // اتمام همه کوییزها
        checkTaskCondition(taskViewModel, 7, fullScoreCount >= 5);                                // در پنج درس نمره کامل
        checkTaskCondition(taskViewModel, 8, fullScoreCount >= 10);                  // تمام نمرات کامل

        // 3. نمایش انیمیشن فقط یکبار
        SharedPreferences flagPrefs = context.getSharedPreferences("gamification_flags", Context.MODE_PRIVATE);
        for (int taskId = 1; taskId <= 8; taskId++) {
            String key = "task_" + taskId + "_just_completed";
            if (flagPrefs.getBoolean(key, false)) {
                showTaskCompletedAnimation(holder.itemView, taskId);
                flagPrefs.edit().putBoolean(key, false).apply();
            }
        }




    }
    private void checkTaskCondition(TaskViewModel taskViewModel, int taskId, boolean conditionMet) {
        if (conditionMet) {
            taskViewModel.completeTaskIfNotDone(taskId, context);
        }
    }

    private void showTaskCompletedAnimation(View view, int taskId) {
        Context context = view.getContext();
        View rootView = ((Activity) context).findViewById(android.R.id.content);
        LottieAnimationView animationView = rootView.findViewById(R.id.taskSuccessAnim);

        if (animationView != null) {
            // 🔊 پخش صدا
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.task_completed);
            mediaPlayer.start();

            // در پایان صدا آزاد کن تا حافظه نشت نکنه
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release();
            });

            // ✅ انیمیشن
            animationView.setVisibility(View.VISIBLE);
            animationView.setProgress(0f);
            animationView.playAnimation();

            Toast.makeText(context,
                    context.getString(R.string.achievement_message, taskId),
                    Toast.LENGTH_SHORT).show();


            animationView.addAnimatorListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    animationView.setVisibility(View.GONE);
                }
            });
        }
    }




    @Override
    public int getItemCount() {
        return (lessons == null) ? 0 : lessons.size();
    }

    public void setLessons(List<HomeModel> lessons) {
        this.allLessons = lessons;  // ذخیره لیست کامل
        this.lessons = new ArrayList<>(lessons);  // نمایش اولیه
        notifyDataSetChanged();
    }
    public void filterByCategory(String category) {
        if (allLessons == null) return;

        if (category.equals("All")) {
            lessons = new ArrayList<>(allLessons);
        } else {
            List<HomeModel> filteredList = new ArrayList<>();
            for (HomeModel lesson : allLessons) {
                if (lesson.getCategory().equalsIgnoreCase(category)) {
                    filteredList.add(lesson);
                }
            }
            lessons = filteredList;
        }
        notifyDataSetChanged();
    }


    // ViewHolder class. which holds our views, obviously.
    public static class LessonViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtMultiQuizScore, txtCodeQuizScore;
        CardView cardLesson, cardMulti, cardCode;
        ImageView iconMultiQuiz, iconCodeQuiz, iconLesson, imgArrow;
        LinearLayout expandableLayout, linearLayoutTitle;

        public LessonViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.lesson_title);

            txtMultiQuizScore = itemView.findViewById(R.id.txt_multi_quiz_score);
            txtCodeQuizScore = itemView.findViewById(R.id.txt_code_quiz_score);

            cardLesson = itemView.findViewById(R.id.card_lesson);
            cardMulti = itemView.findViewById(R.id.card_multi_choice);
            cardCode = itemView.findViewById(R.id.card_code_quiz);

            iconMultiQuiz = itemView.findViewById(R.id.icon_multi_quiz);
            iconLesson = itemView.findViewById(R.id.icon_lesson);
            iconCodeQuiz = itemView.findViewById(R.id.icon_code_quiz);

            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            imgArrow = itemView.findViewById(R.id.imgArrow);
            linearLayoutTitle = itemView.findViewById(R.id.linearLayoutTitle);
        }
    }





}
