package com.automatodev.coinSee.controller.service.firebase;

import android.app.Activity;
import android.widget.Toast;

import com.automatodev.coinSee.controller.callback.firebase.FCoinCallback;
import com.automatodev.coinSee.controller.callback.firebase.FSaveCallback;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.models.firebase.FavCoinFirebase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavCoinService {
    private Activity context;
    private FavCoinFirebase favCoinFirebase;

    public FavCoinService(Activity context) {
        this.context = context;
        favCoinFirebase = new FavCoinFirebase(this.context);
    }

    public void getFavCoinService(String uid, final FCoinCallback callback){
        favCoinFirebase.getFavCoinFirebase(uid, new FCoinCallback() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                List<CoinChildr> favs = new ArrayList<>();
                if (task.isSuccessful()){
                   for (QueryDocumentSnapshot doc: task.getResult()){
                       favs.add(doc.toObject(CoinChildr.class));

                   }
                   callback.onCompleteReturn(favs);

                }
            }

            @Override
            public void onCompleteReturn(List<CoinChildr> list) {
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    public void delFavCoinService(String userUid, String coinUid){
        favCoinFirebase.delFavCoinFirebase(userUid, coinUid, new FSaveCallback() {
            @Override
            public void onSuccess(Task<Void> task) {
            }

            @Override
            public void onFailure(Exception e) {
            }
        });


    }
    public void addFavCoinService(String userUid, CoinChildr coinChildr){
        favCoinFirebase.addFavCoinFirebase(userUid, coinChildr, new FSaveCallback() {
            @Override
            public void onFailure(Exception e) {
            }

            @Override
            public void onSuccess(Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Item favoritado!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
