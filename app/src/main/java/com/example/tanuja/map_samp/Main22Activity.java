package com.example.tanuja.map_samp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class Main22Activity extends AppCompatActivity {
    String days= new String();
    CalendarView cv;
    Button btn1,btn2,btn3,btn4,btn5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


btn1=(Button)findViewById(R.id.button);
        btn2=(Button)findViewById(R.id.button3);
        btn3=(Button)findViewById(R.id.button4);
        btn4=(Button)findViewById(R.id.button5);
        btn5=(Button)findViewById(R.id.button6);
        cv = (CalendarView) findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select day");

        builder.setItems(dayss, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                String days = dayss[which].toString();
                Toast.makeText(getApplicationContext(),days,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.show();
        //date_arr= new String[]{"mon",
    }
    public void doneclick(View v){
        Intent myIntent1 = new Intent(this, Time_picker.class);
        myIntent1.putExtra("DAYS",days );
       // myIntent.putExtra("lastName", "Your Last Name Here");
        startActivity(myIntent1);
       // startActivity(new Intent(getApplicationContext(),Time_picker.class));
    }

}
