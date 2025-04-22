package com.example.learndatastructure.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learndatastructure.R;
import com.example.learndatastructure.model.HomeModel;

import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonViewHolder> {

    private Context context;
    private List<HomeModel> lessons;

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

        // Expand/collapse section
        boolean isExpanded = lesson.isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);


        // Click to expand/collapse
        holder.linearLayoutTitle.setOnClickListener(v -> {
            boolean expand = !lesson.isExpanded();
            lesson.setExpanded(expand);

            // Smooth expand/collapse layout
            TransitionManager.beginDelayedTransition((ViewGroup) holder.itemView);
            notifyItemChanged(position, lesson);

            // Rotate arrow
            float rotationAngle = expand ? 90f : 0f;
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

        // خواندن زبان از SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE);
        String lang = prefs.getString("language", "English");

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

        holder.txtTitle.setText(lesson_title);

//      OnClickListeners for each card (lesson, quiz1, quiz2):
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




    }

    @Override
    public int getItemCount() {
        return (lessons == null) ? 0 : lessons.size();
    }

    public void setLessons(List<HomeModel> lessons) {
        this.lessons = lessons;
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
