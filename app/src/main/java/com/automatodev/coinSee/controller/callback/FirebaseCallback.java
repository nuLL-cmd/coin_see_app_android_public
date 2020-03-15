package com.automatodev.coinSee.controller.callback;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface FirebaseCallback {
    void onSuccessLogin(Task<AuthResult> task);
    void onFailure(Exception e);
}
