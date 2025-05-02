package com.example.learndatastructure.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learndatastructure.R;
import com.example.learndatastructure.model.CodeQuizModel;
import com.example.learndatastructure.model.MultiQuizModel;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private Context context;
    private List<MultiQuizModel> multiQuizList;
    private List<CodeQuizModel> codeQuizList;
    private List<String> userAnswers;
    private List<String> executionResults;
    private boolean isCodeQuiz;

    // For multi-quiz
    public ResultAdapter(Context context, List<MultiQuizModel> quizList, List<String> userAnswers) {
        this.context = context;
        this.multiQuizList = quizList;
        this.userAnswers = userAnswers;
        this.isCodeQuiz = false;
    }

    // For code quiz
    public ResultAdapter(Context context, List<CodeQuizModel> quizList, List<String> userAnswers, List<String> executionResults) {
        this.context = context;
        this.codeQuizList = quizList;
        this.userAnswers = userAnswers;
        this.executionResults = executionResults;
        this.isCodeQuiz = true;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.result_item, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        if (isCodeQuiz) {
            CodeQuizModel question = codeQuizList.get(position);
            String userCode = userAnswers.get(position);
            String apiOutput = executionResults.size() > position ? executionResults.get(position) : "";
            String expected = question.getExpectedOutput();

            holder.txtQuestion.setText((position + 1) + ". " + question.getQuestion());
            holder.txtUserAnswer.setText( userCode);
            holder.txtUserAnswer.setTextColor(Color.BLACK);

            holder.linearApiResponse.setVisibility(View.VISIBLE);
            holder.txtApiOutput.setText( apiOutput);
            holder.linearCorrectAnswer.setVisibility(View.VISIBLE);
            holder.txtCorrectAnswer.setText( expected);

        } else {
            MultiQuizModel question = multiQuizList.get(position);
            String userAns = userAnswers.get(position);
            String correctAns = question.getCorrectAnswer();

            holder.txtQuestion.setText((position + 1) + ". " + question.getQuestion());
            holder.linearApiResponse.setVisibility(View.GONE); // Hide for multi-quiz

            if (userAns == null || userAns.trim().isEmpty()) {
                holder.txtUserAnswer.setText(R.string.result_noAnswer);
                holder.txtUserAnswer.setTextColor(Color.RED);
                holder.linearCorrectAnswer.setVisibility(View.VISIBLE);
                holder.txtCorrectAnswer.setText( correctAns);
            } else if (!userAns.equals(correctAns)) {
                holder.txtUserAnswer.setText( userAns);
                holder.txtUserAnswer.setTextColor(Color.RED);
                holder.linearCorrectAnswer.setVisibility(View.VISIBLE);
                holder.txtCorrectAnswer.setText( correctAns);
            } else {
                holder.txtUserAnswer.setText( userAns);
                holder.txtUserAnswer.setTextColor(Color.GREEN);
                holder.linearCorrectAnswer.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return isCodeQuiz ? codeQuizList.size() : multiQuizList.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion, txtUserAnswer, txtCorrectAnswer, txtApiOutput;
        LinearLayout linearCorrectAnswer , linearApiResponse ;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestionResult);
            txtUserAnswer = itemView.findViewById(R.id.txtUserAnswer);
            txtCorrectAnswer = itemView.findViewById(R.id.txtCorrectAnswer);
            txtApiOutput = itemView.findViewById(R.id.txtApiOutput);

            linearCorrectAnswer = itemView.findViewById(R.id.linearCorrectAnswer);
            linearApiResponse = itemView.findViewById(R.id.linearApiResponse);
        }
    }
}

