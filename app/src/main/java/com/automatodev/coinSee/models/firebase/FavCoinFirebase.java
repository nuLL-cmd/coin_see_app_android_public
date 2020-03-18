package com.automatodev.coinSee.models.firebase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.automatodev.coinSee.controller.callback.FCoinCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FavCoinFirebase {
    private Activity context;
    private FirebaseFirestore firestore;
    public FavCoinFirebase(Activity context) {
        this.context = context;
        firestore = FirebaseFirestore.getInstance();
    }

    public void getFavCoinFirebase(String uid, final FCoinCallback callback){
        firestore.collection("users").document(uid)
                .collection("favorites")
                .get().addOnCompleteListener(context, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                callback.onComplete(task);
            }
        }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
