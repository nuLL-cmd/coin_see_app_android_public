package com.automatodev.coinSee.models.firebase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.automatodev.coinSee.controller.callback.FAuthCallback;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class SaveUserFirebase {
    private Activity context;
    private FirebaseFirestore firestore;

    public SaveUserFirebase(Activity context) {
        this.context = context;
        firestore = FirebaseFirestore.getInstance();
    }
    public void saveNewUser(String uid, UserEntity userEntity, final FAuthCallback callback){
        firestore.collection("users").document(uid)
                .set(userEntity)
                .addOnCompleteListener(context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.onSuccessSave(task);

                    }
                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e);
            }
        });

    }
}
