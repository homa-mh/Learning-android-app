package com.example.learndatastructure.data;

import android.content.Context;
import android.util.Log;

import com.example.learndatastructure.model.HomeModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeRepository {
    private Context context;

    public HomeRepository(Context context) {
        this.context = context;
    }

    // Method to load lessons from the assets
    public List<HomeModel> getLessons() {
        List<HomeModel> lessons = new ArrayList<>();
        try {
            InputStream is;

            // Check if lessons.json exists in internal storage
            java.io.File file = new java.io.File(context.getFilesDir(), "lessons.json");
            if (file.exists()) {
                is = context.openFileInput("lessons.json"); // Read from internal storage
            } else {
                is = context.getAssets().open("lessons.json"); // Fall back to assets on first run
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();

            // Convert the JSON string to a list of HomeModel objects using Gson
            Gson gson = new Gson();
            Type listType = new TypeToken<List<HomeModel>>(){}.getType();
            lessons = gson.fromJson(json.toString(), listType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lessons;
    }


    // Method to update a lesson progress and save it to lessons.json
    public void updateLessonProgress(int lessonId, boolean lessonCompleted) {
        List<HomeModel> lessons = getLessons();  // Load the current lessons list
        for (HomeModel lesson : lessons) {
            if (lesson.getId() == lessonId) {
                lesson.setLessonCompleted(lessonCompleted);
                break;
            }
        }
        saveLessonsToFile(lessons);
    }
    public void updateMultiQuizProgress(int lessonId, Integer multiQuizScore) {
        List<HomeModel> lessons = getLessons();  // Load the current lessons list
        for (HomeModel lesson : lessons) {
            if (lesson.getId() == lessonId) {
                lesson.setMultiQuizScore(multiQuizScore);
                break;
            }
        }
        saveLessonsToFile(lessons);
    }
    public void updateCodeQuizProgress(int lessonId, Integer codeQuizScore) {
        List<HomeModel> lessons = getLessons();  // Load the current lessons list
        for (HomeModel lesson : lessons) {
            if (lesson.getId() == lessonId) {
                lesson.setCodeQuizScore(codeQuizScore);
                break;
            }
        }
        saveLessonsToFile(lessons);
    }

    // Method to save updated lessons list back to the file
    private void saveLessonsToFile(List<HomeModel> lessons) {
        try {
            // Convert the updated lessons list back to JSON string using Gson
            Gson gson = new Gson();
            String json = gson.toJson(lessons);

            // Save the JSON string to a file in internal storage
            FileOutputStream fos = context.openFileOutput("lessons.json", Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("HomeRepository", "Error saving lessons.json", e);
        }
    }
}
