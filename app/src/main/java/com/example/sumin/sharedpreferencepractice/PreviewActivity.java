package com.example.sumin.sharedpreferencepractice;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by LG on 2017-06-27.
 */

public class PreviewActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_activity);

        ImageView iv = (ImageView) findViewById(R.id.preview);
        final AnimationDrawable drawable = (AnimationDrawable) iv.getBackground();

        final LinearLayout mi = (LinearLayout) findViewById(R.id.mini);

        //Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.move_top_bottom);
        //mi.startAnimation(animation1);


        TextView ga = (TextView) findViewById(R.id.ga);

        //Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.move_left_right);
        //mi.startAnimation(animation2);


        TextView gye = (TextView) findViewById(R.id.gye);

        //Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.move_left_right);
        //ga.startAnimation(animation2);

        TextView boo = (TextView) findViewById(R.id.boo);

        TextView makethRich = (TextView) findViewById(R.id.maketh_rich);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        makethRich.startAnimation(fadeIn);


        drawable.start();
        //animator.start();


        // 2초 후 인트로 액티비티 제거
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                drawable.stop();

                Intent intent = new Intent(PreviewActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, 3500);




    }
}
