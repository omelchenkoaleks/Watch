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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerTextView = findViewById(R.id.timer_text_view);

        runTimer();
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
