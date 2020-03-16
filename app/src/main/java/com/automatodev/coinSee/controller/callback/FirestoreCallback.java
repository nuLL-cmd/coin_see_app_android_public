package com.automatodev.coinSee.controller.callback;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

public interface FirestoreCallback {

    void onEventListener(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e);
}
