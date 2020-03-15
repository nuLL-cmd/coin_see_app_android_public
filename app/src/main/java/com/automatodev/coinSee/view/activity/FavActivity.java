package com.automatodev.coinSee.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.service.CoinService;
import com.automatodev.coinSee.controller.service.ConvertData;
import com.automatodev.coinSee.controller.callback.RetrofitCallback;
import com.automatodev.coinSee.view.adapter.FavAdapter;
import com.automatodev.coinSee.view.component.ChartLine;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity {

    public static boolean status;
    private List<CoinChildr> coinChildrList;
    private RecyclerView recyclerFav_fav;
    private FavAdapter favAdapter;
    private ChartLine chartLine;
    private CoinService coinService;
    private ConvertData convertData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        recyclerFav_fav = findViewById(R.id.recyclerFav_fav);
        coinService = new CoinService(this);
        convertData = new ConvertData();
        showData();
    }

    private void showData() {
        recyclerFav_fav.hasFixedSize();
        recyclerFav_fav.setLayoutManager(new LinearLayoutManager(this));
        coinChildrList = new ArrayList<>();
        CoinChildr coinChildr = getIntent().getParcelableExtra("value");
        coinChildrList.add(coinChildr);
        if (coinChildrList.size() != 0) {
            favAdapter = new FavAdapter(this, coinChildrList);
            recyclerFav_fav.setAdapter(favAdapter);
            sClick();
        }
    }

    private void sClick() {
        favAdapter.setOnItemClickListener(new FavAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onIntemClick(int position) {
                favAdapter.notifyItemChanged(position);
                final BottomSheetDialog bt = new BottomSheetDialog(FavActivity.this, R.style.BottomSheetDialogTheme);
                final View view = getLayoutInflater().inflate(R.layout.layout_bottom_bar_fav, null);
                final Button btnClose_btFav = view.findViewById(R.id.btnClose_btFav);
                final LineChart chart = view.findViewById(R.id.chart_btFav);
                final TextView txtCoinValue_btFav = view.findViewById(R.id.txtCoinValue_btFav);
                final TextView txtName_btFav = view.findViewById(R.id.txtName_btFav);
                final TextView txtHigh_btFav = view.findViewById(R.id.txtHigh_btFav);
                final TextView txtLow_btFav = view.findViewById(R.id.txtLow_btFav);
                final TextView txtPercent_btFav = view.findViewById(R.id.txtPercent_btFav);
                final ImageView imgCode_btFav = view.findViewById(R.id.imgCode_btFav);
                final ImageView imgCodeIn_btFav = view.findViewById(R.id.imgCodeIn_btFav);
                final ImageView imgFav_btFav = view.findViewById(R.id.imgFav_btFav);
                final TextView txtCode_btFav = view.findViewById(R.id.txtCode_btFav);
                final TextView txtDate_btFav = view.findViewById(R.id.txtDate_btFav);
                final TextView txtCodeIn_btFav = view.findViewById(R.id.txtCodeIn_btFav);
                final ProgressBar progressDetails_btFav = view.findViewById(R.id.progressDetails_btFav);
                final TextView txtNameTitle_btFav = view.findViewById(R.id.txtNameTitle_btFav);
                final ProgressBar progressChart_btFav = view.findViewById(R.id.progressChart_btFav);

                coinService.requestSingle(coinChildrList.get(position).getCode() + "-" + coinChildrList.get(position).getCodein(), new RetrofitCallback() {
                    @Override
                    public void onSucces(List<CoinChildr> coinChildrList) throws InterruptedException {
                    }

                    @Override
                    public void onSucces(CoinChildr coinChildr0) throws InterruptedException {
                        txtCoinValue_btFav.setText(convertData.convertDecimal(coinChildr0.getBid()));
                        txtName_btFav.setText(coinChildr0.getName());
                        txtHigh_btFav.setText(coinChildr0.getHigh());
                        txtLow_btFav.setText(coinChildr0.getLow());
                        txtPercent_btFav.setText(coinChildr0.getPctChange() + "%");
                        txtCode_btFav.setText(coinChildr0.getCode());
                        txtDate_btFav.setText(convertData.convertDate(coinChildr0.getTimestamp()));
                        txtCodeIn_btFav.setText(coinChildr0.getCodein());
                        progressDetails_btFav.setVisibility(View.GONE);;
                        txtNameTitle_btFav.setText(coinChildr0.getName());
                    }
                });
                coinService.requestRangeDays(coinChildrList.get(position).getCode() + "-" + "BRL", new RetrofitCallback() {
                    @Override
                    public void onSucces(List<CoinChildr> coinChildrList) throws InterruptedException {
                        chartLine = new ChartLine(FavActivity.this, chart, coinChildrList);
                        chartLine.makeGraph();
                        progressChart_btFav.setVisibility(View.GONE);;
                    }

                    @Override
                    public void onSucces(CoinChildr coinChildr0) throws InterruptedException {
                    }
                });
                btnClose_btFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bt.dismiss();
                    }
                });
                bt.setContentView(view);
                bt.setCancelable(true);
                bt.show();
            }

            @Override
            public void onDeleteClick(int position) {
                coinChildrList.remove(position);
                favAdapter.notifyItemRemoved(position);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        status = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        status = false;
    }

    public void actFavMain(View view) {
        NavUtils.navigateUpFromSameTask(this);
    }
}
