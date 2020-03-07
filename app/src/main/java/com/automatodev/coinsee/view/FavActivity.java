package com.automatodev.coinsee.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.automatodev.coinsee.R;
import com.automatodev.coinsee.controller.CoinEntity;
import com.automatodev.coinsee.view.adapter.FavAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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

    private void sClick(){
        favAdapter.setOnItemClickListener(new FavAdapter.OnItemClickListener() {
            @Override
            public void onIntemClick(int position) {
                favAdapter.notifyItemChanged(position);
                final BottomSheetDialog bt = new BottomSheetDialog(FavActivity.this, R.style.BottomSheetDialogTheme);
                View view = getLayoutInflater().inflate(R.layout.layout_bottom_bar_fav,null);
                Button btnDetails_btFav = view.findViewById(R.id.btnDetails_btFav);
                ImageButton imgClose_btFav = view.findViewById(R.id.imgClose_btFav);
                btnDetails_btFav.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(FavActivity.this, DetailsActivity.class);
                        DetailsActivity.type = 1;
                        bt.dismiss();
                        startActivity(intent);

                    }
                });
                imgClose_btFav.setOnClickListener(new View.OnClickListener() {
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
                coinEntityList.remove(position);
                favAdapter.notifyItemRemoved(position);
            }
        });
    }
}
