package com.automatodev.coinsee.models;

import com.automatodev.coinsee.controller.entidade.CoinChildr;
import com.automatodev.coinsee.controller.entidade.CoinDaddy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestService {


    @GET("all")
    Call<CoinDaddy> requestAll();

    @GET("all/{coin}")
    Call<CoinDaddy> requestSingle(@Path("coin")String coin);

    @GET("list/{coin}/{days}")
    Call<List<CoinChildr>> requestRangeDay(@Path("coin")String coin, @Path("days")int days);


}
