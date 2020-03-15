package com.automatodev.coinSee.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.models.Firebase.RequestAuth;

public class ProfileActivity extends AppCompatActivity {
    private RequestAuth auth;
    public static boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = new RequestAuth(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        status = true;
    }
    @Override
    protected void onStop(){
        super.onStop();
        status = false;
    }

    public void actProfileMain(View view){
        NavUtils.navigateUpFromSameTask(this);
    }

    public void logout(View view){
        auth.authSignOut();
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();;
    }


}
