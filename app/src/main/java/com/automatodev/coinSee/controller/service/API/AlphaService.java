package com.automatodev.coinSee.controller.service.API;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.API.AlphaCallback;
import com.automatodev.coinSee.controller.entity.CoinEntityAlpha;
import com.automatodev.coinSee.controller.entity.CoinRangeEntityAlpha;
import com.automatodev.coinSee.models.API.AlphaRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AlphaService {

    private Activity context;
    private AlphaRequest alphaRequest;
    private Retrofit retrofit;
    private String INTRADAY = "FX_INTRADAY";
    private String DAILY = "FX_DAILY";
    private String CURRENCY_RATE = "CURRENCY_EXCHANGE_RATE";
    private String APIKEY = "H7CA8PZAVE3IJ82V";
    private String baseUrl = "https://www.alphavantage.co/";

    public AlphaService(Activity context) {
        this.context = context;
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        alphaRequest = retrofit.create(AlphaRequest.class);
    }
    //###########################################
    //Ssrviço que manipulara os resultados da consulta singular e devolverar em forma de tipo para a view.
    public void getSingleService(String from, String to, final AlphaCallback callback) {
        Call<String> call = alphaRequest.getSingle(CURRENCY_RATE, from, to, APIKEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        CoinEntityAlpha coinEntityAlpha = extractItem(response.body());
                        callback.onSuccessSingle(coinEntityAlpha);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
                final AlertDialog alerta  = new AlertDialog.Builder(context).create();
                Objects.requireNonNull(alerta.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
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
            }
        });
    }
    //###########################################
    //Ssrviço que manipulara os resultados do intervalo de minutos e devolverar em forma de tipo para a view.
    public void getSingleMinService(final String interval, String from, String to, final AlphaCallback callback){
        Call<String> call =  alphaRequest.getSingleIntervalMin(INTRADAY,from,to,interval,APIKEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if (response.isSuccessful()){
                    if(response.body() != null){

                        List<CoinRangeEntityAlpha> alphaList = extractRange(response.body(),interval);
                        callback.onSuccessRange(alphaList);
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
            }
        });

    }
    //###########################################
    //Ssrviço que manipulara os resultados do intervalo de minutos e devolverar em forma de tipo para a view.
    public void getSingleDayService(String from, String to, final AlphaCallback callback){
        Call<String> call = alphaRequest.getSingleRangeDay(DAILY,from,to,APIKEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {

                       List<CoinRangeEntityAlpha> alphaList =  extractRange(response.body(),"Daily");
                       callback.onSuccessRange(alphaList);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {
            }
        });

    }
    //###########################################
    //Metodo que fara a extração de um unico objeto do JSONbjeto, sera chamado de um serviço de chamada singular
    private CoinEntityAlpha extractItem(String response) {
        CoinEntityAlpha coinEntity = new CoinEntityAlpha();
        try {
            JSONObject obj = new JSONObject(response.trim());
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                coinEntity.setFromCode(((JSONObject) obj.get(key)).getString("1. From_Currency Code"));
                coinEntity.setFromName(((JSONObject) obj.get(key)).getString("2. From_Currency Name"));
                coinEntity.setToCode(((JSONObject) obj.get(key)).getString("3. To_Currency Code"));
                coinEntity.setToName(((JSONObject) obj.get(key)).getString("4. To_Currency Name"));
                coinEntity.setRate(((JSONObject) obj.get(key)).getString("5. Exchange Rate"));
                coinEntity.setLastRefresh(((JSONObject) obj.get(key)).getString("6. Last Refreshed"));
                coinEntity.setTimeZone(((JSONObject) obj.get(key)).getString("7. Time Zone"));
                coinEntity.setBid(((JSONObject) obj.get(key)).getString("8. Bid Price"));
                coinEntity.setAsk(((JSONObject) obj.get(key)).getString("9. Ask Price"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return coinEntity;
    }
    //###########################################
    //Metodo que trara a lista de dados do range de dias ou horas
    private List<CoinRangeEntityAlpha> extractRange(String response, String range) {
        List<CoinRangeEntityAlpha> rangeList = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(response.trim());
            String resFilter = obj.getString("Time Series FX(" + range + ")");
            obj = new JSONObject(resFilter);
            Iterator<String> keys = obj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                rangeList.add(new CoinRangeEntityAlpha(
                        key,
                        ((JSONObject) obj.get(key)).getString("1. open"),
                        ((JSONObject) obj.get(key)).getString("2. high"),
                        ((JSONObject) obj.get(key)).getString("3. low"),
                        ((JSONObject) obj.get(key)).getString("4. close")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rangeList;
    }
}
