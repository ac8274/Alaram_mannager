package com.example.alaram_mannager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.alaram_mannager.recivers.ExactAlarm;

import java.util.Calendar;

public class alert_dialog_answer extends AppCompatActivity {
    Intent gi;
    private AlertDialog.Builder adb;
    private int ALARM_REQUEST_CODE;
    private Calendar currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gi = getIntent();
        adb = new AlertDialog.Builder(this);
        ALARM_REQUEST_CODE = gi.getIntExtra("alarm number",0);
        adb.setTitle("Alert number: "+ Integer.toString(ALARM_REQUEST_CODE));
        adb.setMessage("Your alarm has gone off do you want to repeat?");
        adb.setPositiveButton("Ok",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id) {
                setExactAlarm(0);
            }
        });
        adb.setNegativeButton("Snooze 5 minutes ",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id) {
                setExactAlarm(1);
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }

    private void setExactAlarm(int option)
    {
        Intent intent = new Intent(this, ExactAlarm.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
                ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(alarmIntent);

        long l = 0;
        intent.putExtra("original_time",gi.getLongExtra("original_time",l));
        intent.putExtra("alarm number", ALARM_REQUEST_CODE);
        intent.putExtra("Alarm_Option",1);
        alarmIntent = PendingIntent.getBroadcast(this,
                ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        if(option==0)
        {
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, gi.getLongExtra("original_time",l), AlarmManager.INTERVAL_DAY,alarmIntent);
        }
        else {
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, currentTime.getTimeInMillis(),1000*60*5,alarmIntent);
        }
    }

}