package com.automatodev.coinSee.controller.service.firebase;

import android.app.Activity;

import com.automatodev.coinSee.controller.callback.FUserCallback;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.models.firebase.GetUserFirebase;
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

    public void serviceGetUser(String uid, final FUserCallback FUserCallback){

        getUserFirebase.getUserInFirebase(uid, new FUserCallback() {
            @Override
            public void onEventListener(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                userEntity = documentSnapshot.toObject(UserEntity.class);
                FUserCallback.onEventSucccess(userEntity);
            }

            @Override
            public void onEventSucccess(UserEntity userEntity) {
            }
        });
    }

}
