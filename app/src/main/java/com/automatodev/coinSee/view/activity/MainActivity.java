package com.automatodev.coinSee.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.service.CoinService;
import com.automatodev.coinSee.controller.callback.RetrofitCallback;
import com.automatodev.coinSee.view.adapter.CoinAdapter;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    public static boolean status;
    private Animation animation;
    private ProgressBar progressChart_main;
    private RecyclerView recyclerCoin_main;
    private CoinAdapter coinAdapter;
    private List<CoinChildr> coinChildrList;
    private CoinService task;
    private Integer[] resources = {R.drawable.usd,R.drawable.ars,R.drawable.aud ,R.drawable.btc
            ,R.drawable.cad,R.drawable.chf,R.drawable.cny,R.drawable.eth,R.drawable.eur
            ,R.drawable.gbp,R.drawable.ils,R.drawable.jpy,R.drawable.ltc,R.drawable.xrp,R.drawable.usd};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerCoin_main = findViewById(R.id.recyclerCoin_main);
        progressChart_main = findViewById(R.id.progressChart_main);
        ThreeBounce three = new ThreeBounce();
        progressChart_main.setIndeterminateDrawable(three);
        task = new CoinService(this);
        animation = AnimationUtils.loadAnimation(this, R.anim.push_right);

        getData();
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

    private void getData() {
        coinChildrList = new ArrayList<>();
        recyclerCoin_main.hasFixedSize();
        recyclerCoin_main.setLayoutManager(new LinearLayoutManager(this));
        coinChildrList = task.requestAll(new RetrofitCallback() {
            @Override
            public void onSucces(List<CoinChildr> coinChildrList) {
                for (int i = 0; i <coinChildrList.size(); i++)
                    coinChildrList.get(i).setUlrPhoto(resources[i]);
                coinAdapter = new CoinAdapter(coinChildrList, MainActivity.this);
                recyclerCoin_main.setAdapter(coinAdapter);
                progressChart_main.setVisibility(View.GONE);
                recyclerCoin_main.setAnimation(animation);
                sClick();
            }

            @Override
            public void onSucces(CoinChildr coinChildr0) throws InterruptedException {
            }
        });
    }

    public void actMainProfile(View view) {
        if (!ProfileActivity.status) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
    }

    public void actMainFav(View view) {
        if (!FavActivity.status) {
            Intent intent = new Intent(this, FavActivity.class);
            intent.putExtra("value",coinChildrList.get(0));
            startActivity(intent);
        }
    }

    private void sClick() {
        coinAdapter.setOnItemClickListener(new CoinAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                coinAdapter.notifyItemChanged(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("value",coinChildrList.get(position));
                startActivity(intent);
            }

            @Override
            public void onFavItemClick(int position) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
