package com.eup.piechart.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eup.piechart.R;

public class Main4Activity extends AppCompatActivity {
    private ViewGroup viewGroup;
    private Button btn1;
    private TextView tv2;
    private ImageView img1;
    private TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        viewGroup = findViewById(R.id.ln);
        btn1 = (Button) findViewById(R.id.btn1);
        tv2 = (TextView) findViewById(R.id.tv2);
        img1 = (ImageView) findViewById(R.id.img1);
        tv1 = (TextView) findViewById(R.id.tv1);
        btn1.setOnClickListener(new View.OnClickListener() {
            boolean visable = false;

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    visable = !visable;
                    tv2.setVisibility(visable ? View.VISIBLE : View.GONE);
//                    TransitionManager.beginDelayedTransition(viewGroup);
//                    TranslateAnimation animationRight = new TranslateAnimation(0.0f, 500.0f, 0.0f, 0.0f);
//                    animationRight.setDuration(1500);
//                    animationRight.setFillAfter(true);
//                    TranslateAnimation animationLeft = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
//                    animationLeft.setDuration(1500);
//                    animationLeft.setFillAfter(true);
                    Animation RightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rtl);
                    RightSwipe.setFillAfter(true);
                    Animation LeftSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ltr);
                    LeftSwipe.setFillAfter(true);
                    tv1.startAnimation(visable ? RightSwipe : LeftSwipe);
//                    tv1.startAnimation(visable ? animationRight : animationLeft);
                    btn1.setText(visable ? "Hihi": "hi");
                    Log.d("ttt", " - " + visable);
                }
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tv = tv2.getText().toString().trim();
                Toast.makeText(Main4Activity.this, tv, Toast.LENGTH_SHORT).show();
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tv = tv1.getText().toString().trim();
                Toast.makeText(Main4Activity.this, tv, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void slidefromRightToLeft(View view) {

        TranslateAnimation animate;
        if (view.getHeight() == 0) {
            viewGroup.getHeight(); // parent layout
            animate = new TranslateAnimation(viewGroup.getWidth() / 2,
                    0, 0, 0);
        } else {
            animate = new TranslateAnimation(view.getWidth(), 0, 0, 0); // View for animation
        }

        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE); // Change visibility VISIBLE or GONE
    }
}
