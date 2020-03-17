package com.automatodev.coinSee.models.Firebase;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.automatodev.coinSee.controller.callback.FirestoreCallback;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class GetUserFirebase {
    private FirebaseFirestore firestore;
    private Activity context;

    public GetUserFirebase(Activity context) {
        this.context = context;
        firestore = FirebaseFirestore.getInstance();
    }

    public void getUserInFirebase(String uid, final FirestoreCallback firestoreCallback){
        firestore.collection("users").document(uid)
                .addSnapshotListener(context, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        firestoreCallback.onEventListener(documentSnapshot, e );
                    }
                });

    }
}
