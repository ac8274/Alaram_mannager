package com.example.alaram_mannager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alaram_mannager.recivers.ExactAlarm;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private TextView textView;
    private Button Reminder_button;
    private Context context = this;
    private Button Exit_button;
    private AlertDialog.Builder adb;
    private AlertDialog ad;
    private Calendar currentTime;
    private int ALARM_REQUEST_CODE;
    private String FILENAME = "Alarm_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Reminder_button = findViewById(R.id.Reminder_button);
        Exit_button = findViewById(R.id.Exit_button);

        if(Check_SharedPrefrences())
        {
            SharedPreferences settings=getSharedPreferences("Alarm_state",MODE_PRIVATE);
            ALARM_REQUEST_CODE=settings.getInt("AlarmNum",0);
            if(ALARM_REQUEST_CODE==0)
            {
                setInitialSnooze();
            }
            else
            {

            }
        }
        else
        {
            ALARM_REQUEST_CODE=0;
            SharedPreferences settings=getSharedPreferences("Alarm_state",MODE_PRIVATE);
            SharedPreferences.Editor editor=settings.edit();
            editor.putInt("AlarmNum",ALARM_REQUEST_CODE);
            setInitialSnooze();
        }
    }

    public boolean Check_SharedPrefrences()
    {
        File f = new File("/data/data/" + getPackageName() +  "/shared_prefs/" + FILENAME);
        return f.exists();
    }

    public void setInitialSnooze()
    {
        Intent in = new Intent(this, ExactAlarm.class);
        in.putExtra("Alarm_Option",0);
        alarmIntent = PendingIntent.getBroadcast(this,
                ALARM_REQUEST_CODE, in, PendingIntent.FLAG_IMMUTABLE);

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 3600 * 1000,
                AlarmManager.INTERVAL_HOUR, alarmIntent);
    }

    public void open_Timepickerdialog()
    {

        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Choose time");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                Toast.makeText(context, "Can't set alarm to the past or the present!", Toast.LENGTH_LONG).show();
            }
            else {
                setExactAlarm(calSet);
            }
        }
    };

    private void setExactAlarm(Calendar calSet) {

        Intent intent = new Intent(this, ExactAlarm.class);
        alarmIntent = PendingIntent.getBroadcast(this,
                ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(alarmIntent);

        ALARM_REQUEST_CODE++;
        intent.putExtra("alarm number", ALARM_REQUEST_CODE);
        intent.putExtra("Alarm_Option",1);
        alarmIntent = PendingIntent.getBroadcast(this,
                ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY,alarmIntent);

        textView.setText("Alarm: " +  calSet.getTime());
        ALARM_REQUEST_CODE++;
        currentTime = calSet;
    }

    public void Set_Alarm(View view) {
        open_Timepickerdialog();
    }

    public void Exit(View view) {
        finish();
    }
}