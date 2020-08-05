package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eup.piechart.R;

public class Main13Activity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;

    private RelativeLayout mRelativeLayout;
    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main13);

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = Main13Activity.this;

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        mTextView = (TextView) findViewById(R.id.tv);
        mButton = (Button) findViewById(R.id.btn);

        // Set a click listener for button
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do the task
                doTask();
            }
        });
    }

    // Custom method to do a task
    protected void doTask(){
        // Specify the text/word to highlight inside TextView
        String textToHighlight = "the";

        // Construct the formatted text
        String replacedWith = "<font color='red'>" + textToHighlight + "</font>";

        // Get the text from TextView
        String originalString = mTextView.getText().toString();

        // Replace the specified text/word with formatted text/word
        String modifiedString = originalString.replaceAll(textToHighlight,replacedWith);

        // Update the TextView text
        mTextView.setText(Html.fromHtml(modifiedString));
    }
}
