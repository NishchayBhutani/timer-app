package com.nishchay.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekbar;
    Button goButton;

    boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        timerTextView.setText("0:30");
        timerSeekbar.setProgress(30);
        timerSeekbar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("go");
        counterIsActive = false;
    }

    public void buttonClicked(View view) {
        if (counterIsActive) {
            resetTimer();
        } else {
            counterIsActive = true;
            timerSeekbar.setEnabled(false);
            goButton.setText("Stop");
            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mplayer.start();
                    timerTextView.setText("0:30");
                    timerSeekbar.setProgress(30);
                    timerSeekbar.setEnabled(true);
                    countDownTimer.cancel();
                    goButton.setText("go");
                    counterIsActive = false;
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if(seconds < 10) {
            secondString = "0"+seconds;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekbar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        goButton = findViewById(R.id.goButton);

        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        }
    }