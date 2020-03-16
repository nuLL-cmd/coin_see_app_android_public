package com.automatodev.coinSee.controller.service;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.FirebaseCallback;
import com.automatodev.coinSee.models.Firebase.AuthFirebase;
import com.automatodev.coinSee.view.activity.MainActivity;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class AuthService {
    private Activity context;
    private AuthFirebase requestAu;
    private ProgressBar spin_kit_login;
    private ThreeBounce three;

    public AuthService(Activity context) {
        this.context = context;
        requestAu = new AuthFirebase(this.context);
    }

    public void serviceLogin(String email, String pass) {
        View v = context.getLayoutInflater().inflate(R.layout.progress_login,null);
        TextView txtLabel_progress = v.findViewById(R.id.txtLabel_progress);
        txtLabel_progress.setText("Só um momento...");
        final AlertDialog alerta = new AlertDialog.Builder(context).create();
        alerta.setCancelable(false);
        alerta.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alerta.setView(v);
        alerta.show();
        requestAu.requestLogin(email, pass, new FirebaseCallback() {
            @Override
            public void onSuccessLogin(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    verifyUserAndLogin();
                }
            }

            @Override
            public void onSuccessCreateUser(Task<AuthResult> task, FirebaseUser firebaseUser) {
            }

            @Override
            public void onFailure(Exception e) {
                alerta.dismiss();
                AlertDialog.Builder alerta = new AlertDialog.Builder(context);
                alerta.setTitle("Ops!");
                alerta.setMessage("Favor verifique se seu email e/ou senha estão corretos\nDá uma checada tambem na sua internet :D");
                alerta.setPositiveButton("Entendi", null);
                alerta.show();
            }

            @Override
            public void onSuccessSave(Task<Void> task) {
            }
        });
    }

    public void verifyUserAndLogin() {
        if (requestAu.getUser() != null && !MainActivity.status) {
            String uid = requestAu.getUser().getUid();
            Intent intent = new Intent(this.context, MainActivity.class);
            intent.putExtra("uid", uid);
            context.startActivity(intent);
            context.finish();
        }
    }

}
