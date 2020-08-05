package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.eup.piechart.FuriganaTextView;
import com.eup.piechart.MyFuriganaView;
import com.eup.piechart.R;

public class Main12Activity extends AppCompatActivity {
    private FuriganaTextView tv;
    private TextView tv1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);
        tv = (FuriganaTextView) findViewById(R.id.tv);
        tv1 = (TextView) findViewById(R.id.tv1);
        String s = "<ruby><p style ='color:lime'>六書</p><rt>りくしょ</rt></ruby>";
//        String str = "<p style ='color:lime'>六書</p>りくしょ";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            tv.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            tv.setText(Html.fromHtml(s));
//        }
        tv.setText(MyFuriganaView.htlm2Furigana(s));
    }
}
