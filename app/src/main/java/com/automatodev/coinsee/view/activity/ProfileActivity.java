package com.automatodev.coinsee.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.View;

import com.automatodev.coinsee.R;

public class ProfileActivity extends AppCompatActivity {

    public static boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
}
