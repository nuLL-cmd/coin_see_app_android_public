package com.automatodev.coinsee.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.automatodev.coinsee.R;
import com.automatodev.coinsee.controller.CoinEntity;
import com.automatodev.coinsee.view.adapter.CoinAdapter;
import com.automatodev.coinsee.view.adapter.FavAdapter;

import java.util.ArrayList;

public class FavActivity extends AppCompatActivity {

    public static boolean status;
    private ArrayList<CoinEntity> coinEntityList;
    private RecyclerView recyclerFav_fav;
    private FavAdapter favAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        recyclerFav_fav = findViewById(R.id.recyclerFav_fav);

        showData();
        onClick();
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

        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));

        favAdapter = new FavAdapter(this, coinEntityList);
        recyclerFav_fav.setAdapter(favAdapter);

    }

    public void actFavMain(View view){
        NavUtils.navigateUpFromSameTask(this);
    }

    public void onClick(){
        favAdapter.setOnItemClickListener(new FavAdapter.OnItemClickListener() {
            @Override
            public void onIntemClick(int position) {
                favAdapter.notifyItemChanged(position);
                Intent intent = new Intent(FavActivity.this, DetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                coinEntityList.remove(position);
                favAdapter.notifyItemRemoved(position);
            }
        });
    }
}
