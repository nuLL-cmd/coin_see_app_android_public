package com.automatodev.coinSee.controller.service.firebase;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.firebase.FAuthCallback;
import com.automatodev.coinSee.controller.callback.firebase.FSaveCallback;
import com.automatodev.coinSee.controller.callback.firebase.FUserCallback;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.models.firebase.UserFirebase;
import com.automatodev.coinSee.view.activity.MainActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserService {
    private Activity context;
    private UserFirebase userFirebase;
    private UserEntity userEntity;

    public UserService(Activity context) {
        this.context = context;
        userFirebase = new UserFirebase(context);
    }

    public void getUserService(String uid, final FUserCallback FUserCallback) {
        userFirebase.getUserFirebase(uid, new FUserCallback() {
            @Override
            public void onEventListener(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                userEntity = documentSnapshot.toObject(UserEntity.class);
                FUserCallback.onSuccess(userEntity);
            }

            @Override
            public void onSuccess(UserEntity userEntity) {
            }
        });
    }

    public void createUserService(String email, String pass, final UserEntity userEntity) {
        final AlertDialog alerta = new AlertDialog.Builder(context).create();
        final View view = context.getLayoutInflater().inflate(R.layout.progress_login, null);
        final ProgressBar spin_kit = view.findViewById(R.id.spin_kit);
        final LottieAnimationView okAnimation_progress = view.findViewById(R.id.okAnimation_progress);
        final TextView txtLabel_progress = view.findViewById(R.id.txtLabel_progress);
        alerta.setView(view);
        alerta.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alerta.show();
        userFirebase.createUserFirebase(email, pass, new FAuthCallback() {
            @Override
            public void onSuccess(Task<AuthResult> task) {
                userEntity.setUserUid(userFirebase.getUser().getUid());
                if (task.isSuccessful()) {
                    userFirebase.saveUserFirebase(userFirebase.getUser().getUid(), userEntity, new FSaveCallback() {
                        @Override
                        public void onFailure(Exception e) {
                        }

                        @Override
                        public void onSuccess(Task<Void> task) {
                            if (task.isSuccessful()) {
                                spin_kit.setVisibility(View.GONE);
                                txtLabel_progress.setText("Bem vindo!");
                                okAnimation_progress.setVisibility(View.VISIBLE);
                                okAnimation_progress.addAnimatorListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
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
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    public void serviceLogin(String email, String pass) {
        final androidx.appcompat.app.AlertDialog alerta = new androidx.appcompat.app.AlertDialog.Builder(context).create();
        final View v = context.getLayoutInflater().inflate(R.layout.progress_login, null);
        final ProgressBar spin_kit = v.findViewById(R.id.spin_kit);
        final LottieAnimationView okAnimation_progress = v.findViewById(R.id.okAnimation_progress);
        final TextView txtLabel_progress = v.findViewById(R.id.txtLabel_progress);
        txtLabel_progress.setText("Só um momento...");
        alerta.setCancelable(false);
        alerta.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alerta.setView(v);
        alerta.show();
        userFirebase.loginUserFirebase(email, pass, new FAuthCallback() {
            @Override
            public void onSuccess(Task<AuthResult> task) {
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
            public void onFailure(Exception e) {
                alerta.dismiss();
                final androidx.appcompat.app.AlertDialog alertaEmpty = new androidx.appcompat.app.AlertDialog.Builder(context).create();
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
        });
    }

    public void verifyUserAndLogin() {
        if (userFirebase.getUser() != null && !MainActivity.status) {
            String uid = userFirebase.getUser().getUid();
            Intent intent = new Intent(this.context, MainActivity.class);
            intent.putExtra("uid", uid);
            context.startActivity(intent);
            context.finish();
        }
    }

    public void recoverypassService(final String email) {
        final AlertDialog alerta = new AlertDialog.Builder(context).create();
        final View v = context.getLayoutInflater().inflate(R.layout.progress_login, null);
        final TextView txtLabel_progress = v.findViewById(R.id.txtLabel_progress);
        alerta.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        txtLabel_progress.setText("Enviando email...");
        alerta.setView(v);
        alerta.show();
        userFirebase.recoveryPass(email, new FSaveCallback() {
            @Override
            public void onSuccess(Task<Void> task) {
                if (task.isSuccessful()) {
                    alerta.dismiss();
                    final AlertDialog alertOk = new AlertDialog.Builder(context).create();
                    alertOk.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    View viewOk = context.getLayoutInflater().inflate(R.layout.layout_message,null);
                    TextView txtTitle_message = viewOk.findViewById(R.id.txtTitle_message);
                    TextView txtLabel_message = viewOk.findViewById(R.id.txtLabel_message);
                    txtTitle_message.setText("Sucesso!");
                    txtLabel_message.setText("Um email foi enviado para "+email+ "para a redefinição da senha." +
                            "\nFavor verifique a lixeira e/ou a caixa de  sapam.");
                    Button btnDismiss_message = viewOk.findViewById(R.id.btnDismiss_message);
                    alertOk.dismiss();
                    alertOk.setView(viewOk);
                    alertOk.show();
                    btnDismiss_message.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertOk.dismiss();
                            context.finish();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }
}
