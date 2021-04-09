package com.automatodev.coinSee.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.service.firebase.UserService;

public class SplashActivity extends AppCompatActivity {
    private ImageView imgLogo_splash;
    private ImageView imgTitle_splash;
    private Animation animation;
    private UserService userService;

    public static String FACEBOOK_URL = "https://www.facebook.com/Nazomiiamazo";
    public static String LINKEDIN_URL = "https://www.linkedin.com/in/marcoaj/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        userService = new UserService(this);
        imgLogo_splash = findViewById(R.id.imgLogo_splash);
        imgTitle_splash = findViewById(R.id.imgTitle_splash);
        animation = AnimationUtils.loadAnimation(this, R.anim.push_down);
        imgLogo_splash.setAnimation(animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.push_right);
        imgTitle_splash.setAnimation(animation);

      // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



    }

    public void actSplashLogin(View view) {
        if (!LoginActivity.status) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void actSplashRegister(View view) {
        if (!RegisterActivity.status) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        userService.verifyUserAndLogin();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void openFacebook(View view){
        Intent facebook = new Intent(Intent.ACTION_VIEW);
        facebook.setData(Uri.parse(FACEBOOK_URL));
        startActivity(facebook);
    }

    public void openLinkedin(View view){
        Intent facebook = new Intent(Intent.ACTION_VIEW);
        facebook.setData(Uri.parse(LINKEDIN_URL));
        startActivity(facebook);
    }
}

