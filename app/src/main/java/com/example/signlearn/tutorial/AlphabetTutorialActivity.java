package com.example.signlearn.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import com.example.signlearn.R;

public class AlphabetTutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet_tutorial);

        // Set up the custom back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Set click listeners for each button
        setButtonClickListener(R.id.button_a, "A");
        setButtonClickListener(R.id.button_b, "B");
        setButtonClickListener(R.id.button_c, "C");
        setButtonClickListener(R.id.button_d, "D");
        setButtonClickListener(R.id.button_e, "E");
        setButtonClickListener(R.id.button_f, "F");
        setButtonClickListener(R.id.button_g, "G");
        setButtonClickListener(R.id.button_h, "H");
        setButtonClickListener(R.id.button_i, "I");
        setButtonClickListener(R.id.button_j, "J");
        setButtonClickListener(R.id.button_k, "K");
        setButtonClickListener(R.id.button_l, "L");
        setButtonClickListener(R.id.button_m, "M");
        setButtonClickListener(R.id.button_n, "N");
        setButtonClickListener(R.id.button_o, "O");
        setButtonClickListener(R.id.button_p, "P");
        setButtonClickListener(R.id.button_q, "Q");
        setButtonClickListener(R.id.button_r, "R");
        setButtonClickListener(R.id.button_s, "S");
        setButtonClickListener(R.id.button_t, "T");
        setButtonClickListener(R.id.button_u, "U");
        setButtonClickListener(R.id.button_v, "V");
        setButtonClickListener(R.id.button_w, "W");
        setButtonClickListener(R.id.button_x, "X");
        setButtonClickListener(R.id.button_y, "Y");
        setButtonClickListener(R.id.button_z, "Z");
    }

    private void setButtonClickListener(int buttonId, String letter) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(AlphabetTutorialActivity.this, GifViewerActivity.class);
            intent.putExtra("LETTER", letter);
            startActivity(intent);
        });
    }
}
