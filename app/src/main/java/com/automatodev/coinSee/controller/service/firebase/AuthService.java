package com.automatodev.coinSee.controller.service.firebase;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.airbnb.lottie.LottieAnimationView;
import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.FAuthCallback;
import com.automatodev.coinSee.models.firebase.AuthFirebase;
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
        final AlertDialog alerta = new AlertDialog.Builder(context).create();
        final View v = context.getLayoutInflater().inflate(R.layout.progress_login, null);
        final ProgressBar spin_kit = v.findViewById(R.id.spin_kit);
        final LottieAnimationView okAnimation_progress = v.findViewById(R.id.okAnimation_progress);
        final TextView txtLabel_progress = v.findViewById(R.id.txtLabel_progress);

        txtLabel_progress.setText("Só um momento...");
        alerta.setCancelable(false);
        alerta.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alerta.setView(v);
        alerta.show();
        requestAu.requestLogin(email, pass, new FAuthCallback() {
            @Override
            public void onSuccessLogin(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    txtLabel_progress.setText("Successo!!");
                    spin_kit.setVisibility(View.GONE);
                    okAnimation_progress.setVisibility(View.VISIBLE);
                    okAnimation_progress.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            alerta.dismiss();
                            verifyUserAndLogin();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                }

            }

            @Override
            public void onSuccessCreateUser(Task<AuthResult> task, FirebaseUser firebaseUser) {
            }

            @Override
            public void onFailure(Exception e) {
                alerta.dismiss();
                final AlertDialog alertaEmpty = new AlertDialog.Builder(context).create();
                View v = context.getLayoutInflater().inflate(R.layout.layout_message, null);
                TextView txtLabel_message = v.findViewById(R.id.txtLabel_message);
                txtLabel_message.setText("Verifique se seu email e/ou senha estão corretos\nDá uma checada também na sua internet :D");
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
