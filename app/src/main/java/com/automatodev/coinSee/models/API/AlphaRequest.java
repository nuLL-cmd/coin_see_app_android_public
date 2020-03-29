package com.automatodev.coinSee.models.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlphaRequest {

    //Criação dos endpoints para chamadas do tipo GET

    //Dados em tempo real de uma moeda para outra
    @GET("query?")
    Call<String> getSingle(@Query("function")String function, @Query("from_currency")String from,
                           @Query("to_currency")String to, @Query("apikey")String apikey);
    //Range de minutos limitado a 60 minutos
    @GET("query?")
    Call<String> getSingleRangeMin(@Query("function")String function,@Query("from_symbol")String from,
                                   @Query("to_symbol")String to,@Query("apikey")String apikey);
    //Range de dias limitado a 100 resultados por padrao no output=size
    @GET("query?")
    Call<String> getSingleRangeDay(@Query("function")String function,@Query("from_symbol")String from,
                                   @Query("to_symbol")String to, @Query("apikey")String apikey);


}
