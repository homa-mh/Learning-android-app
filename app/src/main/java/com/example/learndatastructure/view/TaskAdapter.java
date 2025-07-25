package com.example.learndatastructure.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.learndatastructure.R;
import com.example.learndatastructure.model.TaskModel;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskModel> taskList;
    private Context context;

    public TaskAdapter(Context context, List<TaskModel> tasks) {
        this.context = context;
        this.taskList = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel task = taskList.get(position);

        SharedPreferences prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE);
        String lang = prefs.getString("language", "English");
        if (lang.equals("Persian")) {
            holder.title.setText(task.getTitleFa());
        } else {
            holder.title.setText(task.getTitleEn());
        }

        holder.icon.setImageResource(
                context.getResources().getIdentifier(task.getIconName(), "drawable", context.getPackageName())
        );

//        if (!task.isCompleted()) {
//            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.task_undone), PorterDuff.Mode.SRC_OVER);
//        }

        if (!task.isCompleted()) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0); // This makes it grayscale

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.icon.setColorFilter(filter);

            // Apply alpha from color.task_undone
            int alpha = ContextCompat.getColor(context, R.color.task_undone) >>> 24; // extract alpha
            holder.icon.setImageAlpha(alpha);
        }

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView icon;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            description = itemView.findViewById(R.id.textDesc);
            icon = itemView.findViewById(R.id.imageIcon);
        }
    }
}






