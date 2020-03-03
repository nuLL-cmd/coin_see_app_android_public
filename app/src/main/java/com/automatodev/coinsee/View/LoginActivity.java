package com.automatodev.coinsee.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.automatodev.coinsee.R;

public class LoginActivity extends AppCompatActivity {

    public static boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    public void actLoginRegister(View view){
        if (!RegisterActivity.status){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void actLoginForgot(View view){
        if (!ForgotActivity.status){
            Intent intent = new Intent(this, ForgotActivity.class);
            startActivity(intent);
        }
    }
}
