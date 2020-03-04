package com.automatodev.coinsee.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.coinsee.R;
import com.automatodev.coinsee.controller.CoinEntity;

import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.DataHandler>{
    private List<CoinEntity> coinEntityList;
    private Activity context;

    public CoinAdapter(List<CoinEntity> coinEntityList, Activity context) {
        this.coinEntityList = coinEntityList;
        this.context = context;
    }

    @NonNull
    @Override
    public CoinAdapter.DataHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = context.getLayoutInflater().inflate(R.layout.layout_celula_main,parent, false);
        return new DataHandler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinAdapter.DataHandler holder, int position) {
        CoinEntity coinEntity = coinEntityList.get(position);
        holder.txtCodeIn_layout.setText(coinEntity.getCodein());
        holder.txtCode_layout.setText(coinEntity.getCode());
        holder.txtName_layout.setText(coinEntity.getName());

        if (coinEntity.getFav())
            holder.imgFav_layout.setImageResource(R.drawable.ic_favorite_red_24dp);
        else
            holder.imgFav_layout.setImageResource(R.drawable.ic_favorite_border_32dp);
    }

    @Override
    public int getItemCount() {
        return coinEntityList.size();
    }

    public class DataHandler extends RecyclerView.ViewHolder {
        private TextView txtName_layout;
        private TextView txtCode_layout;
        private TextView txtCodeIn_layout;
        private ImageView imgFav_layout;

        public DataHandler(@NonNull View itemView) {
            super(itemView);

            txtName_layout = itemView.findViewById(R.id.txtName_layout);
            txtCode_layout = itemView.findViewById(R.id.txtCode_layout);
            txtCodeIn_layout = itemView.findViewById(R.id.txtCodeIn_layout);
            imgFav_layout = itemView.findViewById(R.id.imgFav_layout);
        }
    }
}
