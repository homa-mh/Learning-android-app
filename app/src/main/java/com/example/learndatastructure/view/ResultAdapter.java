package com.example.learndatastructure.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learndatastructure.R;
import com.example.learndatastructure.model.MultiQuizModel;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private Context context;
    private List<MultiQuizModel> quizList;
    private List<String> userAnswers;

    public ResultAdapter(Context context, List<MultiQuizModel> quizList, List<String> userAnswers) {
        this.context = context;
        this.quizList = quizList;
        this.userAnswers = userAnswers;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.result_item, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        MultiQuizModel question = quizList.get(position);
        String userAns = userAnswers.get(position);
        String correctAns = question.getCorrectAnswer();

        holder.txtQuestion.setText((position + 1) + ". " + question.getQuestion());

        // Show "No answer" if userAns is null
        if (userAns == null || userAns.trim().isEmpty()) {
            holder.txtUserAnswer.setText("Your answer: No answer");
            holder.txtUserAnswer.setTextColor(Color.RED);
            holder.txtCorrectAnswer.setVisibility(View.VISIBLE);
            holder.txtCorrectAnswer.setText("Correct answer: " + correctAns);
        } else if (!userAns.equals(correctAns)) {
            holder.txtUserAnswer.setText("Your answer: " + userAns);
            holder.txtUserAnswer.setTextColor(Color.RED);
            holder.txtCorrectAnswer.setVisibility(View.VISIBLE);
            holder.txtCorrectAnswer.setText("Correct answer: " + correctAns);
        } else {
            holder.txtUserAnswer.setText("Your answer: " + userAns);
            holder.txtUserAnswer.setTextColor(Color.GREEN);
            holder.txtCorrectAnswer.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion, txtUserAnswer, txtCorrectAnswer;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestionResult);
            txtUserAnswer = itemView.findViewById(R.id.txtUserAnswer);
            txtCorrectAnswer = itemView.findViewById(R.id.txtCorrectAnswer);
        }
    }
}
