package com.example.signlearn.tutorial;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import java.io.IOException;
import com.example.signlearn.R;

public class GifViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_viewer);

        // Set up the GifImageView
        GifImageView gifImageView = findViewById(R.id.gifImageView);
        String letter = getIntent().getStringExtra("LETTER");
        try {
            GifDrawable gifFromAssets = new GifDrawable(getAssets(), "gifs/tuto_" + letter.toLowerCase() + ".gif");
            gifImageView.setImageDrawable(gifFromAssets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set up the root layout click listener to finish the activity
        RelativeLayout rootLayout = findViewById(R.id.root_layout);
        rootLayout.setOnClickListener(v -> finish());

        // Ensure the GifImageView doesn't consume the click event
        gifImageView.setOnTouchListener((v, event) -> true);
    }
}
