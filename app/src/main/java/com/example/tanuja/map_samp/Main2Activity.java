package com.example.tanuja.map_samp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
ListView listView;
    ArrayAdapter<String> adapter;
    String[] schedules = {"abc","cde", "efg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,schedules);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(1)+" selected",Toast.LENGTH_SHORT).show();
              //  startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }
        });
    }

}
