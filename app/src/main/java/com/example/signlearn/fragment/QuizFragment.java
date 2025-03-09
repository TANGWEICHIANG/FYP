package com.example.signlearn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.signlearn.R;
import com.example.signlearn.quizzes.EasyQuizActivity;
import com.example.signlearn.quizzes.HardQuizActivity;

public class QuizFragment extends Fragment {

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.acitivity_main_quiz, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView easyQuizCardView = view.findViewById(R.id.easy_quiz);
        CardView hardQuizCardView = view.findViewById(R.id.hard_quiz);

        easyQuizCardView.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), EasyQuizActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });

        hardQuizCardView.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), HardQuizActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });
    }
}

