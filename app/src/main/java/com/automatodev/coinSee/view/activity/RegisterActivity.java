package com.automatodev.coinSee.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.controller.service.CreateUserService;

public class RegisterActivity extends AppCompatActivity {

    public static boolean status;
    private EditText edtNome_register;
    private EditText edtSobre_register;
    private EditText edtCountry_register;
    private EditText edtEmail_register;
    private EditText edtPhone_register;
    private EditText edtPass_register;

    private CreateUserService createUserService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtNome_register = findViewById(R.id.edtNome_register);
        edtSobre_register = findViewById(R.id.edtSobre_register);
        edtCountry_register = findViewById(R.id.edtCountry_register);
        edtEmail_register = findViewById(R.id.edtEmail_register);
        edtPhone_register = findViewById(R.id.edtPhone_register);
        edtPass_register = findViewById(R.id.edtPass_register);

        createUserService = new CreateUserService(this);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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

    public void actRegisterLogin(View view) {
        if (!LoginActivity.status) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void teste(View view) {
        String[] fields = new String[6];
        int count = 0;
        fields[0] = edtNome_register.getText().toString().trim();
        fields[1] = edtSobre_register.getText().toString().trim();
        fields[2] = edtCountry_register.getText().toString().trim();
        fields[3] = edtEmail_register.getText().toString().trim();
        fields[4] = edtPhone_register.getText().toString().trim();
        fields[5] = edtPass_register.getText().toString().trim();

        for(String s: fields){
            if (s.isEmpty())
              count++;
        }

        if (count != 0){
            final AlertDialog alertaEmpty = new AlertDialog.Builder(this).create();
            View v = this.getLayoutInflater().inflate(R.layout.layout_message, null);
            Button btnDismiss_message = v.findViewById(R.id.btnDismiss_message);
            btnDismiss_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertaEmpty.dismiss();
                }
            });
            alertaEmpty.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            alertaEmpty.setView(v);
            alertaEmpty.show();
        }else{
            UserEntity userEntity = new UserEntity(fields[0],fields[1]
                    ,fields[2],fields[3],Long.parseLong(fields[4]));
            createUserService.serviceCreateNewUser(fields[3],fields[5],userEntity);
        }

    }
}
