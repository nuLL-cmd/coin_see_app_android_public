package com.automatodev.coinSee.controller.callback.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface FAuthCallback {
    void onSuccess(Task<AuthResult> task);
    void onFailure(Exception e);
}
