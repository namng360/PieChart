package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eup.piechart.CircularProgressBar;
import com.eup.piechart.R;

public class MainActivity extends AppCompatActivity {
    int calsBurned = 0;
    int calsConsumed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addBurned(View v) {
        // Get the new value from a user input:
        EditText burnedEditText = findViewById(R.id.burned);
        int newBurnedCals = Integer.parseInt(burnedEditText.getText().toString());

        // Update the old value:
        calsBurned = newBurnedCals;
        updateChart();

    }

    public void addConsumed(View v) {
        // Get the new value from a user input:
        EditText consumedEditText = findViewById(R.id.consumed);
        int newConsumedCals = Integer.parseInt(consumedEditText.getText().toString());

        // Update the old value:
        calsConsumed = newConsumedCals;
        updateChart();

    }

    public void updateChart() {
        // Update the text in a center of the chart:
//        TextView numberOfCals = findViewById(R.id.number_of_calories);
//        int per = (calsBurned * 100) / calsConsumed;
//        numberOfCals.setText(per + "%");

        // Calculate the slice size and update the pie chart:
        CircularProgressBar pieChart = findViewById(R.id.stats_progressbar);
        double d = (double) calsBurned / (double) calsConsumed;
        int progress = (int) (d * 100);
        pieChart.setProgress(progress);
    }
}
