package com.automatodev.coinsee.models;

import com.automatodev.coinsee.controller.entidade.CoinDaddy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestService {


    @GET("all")
    Call<CoinDaddy> requestAll();
    
    @GET("all/{coin}")
    Call<CoinDaddy> requestSingle(@Path("coin")String coin);
    
    @GET("all/{coin}/{days}")
    Call<CoinDaddy> requestRangeDay(@Path("coin")String coin, @Path("days")int days);


}
