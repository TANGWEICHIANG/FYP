package com.example.signlearn.quizzes;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.signlearn.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HardQuizActivity extends AppCompatActivity {

    private static final String TAG = "HardQuizActivity";

    private ImageView questionImage;
    private Button option1, option2, option3, option4;
    private TextView timerText;
    private ProgressBar progressBar;

    private List<JSONObject> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private static final long QUIZ_TIME = 60000; // 60 seconds
    private static final int TOTAL_QUESTIONS = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_hard);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        TextView instructionsText = findViewById(R.id.instructions_text);
        questionImage = findViewById(R.id.question_image);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        timerText = findViewById(R.id.timer_text);
        progressBar = findViewById(R.id.progress_bar);

        loadQuestions();

        option1.setOnClickListener(v -> checkAnswer(option1.getText().toString()));
        option2.setOnClickListener(v -> checkAnswer(option2.getText().toString()));
        option3.setOnClickListener(v -> checkAnswer(option3.getText().toString()));
        option4.setOnClickListener(v -> checkAnswer(option4.getText().toString()));
    }

    private void loadQuestions() {
        questionList = new ArrayList<>();
        try {
            Resources res = getResources();
            InputStream inputStream = res.openRawResource(R.raw.hard_question);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                questionList.add(jsonArray.getJSONObject(i));
            }
            Log.d(TAG, "Questions loaded: " + questionList.size());

            // Shuffle the questions list to pick them randomly
            Collections.shuffle(questionList);

            startQuiz();
        } catch (Exception e) {
            Log.e(TAG, "Error loading questions: ", e);
            Toast.makeText(HardQuizActivity.this, "Error loading questions.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startQuiz() {
        timeLeftInMillis = QUIZ_TIME;
        startTimer();
        loadNextQuestion();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                endQuiz();
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerText.setText(timeFormatted);
        progressBar.setProgress((int) (timeLeftInMillis / 1000));
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex < TOTAL_QUESTIONS && currentQuestionIndex < questionList.size()) {
            JSONObject question = questionList.get(currentQuestionIndex);
            try {
                String imageUrl = question.getString("image_url");
                String correctAnswer = question.getString("correct_answer");
                JSONArray optionsJson = question.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int i = 0; i < optionsJson.length(); i++) {
                    options.add(optionsJson.getString(i));
                }

                Log.d(TAG, "Loading question: " + currentQuestionIndex);
                Log.d(TAG, "Image URL: " + imageUrl);
                Log.d(TAG, "Correct Answer: " + correctAnswer);
                Log.d(TAG, "Options: " + options);

                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Picasso.get().load(imageUrl).into(questionImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Image loaded successfully.");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "Error loading image: ", e);
                        }
                    });
                } else {
                    Log.e(TAG, "Image URL is null or empty.");
                }

                if (options.size() == 4) {
                    Collections.shuffle(options);

                    option1.setText(options.get(0));
                    option2.setText(options.get(1));
                    option3.setText(options.get(2));
                    option4.setText(options.get(3));

                    currentQuestionIndex++;
                } else {
                    Log.e(TAG, "Options do not contain 4 elements.");
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error parsing question JSON: ", e);
            }
        } else {
            endQuiz();
        }
    }

    private void checkAnswer(String selectedAnswer) {
        try {
            JSONObject question = questionList.get(currentQuestionIndex - 1);
            String correctAnswer = question.getString("correct_answer");

            if (selectedAnswer.equals(correctAnswer)) {
                score++;
                Toast.makeText(HardQuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(HardQuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
            }

            loadNextQuestion();
        } catch (JSONException e) {
            Log.e(TAG, "Error checking answer: ", e);
        }
    }

    private void endQuiz() {
        countDownTimer.cancel();
        Toast.makeText(HardQuizActivity.this, "Quiz Finished! Your score: " + score, Toast.LENGTH_LONG).show();
        // Navigate to a different screen or reset the quiz
    }
}
