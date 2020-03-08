package com.automatodev.coinsee.controller.service;

import android.app.Activity;
import android.util.Log;

import com.automatodev.coinsee.controller.entidade.CoinDaddy;
import com.automatodev.coinsee.controller.entidade.CoinChildr;
import com.automatodev.coinsee.models.RequestService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoinService {

    private Activity context;
    private Retrofit retrofit;
    private RequestService request;
    private String baseUrl = "https://economia.awesomeapi.com.br/";

    public CoinService(Activity context) {
        this.context = context;
    }

    public List<CoinChildr> requestAll(final RetrofitCallback retrofitCallback) {
        final List<CoinChildr> coinChildrList = new ArrayList<>();
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
        request = retrofit.create(RequestService.class);

        Call<CoinDaddy> call = request.requestAll();
        call.enqueue(new Callback<CoinDaddy>() {
            @Override
            public void onResponse(@NotNull Call<CoinDaddy> call, @NotNull Response<CoinDaddy> response) {
                CoinDaddy daddyTests = response.body();
                if (daddyTests != null) {
                    coinChildrList.add(daddyTests.getuSD());
                    coinChildrList.add(daddyTests.getaRS());
                    coinChildrList.add(daddyTests.getaUD());
                    coinChildrList.add(daddyTests.getbTC());
                    coinChildrList.add(daddyTests.getcAD());
                    coinChildrList.add(daddyTests.getcHF());
                    coinChildrList.add(daddyTests.getcNY());
                    coinChildrList.add(daddyTests.geteTH());
                    coinChildrList.add(daddyTests.geteUR());
                    coinChildrList.add(daddyTests.getgBP());
                    coinChildrList.add(daddyTests.getiLS());
                    coinChildrList.add(daddyTests.getjPY());
                    coinChildrList.add(daddyTests.getlTC());
                    coinChildrList.add(daddyTests.getxRP());
                    coinChildrList.add(daddyTests.getuSDT());
                }

                retrofitCallback.onSucces(coinChildrList);

            }

            @Override
            public void onFailure(@NotNull Call<CoinDaddy> call, @NotNull Throwable t) {
                Log.i("logx", "Error: " + t.getMessage(), t);
            }
        });

        return coinChildrList;
    }
}
