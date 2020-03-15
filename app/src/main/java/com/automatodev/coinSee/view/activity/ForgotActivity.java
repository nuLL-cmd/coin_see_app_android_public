package com.automatodev.coinSee.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
}
