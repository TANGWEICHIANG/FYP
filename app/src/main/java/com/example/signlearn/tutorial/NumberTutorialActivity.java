package com.example.signlearn.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.signlearn.R;

public class NumberTutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_tutorial);

        // Set up the custom back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Set click listeners for each button
        setButtonClickListener(R.id.button_0, "0");
        setButtonClickListener(R.id.button_1, "1");
        setButtonClickListener(R.id.button_2, "2");
        setButtonClickListener(R.id.button_3, "3");
        setButtonClickListener(R.id.button_4, "4");
        setButtonClickListener(R.id.button_5, "5");
        setButtonClickListener(R.id.button_6, "6");
        setButtonClickListener(R.id.button_7, "7");
        setButtonClickListener(R.id.button_8, "8");
        setButtonClickListener(R.id.button_9, "9");
    }

    private void setButtonClickListener(int buttonId, String number) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(NumberTutorialActivity.this, GifViewerActivity.class);
            intent.putExtra("LETTER", number);
            startActivity(intent);
        });
    }
}
