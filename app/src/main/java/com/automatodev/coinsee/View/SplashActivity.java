package com.automatodev.coinsee.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.automatodev.coinsee.R;

public class SplashActivity extends AppCompatActivity {
    private ImageView imgLogo_splash;
    private ImageView imgTitle_splash;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgLogo_splash = findViewById(R.id.imgLogo_splash);
        imgTitle_splash = findViewById(R.id.imgTitle_splash);

        animation = AnimationUtils.loadAnimation(this, R.anim.push_down);
        imgLogo_splash.setAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.push_right);
        imgTitle_splash.setAnimation(animation);



    }

    public void actLogin(View view){
        if (!LoginActivity.status){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
    public void actRegister(View view){
        if(!RegisterActivity.status){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}
