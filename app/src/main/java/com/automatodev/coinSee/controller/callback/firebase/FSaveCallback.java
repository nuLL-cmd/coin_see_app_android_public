package com.automatodev.coinSee.controller.callback.firebase;

import com.google.android.gms.tasks.Task;

public interface FSaveCallback {

    void onSuccess(Task<Void> task);
    void onFailure(Exception e);
}
