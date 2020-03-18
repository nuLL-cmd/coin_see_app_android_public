package com.automatodev.coinSee.controller.callback;

import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public interface FCoinCallback {
    void onComplete(Task<QuerySnapshot> task);
    void onCompleteReturn(List<CoinChildr>list);
    void onFailure(Exception e);
}
