package com.example.learndatastructure.data;

import android.content.Context;
import com.example.learndatastructure.model.MultiQuizModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class MultiQuizRepository {
    private Context context;

    public MultiQuizRepository(Context context) {
        this.context = context;
    }

    public List<MultiQuizModel> loadQuizFromJson(String filename) {
        try {
            InputStream is = context.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            Type listType = new TypeToken<List<MultiQuizModel>>() {}.getType();
            return gson.fromJson(json.toString(), listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}