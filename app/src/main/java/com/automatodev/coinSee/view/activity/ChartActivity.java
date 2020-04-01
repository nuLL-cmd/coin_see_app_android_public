package com.automatodev.coinSee.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.entity.CoinRangeEntityAlpha;
import com.automatodev.coinSee.view.component.ChartLine;
import com.github.mikephil.charting.charts.LineChart;

import java.util.List;


public class ChartActivity extends AppCompatActivity {
    public static boolean status;
    private ChartLine chartLine;
    private LineChart lineChart;
    private RelativeLayout relativeChart_chart;
    private ProgressBar progressChart_chart;
    private TextView txtTitleCoin_chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        lineChart = findViewById(R.id.lineChart_chart);
        relativeChart_chart = findViewById(R.id.relativeChart_chart);
        progressChart_chart = findViewById(R.id.progressChart_chart);
        txtTitleCoin_chart = findViewById(R.id.txtTitleCoin_chart);
        getData();
    }

    public void getData() {
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            List<CoinRangeEntityAlpha> l = (List<CoinRangeEntityAlpha>) bundle.getSerializable("list");
            txtTitleCoin_chart.setText(bundle.getString("dataCoin"));
            chartLine = new ChartLine(ChartActivity.this, lineChart, l, bundle.getString("dataCoin"));
            chartLine.makeGraph();
            relativeChart_chart.setVisibility(View.GONE);
            progressChart_chart.setVisibility(View.GONE);
        }
    }
    public void backDetailsActivity(View view){
        NavUtils.navigateUpFromSameTask(ChartActivity.this);
    }
    @Override
    protected void onStart(){
        super.onStart();
        status = true;

    }
    @Override
    protected void onStop(){
        super.onStop();
        status = false;
    }
}
