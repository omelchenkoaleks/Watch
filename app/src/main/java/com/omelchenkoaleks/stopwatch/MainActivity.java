package com.omelchenkoaleks.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView mTimerTextView;

    /* переменные для отслеживания состояния секундомера */
    private int seconds = 0;
    private boolean isRunning = false;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            // восстанавливаем значения переменных в случае изменения ориентации экрана
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        mTimerTextView = findViewById(R.id.timer_text_view);

        runTimer();
    }

    // сохраняем состояние текущей активности
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // сохраняем значение наших переменных
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunning);
        outState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = wasRunning;
    }

    public void onClickStartTimer(View view) {
        isRunning = true;
    }

    public void onClickStopTimer(View view) {
        isRunning = false;
    }

    public void onClickResetTimer(View view) {
        isRunning = false;
        // при сбросе таймера необходимо обновить счетчик
        seconds = 0;
    }

    // для обновления показаний секундомера
    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // пробразуем полученные данные в строковый формат
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                mTimerTextView.setText(time);

                if (isRunning) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }
}
