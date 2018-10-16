package com.example.tanuja.map_samp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        btn1= (Button) findViewById(R.id.button1);
        btn2= (Button) findViewById(R.id.button2);
    }



    public void onBtn1Click(View v) {
        startActivity(new Intent(getApplicationContext(),Main2Activity.class));
    }
    public void onBtn2Click(View v) {
       startActivity(new Intent(getApplicationContext(),Main22Activity.class));
    }
}
