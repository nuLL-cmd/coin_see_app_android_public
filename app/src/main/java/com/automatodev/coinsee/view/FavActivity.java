package com.automatodev.coinsee.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.automatodev.coinsee.R;

public class FavActivity extends AppCompatActivity {

    public static boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
    }
    @Override
    protected void onStart() {
        super.onStart();
        status = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        status = false;
    }
}
