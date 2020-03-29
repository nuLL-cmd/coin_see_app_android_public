package com.automatodev.coinSee.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NavUtils;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.API.AwesomeCallback;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.controller.service.API.AwesomeService;
import com.automatodev.coinSee.controller.service.ConvertDataService;
import com.automatodev.coinSee.view.component.ChartLine;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    public static boolean status;
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
    private ImageView imgUser_details;
    private TextView txtDate_details;
    private ConvertDataService convertDataService;
    private AwesomeService task;
    private ProgressBar progressChart_details;
    private RelativeLayout relativeChart_details;
    private Animation anim;
    private ChartLine chartLine;
    private LineChart lineChart;
    private CardView card;
    private ImageButton btnFullChart_details;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        task = new AwesomeService(this);
        txtCoinValue_details = findViewById(R.id.txtCoinValue_details);
        txtName_details = findViewById(R.id.txtName_details);
        txtHigh_details = findViewById(R.id.txtHigh_details);
        txtLow_details = findViewById(R.id.txtLow_details);
        txtPercent_details = findViewById(R.id.txtPercent_details);
        imgCode_details = findViewById(R.id.imgCode_details);
        imgCodeIn_details = findViewById(R.id.imgCodeIn_details);
        imgFav_details = findViewById(R.id.imgFav_details);
        imgUser_details = findViewById(R.id.imgUser_details);
        txtCode_details = findViewById(R.id.txtCode_details);
        txtDate_details = findViewById(R.id.txtDate_details);
        txtCodeIn_details = findViewById(R.id.txtCodeIn_details);
        progressChart_details = findViewById(R.id.progressChart_details);
        btnFullChart_details = findViewById(R.id.imgChart_details);
        relativeChart_details = findViewById(R.id.relativeChart_details);
        ThreeBounce three = new ThreeBounce();
        progressChart_details.setIndeterminateDrawable(three);
        convertDataService = new ConvertDataService();
        card = findViewById(R.id.card);
        anim = AnimationUtils.loadAnimation(this, R.anim.push_right);
        lineChart = findViewById(R.id.chart);
        getData();
    }

    public void getData() {
        final CoinChildr coinChildr = getIntent().getParcelableExtra("value");
        UserEntity userEntity = getIntent().getParcelableExtra("user");
        if (coinChildr != null && userEntity != null) {
            Glide.with(this).load(userEntity.getUserPhoto()).into(imgUser_details);
            txtCoinValue_details.setText(convertDataService.convertDecimal(coinChildr.getBid()));
            txtName_details.setText(coinChildr.getName());
            txtHigh_details.setText(coinChildr.getHigh());
            txtLow_details.setText(coinChildr.getLow());
            txtPercent_details.setText(coinChildr.getPctChange() + "%");
            if (Double.parseDouble(coinChildr.getPctChange()) < 0)
                txtPercent_details.setTextColor(Color.RED);
            else
                txtPercent_details.setTextColor(Color.parseColor("#43A047"));
            txtCode_details.setText(coinChildr.getCode());
            txtCodeIn_details.setText(coinChildr.getCodein());
            Glide.with(this).load(coinChildr.getUlrPhoto()).placeholder(R.drawable.ic_user_round).into(imgCode_details);
            imgCodeIn_details.setImageResource(R.drawable.brl);
            if (coinChildr.isFav())
                imgFav_details.setImageResource(R.drawable.ic_favorite_red_24dp);
            txtDate_details.setText(convertDataService.convertDate(coinChildr.getTimestamp().substring(0, 10)));
            getDataChart(coinChildr.getCode() + "-" + coinChildr.getCodein());
            btnFullChart_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!ChartActivity.status) {
                        Intent intent = new Intent(DetailsActivity.this, ChartActivity.class);
                        intent.putExtra("dataCoin", coinChildr.getName());
                        intent.putExtra("dataChart", coinChildr.getCode() + '-' + coinChildr.getCodein());
                        startActivity(intent);
                    }
                }
            });
        }
    }

    public void getDataChart(final String value) {
        task.requestRangeDays(value, new AwesomeCallback() {

            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onSucces(final List<CoinChildr> coinChildrList) throws InterruptedException {
                chartLine = new ChartLine(DetailsActivity.this, lineChart, coinChildrList);
                chartLine.makeGraph();
                card.setAnimation(anim);
                relativeChart_details.setVisibility(View.GONE);
                progressChart_details.setVisibility(View.GONE);
            }

            @Override
            public void onSucces(CoinChildr coinChildr0) throws InterruptedException {
            }
        });
    }

    public void actDetailsMain(View view) {
        NavUtils.navigateUpFromSameTask(DetailsActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        status = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        status = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
