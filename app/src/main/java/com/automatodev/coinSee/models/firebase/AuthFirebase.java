package com.automatodev.coinSee.models.firebase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.automatodev.coinSee.controller.callback.FAuthCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthFirebase {
    private Activity context;
    private FirebaseAuth auth;

    public AuthFirebase(Activity context) {
        auth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public void requestLogin(String email, String pass, final FAuthCallback callback) {
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
