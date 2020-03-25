package com.automatodev.coinSee.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.service.firebase.UserService;

public class ForgotActivity extends AppCompatActivity {

    public static boolean status;
    private UserService userService;
    private EditText edtEmail_forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        userService = new UserService(this);
        edtEmail_forgot = findViewById(R.id.edtEmail_forgot);


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
    public void sendEmail(View view){
        edtEmail_forgot.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    edtEmail_forgot.setBackgroundResource(R.drawable.bg_edt);
                else
                    edtEmail_forgot.setBackgroundResource(R.drawable.bg_edt_wrong);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        if (edtEmail_forgot.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "É necessario informar um email valido para esta operação!", Toast.LENGTH_SHORT).show();
            edtEmail_forgot.setBackgroundResource(R.drawable.bg_edt_wrong);
        }else
        userService.recoverypassService(edtEmail_forgot.getText().toString().trim());
    }
}
