package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eup.piechart.R;
import com.eup.piechart.adapter.ShareRefAdapter;
import com.eup.piechart.model.ShareRefModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Main6Activity extends AppCompatActivity {
    ArrayList<ShareRefModel> shareRefModelArrayList;
    private RecyclerView mRecyclerView;
    private ShareRefAdapter shareRefAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String json;
    private EditText line1, line2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        loadData();
        buildRecyclerView();
        setInsertButton();

        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedpreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        json = gson.toJson(shareRefModelArrayList);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedpreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<ShareRefModel>>() {
        }.getType();
        shareRefModelArrayList = gson.fromJson(json, type);

        if (shareRefModelArrayList == null) {
            shareRefModelArrayList = new ArrayList<>();
        }
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        shareRefAdapter = new ShareRefAdapter(shareRefModelArrayList, this);
        shareRefAdapter.delete = new ShareRefAdapter.Delete() {
            @Override
            public void onDelete() {
                json = new Gson().toJson(shareRefModelArrayList);
            }
        };
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(shareRefAdapter);
    }

    private void setInsertButton() {
        Button buttonInsert = findViewById(R.id.button_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line1 = findViewById(R.id.edittext_line_1);
                line2 = findViewById(R.id.edittext_line_2);
                Log.d("ddd", json);
                if (!json.contains("\"mLine1\":\"" + line1.getText().toString() + "\"")){
                    shareRefModelArrayList.add(new ShareRefModel(line1.getText().toString(), line2.getText().toString()));
                    shareRefAdapter.notifyItemInserted(shareRefModelArrayList.size());
                    saveData();

                }
//                insertItem(line1.getText().toString(), line2.getText().toString());
            }
        });
    }

//    private void insertItem(String a, String b) {
//
//        if(json == null){
//            shareRefModelArrayList.add(new ShareRefModel(a, b));
//            shareRefAdapter.notifyItemInserted(shareRefModelArrayList.size());
//            return;
//        }
//        if (json.contains("\"" + a + "\"")){
//            shareRefModelArrayList.add(new ShareRefModel(a, b));
//            shareRefAdapter.notifyItemInserted(shareRefModelArrayList.size());
//        }
//    }
}
