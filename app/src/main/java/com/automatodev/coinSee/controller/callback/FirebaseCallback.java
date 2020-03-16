package com.automatodev.coinSee.controller.callback;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public interface FirebaseCallback {

    void onSuccessLogin(Task<AuthResult> task);

    void onSuccessCreateUser(Task<AuthResult> task, FirebaseUser firebaseUser);

    void onFailure(Exception e);

    void onSuccessSave(Task<Void> task);
}
