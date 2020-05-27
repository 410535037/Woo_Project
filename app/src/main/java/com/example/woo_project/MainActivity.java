package com.example.woo_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.woo_project.home.home;
import com.example.woo_project.home.home2;
import com.example.woo_project.login.login;
import com.example.woo_project.record.record;

public class   MainActivity extends AppCompatActivity {

    TextView logo,tx2,tx3;
    Button btn;
    ImageView bg;
    Animation smalltobig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        logo = (TextView) findViewById(R.id.logo);
        tx2 = (TextView) findViewById(R.id.tx2);
        tx3 = (TextView) findViewById(R.id.tx3);
        btn = (Button) findViewById(R.id.btn);
        bg = (ImageView) findViewById(R.id.bg);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        bg.startAnimation(smalltobig);

        logo.setTranslationX(400);
        tx2.setTranslationX(400);
        tx3.setTranslationX(400);
        btn.setTranslationX(400);

        logo.setAlpha(0);
        tx2.setAlpha(0);
        tx3.setAlpha(0);
        btn.setAlpha(0);


        logo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        tx2.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        tx3.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();
        btn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent( MainActivity.this, home.class);
                startActivity(a);
                MainActivity.this.finish();

            }
        });
    }


}

