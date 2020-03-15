package com.automatodev.coinSee.models.Firebase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.automatodev.coinSee.controller.callback.FirebaseCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RequestAuth {
    private Activity context;
    private FirebaseAuth auth;

    public RequestAuth(Activity context) {
        auth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public void requestLogin(String email, String pass, final FirebaseCallback callback) {
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        callback.onSuccessLogin(task);
                    }
                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e);
            }
        });
    }

    public void authSignOut() {
        auth.signOut();
    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }
}
