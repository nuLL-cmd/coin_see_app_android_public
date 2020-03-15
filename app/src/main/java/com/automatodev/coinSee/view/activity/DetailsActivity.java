package com.automatodev.coinSee.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.service.CoinService;
import com.automatodev.coinSee.controller.service.ConvertData;
import com.automatodev.coinSee.controller.callback.RetrofitCallback;
import com.automatodev.coinSee.view.component.ChartLine;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private TextView txtCoinValue_details;
    private TextView txtName_details;
    private TextView txtCode_details;
    private TextView txtCodeIn_details;
    private TextView txtHigh_details;
    private TextView txtLow_details;
    private TextView txtPercent_details;
    private ImageView imgCode_details;
    private ImageView imgCodeIn_details;
    private ImageView imgFav_details;
    private TextView txtDate_details;
    private TextView txtDateAtt_details;
    private  ConvertData convertData;
    private CoinService task;
    private ProgressBar progressChart_details;
    private ChartLine chartLine;
    private LineChart lineChart;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        task = new CoinService(this);

        txtCoinValue_details = findViewById(R.id.txtCoinValue_details);
        txtName_details = findViewById(R.id.txtName_details);
        txtHigh_details = findViewById(R.id.txtHigh_details);
        txtLow_details = findViewById(R.id.txtLow_details);
        txtPercent_details = findViewById(R.id.txtPercent_details);
        imgCode_details = findViewById(R.id.imgCode_details);
        imgCodeIn_details = findViewById(R.id.imgCodeIn_details);
        imgFav_details = findViewById(R.id.imgFav_details);
        txtCode_details =  findViewById(R.id.txtCode_details);
        txtDate_details = findViewById(R.id.txtDate_details);
        txtCodeIn_details = findViewById(R.id.txtCodeIn_details);
        txtDateAtt_details = findViewById(R.id.txtDateAtt_details);
        progressChart_details = findViewById(R.id.progressChart_details);
         lineChart = findViewById(R.id.chart);
        convertData = new ConvertData();

        getData();
    }

    public void getData() {

        CoinChildr coinChildr = getIntent().getParcelableExtra("value");
        if (coinChildr != null) {
        txtCoinValue_details.setText(convertData.convertDecimal(coinChildr.getBid()));
        txtName_details.setText(coinChildr.getName());
        txtHigh_details.setText(coinChildr.getHigh());
        txtLow_details.setText(coinChildr.getLow());
        txtPercent_details.setText(coinChildr.getPctChange()+"%");
        txtCode_details.setText(coinChildr.getCode());
        txtCodeIn_details.setText(coinChildr.getCodein());
        imgCode_details.setImageResource(coinChildr.getUlrPhoto());
        imgCodeIn_details.setImageResource(R.drawable.brl);
        imgFav_details.setImageResource(R.drawable.ic_favorite_red_24dp);
        if (coinChildr.getTimestamp().length() > 10)
            txtDate_details.setText(convertData.convertDate(coinChildr.getTimestamp().substring(0,10)));
        else
            txtDate_details.setText(convertData.convertDate(coinChildr.getTimestamp()));
        txtDateAtt_details.setText(coinChildr.getCreate_date());
        getDataChart(coinChildr.getCode()+"-"+coinChildr.getCodein());
        }

    }

    public void getDataChart(final String value){
        task.requestRangeDays(value, new RetrofitCallback() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onSucces(List<CoinChildr> coinChildrList) throws InterruptedException {
                chartLine = new ChartLine(DetailsActivity.this, lineChart, coinChildrList);
                chartLine.makeGraph();
                progressChart_details.setVisibility(View.GONE);

            }

            @Override
            public void onSucces(CoinChildr coinChildr0) throws InterruptedException {
            }
        });
    }

    public void actDetailsMain(View view){
        NavUtils.navigateUpFromSameTask(DetailsActivity.this);
    }
}
