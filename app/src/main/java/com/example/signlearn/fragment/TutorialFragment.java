package com.example.signlearn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.signlearn.R;
import com.example.signlearn.tutorial.AlphabetTutorialActivity;
import com.example.signlearn.tutorial.NumberTutorialActivity;

public class TutorialFragment extends Fragment {

    public TutorialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.acitivity_main_tutorial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView alphabetTutorialCardView = view.findViewById(R.id.alphabet_tutorial_button);
        CardView numberTutorialCardView = view.findViewById(R.id.number_tutorial_button);

        alphabetTutorialCardView.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AlphabetTutorialActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });

        numberTutorialCardView.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), NumberTutorialActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });
    }
}
