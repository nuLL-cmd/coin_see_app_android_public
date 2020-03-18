package com.automatodev.coinSee.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.automatodev.coinSee.controller.callback.FCoinCallback;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.controller.service.API.CoinService;
import com.automatodev.coinSee.controller.service.ConvertDataService;
import com.automatodev.coinSee.controller.callback.RetrofitCallback;
import com.automatodev.coinSee.controller.service.firebase.FavCoinService;
import com.automatodev.coinSee.view.adapter.FavAdapter;
import com.automatodev.coinSee.view.component.ChartLine;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavActivity extends AppCompatActivity {

    public static boolean status;
    private RecyclerView recyclerFav_fav;
    private FavAdapter favAdapter;
    private ChartLine chartLine;
    private CoinService coinService;
    private Animation animation;
    private ConvertDataService convertDataService;
    private CircleImageView imgUser_fav;
    private FavCoinService favCoinService;
    private ProgressBar progressFav_fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        recyclerFav_fav = findViewById(R.id.recyclerFav_fav);
        imgUser_fav = findViewById(R.id.imgUser_fav);

        coinService = new CoinService(this);
        convertDataService = new ConvertDataService();
        favCoinService = new FavCoinService(this);
        progressFav_fav = findViewById(R.id.progressFav_fav);

        favAdapter = new FavAdapter(this, null);
        animation = AnimationUtils.loadAnimation(this, R.anim.push_left);
        ThreeBounce three = new ThreeBounce();
        progressFav_fav.setIndeterminateDrawable(three);
        recyclerFav_fav.hasFixedSize();
        recyclerFav_fav.setLayoutManager(new LinearLayoutManager(this));



            UserEntity userEntity = getIntent().getParcelableExtra("user");
            Glide.with(this).load(userEntity.getUserPhoto()).placeholder(R.drawable.ic_user_round).into(imgUser_fav);
            getDataFav(userEntity.getUserUid());

       
    }

    private void getDataFav(String uid) {
            favCoinService.getFavCoinService(uid, new FCoinCallback() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                }

                @Override
                public void onCompleteReturn(List<CoinChildr> list) {
                    favAdapter.setCoinEntityList(list);
                    recyclerFav_fav.setAdapter(favAdapter);
                    progressFav_fav.setVisibility(View.GONE);
                    recyclerFav_fav.setAnimation(animation);
                    sClick(list);
                }

                @Override
                public void onFailure(Exception e) {
                }
            });


    }

    private void sClick(final List<CoinChildr> coinChildrList) {
        favAdapter.setOnItemClickListener(new FavAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onIntemClick(final int position) {
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
                final ImageView imgCodeTitle_btFav = view.findViewById(R.id.imgCodeTitle_btFav);
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
                        txtCoinValue_btFav.setText(convertDataService.convertDecimal(coinChildr0.getBid()));
                        txtName_btFav.setText(coinChildr0.getName());
                        txtHigh_btFav.setText(coinChildr0.getHigh());
                        txtLow_btFav.setText(coinChildr0.getLow());
                        txtPercent_btFav.setText(coinChildr0.getPctChange() + "%");
                        txtCode_btFav.setText(coinChildr0.getCode());
                        txtDate_btFav.setText(convertDataService.convertDate(coinChildr0.getTimestamp()));
                        txtCodeIn_btFav.setText(coinChildr0.getCodein());
                        progressDetails_btFav.setVisibility(View.GONE);;
                        txtNameTitle_btFav.setText(coinChildr0.getName());

                        Glide.with(FavActivity.this).load(coinChildrList.get(position).getUlrPhoto())
                                .transition(DrawableTransitionOptions.withCrossFade()).into(imgCodeTitle_btFav);

                        Glide.with(FavActivity.this).load(coinChildrList.get(position).getUlrPhoto())
                                .transition(DrawableTransitionOptions.withCrossFade()).into(imgCode_btFav);
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
