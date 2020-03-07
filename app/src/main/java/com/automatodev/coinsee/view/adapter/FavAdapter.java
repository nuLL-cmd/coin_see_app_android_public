package com.automatodev.coinsee.view.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.coinsee.R;
import com.automatodev.coinsee.controller.CoinEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.DataHandler> {
    private Activity context;
    private List<CoinEntity> coinEntityList;
    private  OnItemClickListener listener;

    public interface OnItemClickListener{
        void onIntemClick(int position);
        void onDeleteClick(int position);
    }
    public FavAdapter(Activity context, List<CoinEntity> coinEntityList) {
        this.context = context;
        this.coinEntityList = coinEntityList;
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public DataHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = context.getLayoutInflater().inflate(R.layout.layout_celula_fav,parent, false);
       return new DataHandler(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHandler holder, int position) {
        CoinEntity coinEntity = coinEntityList.get(position);
        holder.txtName_layoutFav.setText(coinEntity.getName());
        holder.txtCode_layoutFav.setText(coinEntity.getCode());

        Glide.with(context).load(coinEntity.getUrlCoin())
                .placeholder(R.drawable.usa)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgCoin_layoutFav);

    }

    @Override
    public int getItemCount() {
        return coinEntityList.size();
    }

    public class DataHandler extends RecyclerView.ViewHolder {
        private TextView txtName_layoutFav;
        private TextView txtCode_layoutFav;
        private ImageView imgCoin_layoutFav;
        private ImageButton btnDelete_layoutFav;

        public DataHandler(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            txtName_layoutFav = itemView.findViewById(R.id.txtName_layoutFav);
            txtCode_layoutFav = itemView.findViewById(R.id.txtCode_layoutFav);
            imgCoin_layoutFav = itemView.findViewById(R.id.imgCoin_layoutFav);
            btnDelete_layoutFav = itemView.findViewById(R.id.btnDelete_layoutFav);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){

                            listener.onIntemClick(position);
                        }
                    }
                }
            });

            btnDelete_layoutFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }
    }
}
