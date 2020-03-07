package com.automatodev.coinsee.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.coinsee.R;
import com.automatodev.coinsee.controller.CoinEntity;
import com.github.mikephil.charting.formatter.IFillFormatter;

import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.DataHandler>{
    private List<CoinEntity> coinEntityList;
    private Activity context;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onFavItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public CoinAdapter(List<CoinEntity> coinEntityList, Activity context) {
        this.coinEntityList = coinEntityList;
        this.context = context;
    }

    @NonNull
    @Override
    public CoinAdapter.DataHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = context.getLayoutInflater().inflate(R.layout.layout_celula_main,parent, false);
        return new DataHandler(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinAdapter.DataHandler holder, int position) {
        CoinEntity coinEntity = coinEntityList.get(position);
        holder.txtCodeIn_layout.setText(coinEntity.getCodein());
        holder.txtCode_layout.setText(coinEntity.getCode());
        holder.txtName_layout.setText(coinEntity.getName());
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

        public DataHandler(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            txtName_layout = itemView.findViewById(R.id.txtName_layout);
            txtCode_layout = itemView.findViewById(R.id.txtCode_layout);
            txtCodeIn_layout = itemView.findViewById(R.id.txtCodeIn_layout);
            imgFav_layout = itemView.findViewById(R.id.imgFav_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onItemClick(position);
                    }
                }
            });
            imgFav_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onFavItemClick(position);
                    }
                }
            });
        }
    }

}
