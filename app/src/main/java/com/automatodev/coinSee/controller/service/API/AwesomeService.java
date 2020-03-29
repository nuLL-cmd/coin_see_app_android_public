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
import com.automatodev.coinSee.controller.entity.CoinDaddy;
import com.automatodev.coinSee.models.API.AwesomeRequest;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AwesomeService {

    private Activity context;
    private Retrofit retrofit;
    private AwesomeRequest request;
    private String baseUrl = "https://economia.awesomeapi.com.br/";
    private SwipeRefreshLayout sw;

    public AwesomeService(Activity context) {
        this.context = context;
        sw = context.findViewById(R.id.swipte_main);
    }

    public void requestAll(final AwesomeCallback callback) {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
        request = retrofit.create(AwesomeRequest.class);
        Call<CoinDaddy> call = request.requestAll();
        call.enqueue(new Callback<CoinDaddy>() {
            @Override
            public void onResponse(@NotNull Call<CoinDaddy> call, @NotNull Response<CoinDaddy> response) {
                CoinDaddy daddyTests = response.body();
                List<CoinChildr> coinChildrList = new ArrayList<>();
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
                    try {
                        callback.onSucces(coinChildrList);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CoinDaddy> call, @NotNull Throwable t) {
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
                sw.setRefreshing(false);
            }
        });

    }

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
    }

    public void requestSingle(final String value, final AwesomeCallback callback) {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(AwesomeRequest.class);
        Call<CoinDaddy> call = request.requestSingle(value);
        call.enqueue(new Callback<CoinDaddy>() {
            @Override
            public void onResponse(@NotNull Call<CoinDaddy> call, @NotNull Response<CoinDaddy> response) {
                CoinDaddy daddyTests = response.body();
                List<CoinChildr> coinChildrList = new ArrayList<>();
                CoinChildr cSingle = null;
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
                    for (CoinChildr c : coinChildrList) {
                        if (c != null)
                            cSingle = c;
                    }
                    try {
                        callback.onSucces(cSingle);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CoinDaddy> call, @NotNull Throwable t) {
                Log.e("logx", "ErrorSingle: " + t.getMessage(), t);
            }
        });
    }
}
