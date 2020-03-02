package com.automatodev.coinsee.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.automatodev.coinsee.R;

public class LoginActivity extends AppCompatActivity {

    public static boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    @Override
    protected void onStart(){
        super.onStart();
        status = false;
    }
}
