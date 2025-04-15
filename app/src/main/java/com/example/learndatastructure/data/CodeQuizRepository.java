package com.example.learndatastructure.data;

import android.content.Context;

import com.example.learndatastructure.model.CodeQuizModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CodeQuizRepository {
    private Context context;
    private static final String PISTON_API_URL = "https://emkc.org/api/v2/piston/execute";

    public CodeQuizRepository(Context context) {
        this.context = context;
    }

    public List<CodeQuizModel> loadCodeQuizzes(String filename) {
        try {
            InputStream is = context.getAssets().open(filename);
            InputStreamReader reader = new InputStreamReader(is);
            return new Gson().fromJson(reader, new TypeToken<List<CodeQuizModel>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void executeCode(String code, String language, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("language", language);
        jsonMap.put("version", getVersion(language));

        jsonMap.put("stdin", "");
        jsonMap.put("args", new ArrayList<>());

        Map<String, String> file = new HashMap<>();
        file.put("name", "Main." + getExtension(language));
        file.put("content", code);

        List<Map<String, String>> files = new ArrayList<>();
        files.add(file);

        jsonMap.put("files", files);

        String json = new Gson().toJson(jsonMap);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(PISTON_API_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }


    private String getExtension(String language) {
        switch (language) {
            case "python":
                return "py";
            case "java":
                return "java";
            case "c":
                return "c";
            case "cpp":
            case "c++":
                return "cpp";
            default:
                return "txt"; // fallback
        }
    }
//    private String getVersion(String language) {
//        switch (language) {
//            case "python":
//                return "3.10.0";
//            case "java":
//                return "15.0.2"; // or check Piston's supported versions
//            case "c":
//                return "10.2.0";
//            case "cpp":
//            case "c++":
//                return "10.2.0";
//            default:
//                return "*"; // fallback to latest
//        }
//    }
private String getVersion(String language) {
    return "*"; // for debugging
}


}
