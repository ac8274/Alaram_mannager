package com.example.alaram_mannager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alaram_mannager.recivers.set_up_reminder;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private TextView textView;
    private Button Reminder_button;
    private Button Exit_button;
    private AlertDialog.Builder adb;
    private AlertDialog ad;
    private Calendar currentTime;
    private int ALARM_REQUEST_CODE;
    private int SET_UP_ALARM_REQUEST_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ALARM_REQUEST_CODE=0;

        textView = findViewById(R.id.textView);
        Reminder_button = findViewById(R.id.Reminder_button);
        Exit_button = findViewById(R.id.Exit_button);
        setInitialSnooze();
    }

    public void setInitialSnooze()
    {
        Intent intent = new Intent(this, set_up_reminder.class);
        alarmIntent = PendingIntent.getBroadcast(this,
                SET_UP_ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 60 * 60 * 1000,
                AlarmManager.INTERVAL_HOUR, alarmIntent);
    }

    public void Set_Alarm(View view) {

    }

    public void Exit(View view) {
        finish();
    }
}