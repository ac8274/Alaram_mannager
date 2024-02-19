package com.example.alaram_mannager.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.alaram_mannager.alert_dialog_answer;

public class ExactAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        int option = intent.getIntExtra("Alarm_Option",0);
        if(option == 0)
        {
            Toast.makeText(context,"Set Up Reminder",Toast.LENGTH_SHORT).show();
        }
        else {

            Intent i = new Intent(context.getApplicationContext(), alert_dialog_answer.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

    }
}