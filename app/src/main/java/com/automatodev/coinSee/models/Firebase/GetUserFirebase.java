package com.automatodev.coinSee.models.Firebase;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.automatodev.coinSee.controller.callback.FirestoreCallback;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class GetUserFirebase {
    private FirebaseFirestore firestore;
    private Activity context;

    public GetUserFirebase(Activity context) {
        this.context = context;
    }

    public void getUserFirebase(String uid, final FirestoreCallback firestoreCallback){
        UserEntity userEntity = new UserEntity();
        firestore.collection("users").document("uid")
                .addSnapshotListener(context, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        firestoreCallback.onEventListener(documentSnapshot, e );
                    }
                });

    }
}
