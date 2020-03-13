package com.automatodev.coinsee.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.coinsee.R;
import com.automatodev.coinsee.controller.entidade.CoinChildr;
import com.automatodev.coinsee.view.adapter.FavAdapter;
import com.automatodev.coinsee.view.components.ChartLine;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity {

    public static boolean status;
    private List<CoinChildr> coinEntityList;
    private RecyclerView recyclerFav_fav;
    private FavAdapter favAdapter;
    private ChartLine chartLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        recyclerFav_fav = findViewById(R.id.recyclerFav_fav);

        showData();
        sClick();
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
    private void showData(){
        coinEntityList = new ArrayList<>();
        recyclerFav_fav.hasFixedSize();
        recyclerFav_fav.setLayoutManager(new LinearLayoutManager(this));

        coinEntityList.add(new CoinChildr("USA","BRL","Dolar Comercial",false));
        coinEntityList.add(new CoinChildr("USA","BRL","Dolar Comercial",false));
        coinEntityList.add(new CoinChildr("USA","BRL","Dolar Comercial",false));
        coinEntityList.add(new CoinChildr("USA","BRL","Dolar Comercial",false));
        coinEntityList.add(new CoinChildr("USA","BRL","Dolar Comercial",false));
        coinEntityList.add(new CoinChildr("USA","BRL","Dolar Comercial",false));
        coinEntityList.add(new CoinChildr("USA","BRL","Dolar Comercial",false));
        coinEntityList.add(new CoinChildr("USA","BRL","Dolar Comercial",false));
        coinEntityList.add(new CoinChildr("USA","BRL","Dolar Comercial",false));

        favAdapter = new FavAdapter(this, coinEntityList);
        recyclerFav_fav.setAdapter(favAdapter);

    }

    public void actFavMain(View view){
        NavUtils.navigateUpFromSameTask(this);
    }

    private void sClick(){
        favAdapter.setOnItemClickListener(new FavAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onIntemClick(int position) {
                favAdapter.notifyItemChanged(position);
                final BottomSheetDialog bt = new BottomSheetDialog(FavActivity.this, R.style.BottomSheetDialogTheme);
                View view = getLayoutInflater().inflate(R.layout.layout_bottom_bar_fav,null);
                Button btnClose_btFav = view.findViewById(R.id.btnClose_btFav);
                LineChart chart = view.findViewById(R.id.chart_btFav);
                chartLine = new ChartLine(FavActivity.this, chart, coinEntityList);
                chartLine.makeGraph();
                btnClose_btFav.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        bt.dismiss();

                    }
                });
                bt.setContentView(view);
                bt.setCancelable(true);
                bt.show();
            }

            @Override
            public void onDeleteClick(int position) {
                coinEntityList.remove(position);
                favAdapter.notifyItemRemoved(position);
            }
        });
    }

}
