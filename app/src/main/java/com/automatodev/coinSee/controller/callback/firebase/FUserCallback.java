package com.automatodev.coinSee.controller.callback.firebase;

import com.automatodev.coinSee.controller.entity.UserEntity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

public interface FUserCallback {

    void onEventListener(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e);
    void onSuccess(UserEntity userEntity);
}
