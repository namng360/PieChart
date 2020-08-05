package com.eup.piechart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eup.piechart.model.ListItem;

public class MyAdapter extends ArrayAdapter<ListItem> {
    int res = 0;
    int textRes = 0;

    public MyAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        res = resource;
        textRes = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
        }
        ListItem item = getItem(position);
        if (item.hasBackground) {
            convertView.setBackgroundColor(Color.RED);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        TextView textView = (TextView) convertView.findViewById(textRes);
        textView.setText(item.value);
        return convertView;
    }
}