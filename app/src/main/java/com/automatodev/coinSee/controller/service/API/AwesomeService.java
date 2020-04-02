package com.automatodev.coinSee.controller.service.API;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.API.AwesomeCallback;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.models.API.AwesomeRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AwesomeService {

    private Activity context;
    private Retrofit retrofit;
    private AwesomeRequest request;
    private String baseUrl = "https://economia.awesomeapi.com.br/";

    public AwesomeService(Activity context) {
        this.context = context;

    }
    public void requestAll(final SwipeRefreshLayout sw, final AwesomeCallback callback) {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        request = retrofit.create(AwesomeRequest.class);
        Call<String> call = request.requestAll();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if (response.isSuccessful()){
                    if (response.body()!= null){
                        List<CoinChildr> coinChildrList = extractAll(response.body());
                        try {
                            callback.onSucces(coinChildrList);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                final AlertDialog alerta  = new AlertDialog.Builder(context).create();
                alerta.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                View v = context.getLayoutInflater().inflate(R.layout.layout_message,null);
                TextView txtTitle_message = v.findViewById(R.id.txtTitle_message);
                TextView txtLabel_message = v.findViewById(R.id.txtLabel_message);

                Button btnDismiss_message = v.findViewById(R.id.btnDismiss_message);
                txtTitle_message.setText("Ops!");
                txtLabel_message.setText("Não foi possivel conectar a ao serviço de informações\nPor favor verifique sua conexão e tente novamente.");
                btnDismiss_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alerta.dismiss();
                    }
                });
                alerta.setView(v);
                alerta.show();
                sw.setRefreshing(false);;
            }
        });

    }
/*
    public void requestRangeDays(String value, final AwesomeCallback callback) {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        request = retrofit.create(AwesomeRequest.class);
        Call<List<CoinChildr>> call = request.requestRangeDay(value, 14);
        call.enqueue(new Callback<List<CoinChildr>>() {
            @Override
            public void onResponse(Call<List<CoinChildr>> call, Response<List<CoinChildr>> response) {
                List<CoinChildr> childrsAux = response.body();
                List<CoinChildr> childerList = new ArrayList<>();
                if (childrsAux != null) {
                    childerList.addAll(childrsAux);
                    try {
                        callback.onSucces(childerList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<CoinChildr>> call, @NotNull Throwable t) {
                Log.e("logx", "Error: " + t.getMessage(), t);
            }
        });
    }*/
    public void requestSingle(final String value, final AwesomeCallback callback) {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(ScalarsConverterFactory.create())
                .build();
        request = retrofit.create(AwesomeRequest.class);
        Call<String> call = request.requestSingle(value);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        CoinChildr coinChildr = extractSingle(response.body());
                        try {
                            callback.onSucces(coinChildr);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                Log.e("logx", "ErrorSingle: " + t.getMessage(), t);
            }
        });
    }
    public void requestRangeDays(String value, final AwesomeCallback callback){
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(AwesomeRequest.class);
        Call<List<CoinChildr>> call = request.requestRangeDay(value,14);
        call.enqueue(new Callback<List<CoinChildr>>() {
            @Override
            public void onResponse(Call<List<CoinChildr>> call, Response<List<CoinChildr>> response) {
                if(response.isSuccessful()){
                    if (response.body() != null){
                        List<CoinChildr> coinChildrs = response.body();
                        try {
                            callback.onSucces(coinChildrs);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CoinChildr>> call, Throwable t) {
            }
        });
    }
    private List<CoinChildr> extractAll(String response){
        List<CoinChildr> listCoinChildr = new ArrayList<>();
        try{
            JSONObject obj = new JSONObject(response.trim());
            Iterator<String> keys = obj.keys();
            while(keys.hasNext()){
                String key = keys.next();
                listCoinChildr.add(new CoinChildr(
                        ((JSONObject)obj.get(key)).get("ask").toString(),
                        ((JSONObject)obj.get(key)).get("bid").toString(),
                        ((JSONObject)obj.get(key)).get("code").toString(),
                        ((JSONObject)obj.get(key)).get("codein").toString(),
                        ((JSONObject)obj.get(key)).get("create_date").toString(),
                        ((JSONObject)obj.get(key)).get("high").toString(),
                        ((JSONObject)obj.get(key)).get("low").toString(),
                        ((JSONObject)obj.get(key)).get("name").toString(),
                        ((JSONObject)obj.get(key)).get("pctChange").toString(),
                        ((JSONObject)obj.get(key)).get("timestamp").toString(),
                        ((JSONObject)obj.get(key)).get("varBid").toString()
                        ));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return listCoinChildr;
    }

    private CoinChildr extractSingle(String response){
        CoinChildr coinChildr = null;
        try{
            JSONObject obj = new JSONObject(response.trim());
            Iterator<String> keys = obj.keys();
            while(keys.hasNext()){
                String key = keys.next();
                coinChildr = new CoinChildr(
                        ((JSONObject)obj.get(key)).get("ask").toString(),
                        ((JSONObject)obj.get(key)).get("bid").toString(),
                        ((JSONObject)obj.get(key)).get("code").toString(),
                        ((JSONObject)obj.get(key)).get("codein").toString(),
                        ((JSONObject)obj.get(key)).get("create_date").toString(),
                        ((JSONObject)obj.get(key)).get("high").toString(),
                        ((JSONObject)obj.get(key)).get("low").toString(),
                        ((JSONObject)obj.get(key)).get("name").toString(),
                        ((JSONObject)obj.get(key)).get("pctChange").toString(),
                        ((JSONObject)obj.get(key)).get("timestamp").toString(),
                        ((JSONObject)obj.get(key)).get("varBid").toString()
                );
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
        return coinChildr;
    }
}
