package com.automatodev.coinSee.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.automatodev.coinSee.R;

public class ForgotActivity extends AppCompatActivity {

    public static boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
    }

    @Override
    protected void onStart(){
        super.onStart();
        status = true;
    }
    @Override
    protected  void onStop(){
        super.onStop();
        status = false;
    }
    public void acForgotSplash(View view){
        NavUtils.navigateUpFromSameTask(ForgotActivity.this);
    }
}
