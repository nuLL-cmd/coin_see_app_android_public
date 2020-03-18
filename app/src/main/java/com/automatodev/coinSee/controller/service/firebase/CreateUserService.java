package com.automatodev.coinSee.controller.service.firebase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.FAuthCallback;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.models.firebase.CreateUserFirebase;
import com.automatodev.coinSee.models.firebase.SaveUserFirebase;
import com.automatodev.coinSee.view.activity.MainActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class CreateUserService {
    private Activity context;
    private CreateUserFirebase createUserFirebase;
    private SaveUserFirebase saveUserFirebase;

    public CreateUserService(Activity context) {
        this.context = context;
        saveUserFirebase = new SaveUserFirebase(context);
        createUserFirebase = new CreateUserFirebase(context);
    }

    public void serviceCreateNewUser(String email, String pass, final UserEntity userEntity){
        final AlertDialog alerta = new AlertDialog.Builder(context).create();
        View view = context.getLayoutInflater().inflate(R.layout.progress_login,null);
        alerta.setView(view);
        alerta.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alerta.show();
        createUserFirebase.createUser(email, pass, new FAuthCallback() {
            @Override
            public void onSuccessLogin(Task<AuthResult> task) {
            }
            @Override
            public void onSuccessCreateUser(Task<AuthResult> task, final FirebaseUser firebaseUser) {
                userEntity.setUserUid(createUserFirebase.getUser().getUid());
                if (task.isSuccessful()){
                    saveUserFirebase.saveNewUser(firebaseUser.getUid(), userEntity, new FAuthCallback() {
                        @Override
                        public void onSuccessLogin(Task<AuthResult> task) {
                        }

                        @Override
                        public void onSuccessCreateUser(Task<AuthResult> task, FirebaseUser firebaseUser1) {
                        }

                        @Override
                        public void onFailure(Exception e) {
                        }

                        @Override
                        public void onSuccessSave(Task<Void> task) {
                            if (task.isSuccessful()){
                                verifyUserAndLogin(firebaseUser.getUid());
                            }
                        }
                    });
                }
            }
            @Override
            public void onFailure(Exception e) {
            }
            @Override
            public void onSuccessSave(Task<Void> task) {
            }
        });
    }

    private void verifyUserAndLogin(String uid) {
        if (createUserFirebase.getUser() != null && !MainActivity.status) {
            Intent intent = new Intent(this.context, MainActivity.class);
            intent.putExtra("uid", uid);
            context.startActivity(intent);
            context.finish();
        }
    }
}
