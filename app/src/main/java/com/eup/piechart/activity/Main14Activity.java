package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.eup.piechart.FuriganaTextView;
import com.eup.piechart.HtmlHelper;
import com.eup.piechart.MyFuriganaView;
import com.eup.piechart.R;
import com.eup.piechart.StringHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main14Activity extends AppCompatActivity {
    private EditText searchEdt;
    private TextView tv;
    private WebView frtv;
    private HtmlHelper htmlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main14);
        searchEdt = (EditText) findViewById(R.id.search_edt);
        tv = (TextView) findViewById(R.id.tv);
        frtv = (WebView) findViewById(R.id.frtv);
        String hl = "<font color='#ccc222'>一</font>";
//        String w = "もう<ruby><body  align='center'><font color='#ccc222'>ABC</font></body><rt><font color='#FF0000'>abcd</font></rt></ruby>つ";
        String w = "十一";
        String p = "じゅういち";
        String str = MyFuriganaView.stringToFurigana(w, p);
        String rp = str.replace("一", hl);
        String highLight = "<p style=\"font-size:24px\">" + rp + "</p>";
        Log.d("fff", rp);
        String s = getTagValues(rp, "一");
        s = s.substring(s.indexOf("<rt>") + 4);
        s = s.substring(0, s.indexOf("</rt>"));
        Log.d("bbbb", rp + "    - rp");
        Log.d("bbbb", s);
        WebSettings webSettings = frtv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        frtv.setBackgroundColor(Color.TRANSPARENT);
//
        frtv.loadDataWithBaseURL(null, highLight,"text/html","charset=UTF-8", null);
//        String titleHtml = htmlHelper.string2Title(rp, 24, "rgba(0,0,0,.71)");
//        frtv.loadDataWithBaseURL(null, titleHtml, "text/html", "utf-8", null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv.setText(Html.fromHtml(hl, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv.setText(Html.fromHtml(hl));
        }
//        searchEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                highlightText(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    private static String getTagValues(String str, String kanji) {
        Pattern TAG_REGEX = Pattern.compile("<font color='#ccc222'>" + kanji + "</font>(.+?)</ruby>", Pattern.DOTALL);
        Matcher matcher = TAG_REGEX.matcher(str);
        matcher.find();
        return matcher.group(1);
    }

//    private void highlightText(String s) {
//        SpannableString spannableString = new SpannableString(frtv.getText());
//        BackgroundColorSpan[] backgroundColorSpan =
//                spannableString.getSpans(0, spannableString.length(), BackgroundColorSpan.class);
//        for (BackgroundColorSpan bgSpan : backgroundColorSpan) {
//            spannableString.removeSpan(bgSpan);
//        }
//        int indexOfKeyWord = spannableString.toString().indexOf(s);
//        while (indexOfKeyWord > 0) {
//            spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), indexOfKeyWord,
//                    indexOfKeyWord + s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            indexOfKeyWord = spannableString.toString().indexOf(s, indexOfKeyWord + s.length());
//        }
//        tv.setText(spannableString);
//    }
}
