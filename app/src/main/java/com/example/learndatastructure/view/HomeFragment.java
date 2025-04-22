package com.example.learndatastructure.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learndatastructure.R;
import com.example.learndatastructure.model.HomeModel;
import com.example.learndatastructure.utils.FontUtil;
import com.example.learndatastructure.viewModel.HomeViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private LessonsAdapter lessonAdapter;
    private HomeViewModel lessonViewModel;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // گرفتن کانتکست ایمن
        Context context = requireContext();  // یا getContext() اگر مطمئنی نال نیست
        // گرفتن فونت با توجه به زبان
        Typeface typeface = FontUtil.getFontByLanguage(context);
        // اعمال فونت به کل ویوی فرگمنت
        FontUtil.applyFontToView(context, view, typeface);

        recyclerView = view.findViewById(R.id.recycler_lessons);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        lessonAdapter = new LessonsAdapter(getContext());
        recyclerView.setAdapter(lessonAdapter);

        lessonViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        lessonViewModel.getLessons().observe(getViewLifecycleOwner(), new Observer<List<HomeModel>>() {
            @Override
            public void onChanged(List<HomeModel> lessons) {
                if (lessons != null) {
                    lessonAdapter.setLessons(lessons);
                } else {
                    Toast.makeText(getContext(), "No lessons available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        lessonViewModel.refreshLessons();
    }
}
