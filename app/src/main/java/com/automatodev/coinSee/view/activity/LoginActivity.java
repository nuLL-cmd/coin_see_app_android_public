package com.automatodev.coinSee.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.service.AuthService;

public class LoginActivity extends AppCompatActivity {

    public static boolean status;
    private AuthService authService;
    private EditText edtEmail_login;
    private EditText edtPass_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authService = new AuthService(this);
        edtEmail_login = findViewById(R.id.edtEmail_login);
        edtPass_login = findViewById(R.id.edtPass_login);
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

    public void actLoginRegister(View view) {
        if (!RegisterActivity.status) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void actLoginForgot(View view) {
        if (!ForgotActivity.status) {
            Intent intent = new Intent(this, ForgotActivity.class);
            startActivity(intent);
        }
    }

    public void actLoginMain(View view) {
        edtEmail_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                edtEmail_login.setBackgroundResource(R.drawable.bg_edt);
                else
                    edtEmail_login.setBackgroundResource(R.drawable.bg_edt_wrong);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPass_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    edtPass_login.setBackgroundResource(R.drawable.bg_edt);
                else
                    edtPass_login.setBackgroundResource(R.drawable.bg_edt_wrong);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        String email = edtEmail_login.getText().toString().trim();
        String pass = edtPass_login.getText().toString().trim();
        if (email.isEmpty() || pass.isEmpty()) {
            if (email.isEmpty() && pass.isEmpty()) {
                edtPass_login.setBackgroundResource(R.drawable.bg_edt_wrong);
                edtEmail_login.setBackgroundResource(R.drawable.bg_edt_wrong);
            } else if (email.isEmpty())
                edtEmail_login.setBackgroundResource(R.drawable.bg_edt_wrong);
            else
                edtPass_login.setBackgroundResource(R.drawable.bg_edt_wrong);
        } else
            authService.serviceLogin(email, pass);
        ;
    }
}
