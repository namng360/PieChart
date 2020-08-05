package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import com.eup.piechart.R;

public class Main2Activity extends AppCompatActivity {
    //    private RecyclerView rcv;
//    private Adapter adapter;
//    private LinearLayoutManager linearLayoutManager;
//    private List<Model> models;
    private TextView textView;

    private Switch sw;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SWITCH1 = "switch1";
    private boolean switchOnOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sw = (Switch) findViewById(R.id.sw);
        textView = findViewById(R.id.textview);
//        rcv = (RecyclerView) findViewById(R.id.rcv);
//        models = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            models.add(new Model("a " + i));
//        }
//        Collections.shuffle(models);
//        adapter = new Adapter(this, models);
//        linearLayoutManager = new LinearLayoutManager(this);
//        rcv.setLayoutManager(linearLayoutManager);
//        rcv.setAdapter(adapter);
        loadData();
        updateViews();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }

    public void updateViews() {
        sw.setChecked(switchOnOff);
        if (switchOnOff == true){
            textView.setText("True");
        }else if(switchOnOff == false){
            textView.setText("False");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (switchOnOff == true){
            textView.setText("True");
        }else if(switchOnOff == false){
            textView.setText("False");
        }
    }
}



