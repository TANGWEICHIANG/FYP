package com.example.signlearn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.signlearn.CameraActivity;
import com.example.signlearn.LetterCombineActivity;
import com.example.signlearn.R;

public class RecognitionFragment extends Fragment {

    public RecognitionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.acitivity_main_recognition, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView cameraCardView = view.findViewById(R.id.camera_button);
        CardView combineLetterCardView = view.findViewById(R.id.combine_letter_button);

        cameraCardView.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CameraActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });

        combineLetterCardView.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LetterCombineActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });
    }

}

