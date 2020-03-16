package com.automatodev.coinSee.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.automatodev.coinSee.R;

public class RegisterActivity extends AppCompatActivity {

    public static boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        status = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        status = true;
    }

    public void actRegisterLogin(View view){
        if(!LoginActivity.status){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void teste(View view){

        final android.app.AlertDialog alerta = new android.app.AlertDialog.Builder(this).create();
        View v = this.getLayoutInflater().inflate(R.layout.progress_login,null);
        alerta.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alerta.setView(v);
        alerta.show();
    }
}
