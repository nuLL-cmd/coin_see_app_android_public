package com.automatodev.coinSee.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.models.Firebase.AuthFirebase;

public class LoginActivity extends AppCompatActivity {

    public static boolean status;
    private AuthFirebase auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = new AuthFirebase(this);
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
    public void actLoginMain(View view){
       auth.login("teste@gmail.com","402866");
    }

}
