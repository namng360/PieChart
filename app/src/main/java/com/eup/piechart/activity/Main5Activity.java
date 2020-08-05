package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eup.piechart.R;

public class Main5Activity extends AppCompatActivity {
    SharedPreferences prefs = null;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        tv = (TextView) findViewById(R.id.tv);

        prefs = getSharedPreferences("com.eup.piechart", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            tv.setVisibility(View.VISIBLE);
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
}
