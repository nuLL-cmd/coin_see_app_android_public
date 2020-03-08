package com.automatodev.coinsee.controller.service;

import com.automatodev.coinsee.controller.entidade.CoinChildr;

import java.util.List;

public interface RetrofitCallback {
    void onSucces(List<CoinChildr> coinChildrList);
}
