package com.automatodev.coinSee.controller.service;

import android.app.Activity;

import com.automatodev.coinSee.controller.callback.FirestoreCallback;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.models.Firebase.GetUserFirebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class GetUserService {
    private Activity context;
    private GetUserFirebase getUserFirebase;
    private UserEntity userEntity;
    public GetUserService(Activity context) {
        this.context = context;
        getUserFirebase = new GetUserFirebase(this.context);
    }

    public void serviceGetUser(String uid, final FirestoreCallback firestoreCallback){

        getUserFirebase.getUserInFirebase(uid, new FirestoreCallback() {
            @Override
            public void onEventListener(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                userEntity = documentSnapshot.toObject(UserEntity.class);
                firestoreCallback.onEventSucccess(userEntity);
            }

            @Override
            public void onEventSucccess(UserEntity userEntity) {
            }
        });
    }

}
