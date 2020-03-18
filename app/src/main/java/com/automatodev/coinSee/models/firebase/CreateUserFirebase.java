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

public class CreateUserFirebase {
    private Activity context;
    private FirebaseAuth auth;

    public CreateUserFirebase(Activity context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();
    }

    public void createUser(String email, String pass, final FAuthCallback callback){
        auth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        callback.onSuccessCreateUser(task,auth.getCurrentUser());
                    }
                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e);
            }
        });
    }

    public FirebaseUser getUser(){
        return auth.getCurrentUser();
    }
}
