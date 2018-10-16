package com.example.tanuja.map_samp;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Time_picker extends AppCompatActivity {
TimePicker tp ;
    String days;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
Intent intent = getIntent();
        days = intent.getStringExtra("DAYS");
        tp = (TimePicker) findViewById(R.id.timePicker);
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void Timeselected(View v){
        //startActivity(new Intent(getApplicationContext(),NotificationService.class));
        int hr = tp.getHour();
        int min = tp.getMinute();

       
    }

}
