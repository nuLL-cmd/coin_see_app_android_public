package com.automatodev.coinSee.models.API;

import com.automatodev.coinSee.controller.entity.CoinChildr;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AwesomeRequest {


    @GET("all")
    Call<String> requestAll();

    @GET("all/{coin}")
    Call<String> requestSingle(@Path("coin")String coin);

    @GET("list/{coin}/{days}")
    Call<List<CoinChildr>> requestRangeDay(@Path("coin")String coin, @Path("days")int days);


}
