package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.eup.piechart.R;
import com.eup.piechart.adapter.MyAdapter;
import com.eup.piechart.adapter.RecyclerAdapter;
import com.eup.piechart.model.ListItem;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main11Activity extends AppCompatActivity {
    private List<ListItem> forexInstruments;
    private List<String> selectedGlobalInstruments;
    private RecyclerView forexListView;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);
        String[] temp = new String[]{
                "EURCAD",
                "USDGPY",
                "AUDUSD",
                "USDCHF",
                "EURGPY",
                "6B 03-16",
                "6E 03-16",
                "CL 03-16",
                "ZB 09-16"
        };
        selectedGlobalInstruments = new ArrayList<>(Arrays.asList(temp));
        forexInstruments = new ArrayList<>();
        forexInstruments.add(getDefaultItem("USDGPY"));
        forexInstruments.add(getDefaultItem("AUDUSD"));
        forexInstruments.add(getDefaultItem("EURGBP"));
        forexInstruments.add(getDefaultItem("USDJPY"));
        forexInstruments.add(getDefaultItem("GBPUSD"));
        forexInstruments.add(getDefaultItem("EURGPY"));
        forexInstruments.add(getDefaultItem("EURGPY"));

//        updateCommonElements();

        forexListView = findViewById(R.id.listView);
        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(forexInstruments, this);
//        final MyAdapter adapter = new MyAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1);
//        recyclerAdapter.addAll(forexInstruments);
//        final int totalSize = forexInstruments.size();
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                int span;
//                span = totalSize % 3;
//                if (totalSize < 3) {
//                    return 6;
//                } else if (span == 0 || (position <= ((totalSize - 1) - span))) {
//                    return 2;
//                } else if (span == 1) {
//                    return 6;
//                } else {
//                    return 3;
//                }
//            }
//        });

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);


        forexListView.setLayoutManager(layoutManager);
        forexListView.setAdapter(recyclerAdapter);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCommonElements();
                recyclerAdapter.notifyDataSetChanged();
            }
        });


    }

//    public static class MySizeLookup extends GridLayoutManager.SpanSizeLookup {
//
//        public int getSpanSize(int position) {
//            if (position >= 0 && position <= 2) {
//                return 2;
//            } else if (position == 3 || position == 6) {
//                return 1;
//            } else {
//                return 2;
//            }
//        }
//    }

    private ListItem getDefaultItem(String value) {
        return new ListItem(value, false);
    }

    private void updateCommonElements() {
        for (int j = 0; j < selectedGlobalInstruments.size(); j++) {
            for (int i = 0; i < forexInstruments.size(); i++) {
                String instrumentList = forexInstruments.get(i).value;
                Log.d("aaa", instrumentList);
                Log.d("bbb", selectedGlobalInstruments.toString());
                Log.d("ccc", forexInstruments.toString());
                if (selectedGlobalInstruments.get(j).equals(instrumentList)) {
                    forexInstruments.get(i).hasBackground = true;
                }
            }
        }
    }
}
