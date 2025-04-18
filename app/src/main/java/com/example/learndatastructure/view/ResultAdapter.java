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
            holder.txtUserAnswer.setText("Your code:\n" + userCode);
            holder.txtUserAnswer.setTextColor(Color.BLACK);

            holder.txtApiOutput.setVisibility(View.VISIBLE);
            holder.txtApiOutput.setText("API Output:\n" + apiOutput);
            holder.txtCorrectAnswer.setVisibility(View.VISIBLE);
            holder.txtCorrectAnswer.setText("Expected Output:\n" + expected);

        } else {
            MultiQuizModel question = multiQuizList.get(position);
            String userAns = userAnswers.get(position);
            String correctAns = question.getCorrectAnswer();

            holder.txtQuestion.setText((position + 1) + ". " + question.getQuestion());
            holder.txtApiOutput.setVisibility(View.GONE); // Hide for multi-quiz

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
    }

    @Override
    public int getItemCount() {
        return isCodeQuiz ? codeQuizList.size() : multiQuizList.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView txtQuestion, txtUserAnswer, txtCorrectAnswer, txtApiOutput;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestion = itemView.findViewById(R.id.txtQuestionResult);
            txtUserAnswer = itemView.findViewById(R.id.txtUserAnswer);
            txtCorrectAnswer = itemView.findViewById(R.id.txtCorrectAnswer);
            txtApiOutput = itemView.findViewById(R.id.txtApiOutput);
        }
    }
}

