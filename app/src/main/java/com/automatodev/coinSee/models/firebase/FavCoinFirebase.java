package com.automatodev.coinSee.models.firebase;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.automatodev.coinSee.controller.callback.FAuthCallback;
import com.automatodev.coinSee.controller.callback.FCoinCallback;
import com.automatodev.coinSee.controller.entity.CoinChildr;
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

    public void delFavCoinFirebase(String userUid, String coinUid, final FAuthCallback callback){
        firestore.collection("users").document(userUid).collection("favorites")
                .document(coinUid)
                .delete()
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

    public void addFavCoinFirebase(String userUid, CoinChildr coinChildr, final FAuthCallback callback){
        firestore.collection("users").document(userUid)
                .collection("favorites")
                .document(coinChildr.getCoinUid())
               .set(coinChildr)
                .addOnCompleteListener(context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.onSuccessSave(task);;
                    }
                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e);
            }
        });
    }

}
