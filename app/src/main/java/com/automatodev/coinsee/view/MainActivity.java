package com.automatodev.coinsee.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.automatodev.coinsee.R;
import com.automatodev.coinsee.controller.CoinEntity;
import com.automatodev.coinsee.view.adapter.CoinAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static boolean status;
    private RecyclerView recyclerCoin_main;
    private CoinAdapter coinAdapter;
    private List<CoinEntity> coinEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerCoin_main = findViewById(R.id.recyclerCoin_main);

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
        recyclerCoin_main.hasFixedSize();
        recyclerCoin_main.setLayoutManager(new LinearLayoutManager(this));

        coinEntityList.add(new CoinEntity("USA","BRL","Dolar Comercial",null,false));
        coinEntityList.add(new CoinEntity("USA","BRL","Dolar Comercial",null,false));
        coinEntityList.add(new CoinEntity("USA","BRL","Dolar Comercial",null,false));
        coinEntityList.add(new CoinEntity("USA","BRL","Dolar Comercial",null,false));
        coinEntityList.add(new CoinEntity("USA","BRL","Dolar Comercial",null,false));
        coinEntityList.add(new CoinEntity("USA","BRL","Dolar Comercial",null,false));
        coinEntityList.add(new CoinEntity("USA","BRL","Dolar Comercial",null,false));
        coinEntityList.add(new CoinEntity("USA","BRL","Dolar Comercial",null,false));
        coinEntityList.add(new CoinEntity("USA","BRL","Dolar Comercial",null,false));

        coinAdapter = new CoinAdapter(coinEntityList, this);
        recyclerCoin_main.setAdapter(coinAdapter);

    }

    public void actMainProfile(View view){
        if(!ProfileActivity.status){
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);

        }
    }

    public void actMainFav(View view){
        if(!FavActivity.status){
            Intent intent = new Intent(this, FavActivity.class);
            startActivity(intent);

        }
    }

    private void sClick(){
        coinAdapter.setOnItemClickListener(new CoinAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                coinAdapter.notifyItemChanged(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFavItemClick(int position) {



            }
        });
    }

}
