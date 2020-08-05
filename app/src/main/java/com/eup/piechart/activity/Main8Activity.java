package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.eup.piechart.GridDividerDecoration;
import com.eup.piechart.R;
import com.eup.piechart.adapter.ElementsAdapter;
import com.eup.piechart.model.Elements;

import java.util.ArrayList;
import java.util.List;

public class Main8Activity extends AppCompatActivity implements ElementsAdapter.Callbacks {
    private List<Elements> mainList = new ArrayList<>();
    private List<Elements> dummyList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ElementsAdapter elementsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        int columns = 2;
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columns);

        recyclerView.addItemDecoration(new GridDividerDecoration(this));
        recyclerView.setLayoutManager(gridLayoutManager);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return elementsAdapter.isPositionFooter(position) ? gridLayoutManager.getSpanCount() : 1;
            }
        });

        // added all elements to main list
        for (int i = 0; i < 12; ++i) {
            Elements elements = new Elements();
            elements.setIcon(R.mipmap.ic_launcher);
            elements.setName("Element " + i);

            mainList.add(elements);
        }

        // now only add number of elements for the first show i.e. 6
        for (int i = 0; i < 6; ++i) {
            dummyList.add(mainList.get(i));
        }

        elementsAdapter = new ElementsAdapter(dummyList);
        elementsAdapter.setCallback(this);
        elementsAdapter.setWithFooter(true); //enabling footer to show
        recyclerView.setAdapter(elementsAdapter);
    }

    @Override
    public void onClickLoadMore() {
        elementsAdapter.setWithFooter(false); // hide footer

        // now add remaining elements
        for (int i = 6; i < mainList.size(); ++i) {
            dummyList.add(mainList.get(i));
        }

        elementsAdapter.notifyDataSetChanged(); // more elements will be added
    }
}
