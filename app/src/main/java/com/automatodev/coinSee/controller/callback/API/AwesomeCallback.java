package com.automatodev.coinSee.controller.callback.API;

import com.automatodev.coinSee.controller.entity.CoinChildr;

import java.util.List;

public interface AwesomeCallback {

    void onSucces(List<CoinChildr> coinChildrList) throws InterruptedException;
    void onSucces(CoinChildr coinChildr0) throws InterruptedException;
}
