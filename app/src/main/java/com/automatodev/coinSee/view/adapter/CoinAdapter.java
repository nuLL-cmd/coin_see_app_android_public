package com.automatodev.coinSee.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.service.ConvertDataService;

import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.DataHandler> {
    private final Activity context;
    private List<CoinChildr> coinChildrs;
    private OnItemClickListener listener;
    private ConvertDataService convertDataService;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onFavItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CoinAdapter(List<CoinChildr> coinChildrs, Activity context) {
        this.context = context;
        this.coinChildrs = coinChildrs;
        this.convertDataService = new ConvertDataService();
    }

    @NonNull
    @Override
    public CoinAdapter.DataHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_celula_main, parent, false);
        return new DataHandler(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinAdapter.DataHandler holder, int position) {
        CoinChildr coinChildr = coinChildrs.get(position);
        holder.txtCodeIn_layout.setText(coinChildr.getCodein());
        holder.txtCode_layout.setText(coinChildr.getCode());
        holder.txtName_layout.setText(coinChildr.getName());
        holder.imgFav_layout.setImageResource(R.drawable.ic_favorite_border_32dp);
        holder.txtHigh_layout.setText(coinChildr.getHigh());
        holder.txtLow_layout.setText(coinChildr.getLow());
        holder.txtCoinValue_layout.setText(convertDataService.convertDecimal(coinChildr.getBid()));
        if (coinChildr.getTimestamp().length() > 10)
            holder.txtDate_layout.setText(convertDataService.convertDate(coinChildr.getTimestamp().substring(0, 10)));
        else
            holder.txtDate_layout.setText(convertDataService.convertDate(coinChildr.getTimestamp()));
        holder.txtPercent_layout.setText(coinChildr.getPctChange() + "%");
        holder.imgCode_layout.setImageResource(coinChildr.getUlrPhoto());
    }

    @Override
    public int getItemCount() {
        return coinChildrs.size();
    }

    public class DataHandler extends RecyclerView.ViewHolder {
        private TextView txtName_layout;
        private TextView txtCode_layout;
        private TextView txtCodeIn_layout;
        private ImageView imgFav_layout;
        private ImageView imgCode_layout;
        private TextView txtHigh_layout;
        private TextView txtLow_layout;
        private TextView txtCoinValue_layout;
        private TextView txtDate_layout;
        private TextView txtPercent_layout;

        public DataHandler(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtName_layout = itemView.findViewById(R.id.txtName_layout);
            txtCode_layout = itemView.findViewById(R.id.txtCode_layout);
            txtCodeIn_layout = itemView.findViewById(R.id.txtCodeIn_layout);
            imgFav_layout = itemView.findViewById(R.id.imgFav_layout);
            imgCode_layout = itemView.findViewById(R.id.imgCode_layout);
            txtHigh_layout = itemView.findViewById(R.id.txtHigh_layout);
            txtLow_layout = itemView.findViewById(R.id.txtLow_layout);
            txtCoinValue_layout = itemView.findViewById(R.id.txtCoinValue_layout);
            txtDate_layout = itemView.findViewById(R.id.txtDate_layout);
            txtPercent_layout = itemView.findViewById(R.id.txtPercent_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onItemClick(position);
                    }
                }
            });
            imgFav_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            listener.onFavItemClick(position);
                    }
                }
            });
        }
    }
}
