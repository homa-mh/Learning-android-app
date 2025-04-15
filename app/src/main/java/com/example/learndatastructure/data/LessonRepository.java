package com.example.learndatastructure.data;

import android.content.Context;
import android.util.Log;

import com.example.learndatastructure.model.LessonModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LessonRepository {
    private Context context;

    public LessonRepository(Context context) {
        this.context = context;
    }

    public LessonModel getLessonFromAssets(String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");

            JSONArray pagesArray = jsonObject.getJSONArray("pages");
            List<String> pages = new ArrayList<>();
            for (int i = 0; i < pagesArray.length(); i++) {
                JSONObject pageObj = pagesArray.getJSONObject(i);
                String content = pageObj.getString("content");
                pages.add(content);
            }

            return new LessonModel(id, title, pages);

        } catch (Exception e) {
            Log.e("LessonRepository", "Error loading lesson from file: " + fileName, e);
            return null;
        }
    }
}
