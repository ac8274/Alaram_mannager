package com.example.alaram_mannager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class alert_dialog_answer extends AppCompatActivity {
    Intent gi;
    private AlertDialog.Builder adb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gi = getIntent();
        adb = new AlertDialog.Builder(this);
        adb.setTitle("Alert number: ");
    }
}