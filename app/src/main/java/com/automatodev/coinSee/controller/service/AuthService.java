package com.automatodev.coinSee.controller.service;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.FirebaseCallback;
import com.automatodev.coinSee.models.Firebase.RequestAuth;
import com.automatodev.coinSee.view.activity.MainActivity;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class AuthService {
    private Activity context;
    private RequestAuth requestAu;
    private ProgressBar spin_kit_login;
    private ThreeBounce three;

    public AuthService(Activity context) {
        this.context = context;
        requestAu = new RequestAuth(context);
    }

    public void serviceLogin(String email, String pass) {
        spin_kit_login = context.findViewById(R.id.spin_kit_login);
        three = new ThreeBounce();
        spin_kit_login.setIndeterminateDrawable(three);
        spin_kit_login.setVisibility(View.VISIBLE);
        requestAu.requestLogin(email, pass, new FirebaseCallback() {
            @Override
            public void onSuccessLogin(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    verifyUserAndLogin();
                }
            }

            @Override
            public void onFailure(Exception e) {
                spin_kit_login.setVisibility(View.GONE);
                AlertDialog.Builder alerta = new AlertDialog.Builder(context);
                alerta.setTitle("Ops!");
                alerta.setMessage("Favor verifique se seu email e/ou senha estão corretos\nDá uma checada tambem na sua internet :D");
                alerta.setPositiveButton("Entendi", null);
                alerta.show();
            }
        });
    }

    public void verifyUserAndLogin() {
        if (requestAu.getUser() != null && !MainActivity.status) {
            String uid = requestAu.getUser().getUid();
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("uid", uid);
            context.startActivity(intent);
            context.finish();
        }
    }
}
