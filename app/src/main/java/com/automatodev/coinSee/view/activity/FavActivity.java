package com.automatodev.coinSee.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.API.AwesomeCallback;
import com.automatodev.coinSee.controller.callback.firebase.FCoinCallback;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.controller.service.API.AlphaService;
import com.automatodev.coinSee.controller.service.API.AwesomeService;
import com.automatodev.coinSee.controller.service.ConvertDataService;
import com.automatodev.coinSee.controller.service.firebase.FavCoinService;
import com.automatodev.coinSee.view.adapter.FavAdapter;
import com.automatodev.coinSee.view.component.ChartLine;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavActivity extends AppCompatActivity {

    public static boolean status;
    private int statusAnimation = 0;
    private RecyclerView recyclerFav_fav;
    private FavAdapter favAdapter;
    private ChartLine chartLine;
    private AwesomeService awesomeService;
    private Animation animation;
    private ConvertDataService convertDataService;
    private CircleImageView imgUser_fav;
    private FavCoinService favCoinService;
    private ProgressBar progressFav_fav;
    private UserEntity userEntity;
    private RelativeLayout relativeNothing_fav;
    private AlphaService alphaService;
    private CardView cardDetails_fav;
    private Animation anim;
    private Animation anim2;

    private TextView txtCoinValue_btFav;
    private TextView txtName_btFav;
    private TextView txtHigh_btFav;
    private TextView txtLow_btFav;
    private TextView txtPercent_btFav;
    private ImageView imgCode_btFav;
    private TextView txtCode_btFav;
    private TextView txtDate_btFav;
    private TextView txtCodeIn_btFav;
    private RelativeLayout relative_detais_btFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        recyclerFav_fav = findViewById(R.id.recyclerFav_fav);
        imgUser_fav = findViewById(R.id.imgUser_fav);
        relativeNothing_fav = findViewById(R.id.relativeNothing_fav);
        progressFav_fav = findViewById(R.id.progressFav_fav);
        cardDetails_fav = findViewById(R.id.cardDetails_fav);
        txtCoinValue_btFav = findViewById(R.id.txtCoinValue_btFav);
        txtName_btFav = findViewById(R.id.txtName_btFav);
        txtHigh_btFav = findViewById(R.id.txtHigh_btFav);
        txtLow_btFav = findViewById(R.id.txtLow_btFav);
        txtPercent_btFav = findViewById(R.id.txtPercent_btFav);
        imgCode_btFav = findViewById(R.id.imgCode_btFav);
        txtCode_btFav = findViewById(R.id.txtCode_btFav);
        txtDate_btFav = findViewById(R.id.txtDate_btFav);
        txtCodeIn_btFav = findViewById(R.id.txtCodeIn_btFav);
        relative_detais_btFav = findViewById(R.id.relative_detais_btFav);
        awesomeService = new AwesomeService(this);
        alphaService = new AlphaService(this);
        convertDataService = new ConvertDataService();
        favCoinService = new FavCoinService(this);
        favAdapter = new FavAdapter(this, null);
        animation = AnimationUtils.loadAnimation(this, R.anim.push_right);
        anim = AnimationUtils.loadAnimation(this, R.anim.push_right);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.push_left);
        ThreeBounce three = new ThreeBounce();
        progressFav_fav.setIndeterminateDrawable(three);
        recyclerFav_fav.hasFixedSize();
        recyclerFav_fav.setLayoutManager(new LinearLayoutManager(this));
        userEntity = getIntent().getParcelableExtra("user");
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
                if (list.size() == 0)
                    relativeNothing_fav.setVisibility(View.VISIBLE);
                relativeNothing_fav.setAnimation(animation);
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

                if (cardDetails_fav.getVisibility() == View.GONE){
                    cardDetails_fav.setVisibility(View.VISIBLE);
                    cardDetails_fav.setAnimation(anim);
                }
/*                if (statusAnimation == 0) {
                    statusAnimation = 1;
                } else{
                    cardDetails_fav.setAnimation(anim2);
                    statusAnimation = 0;
                }*/
                awesomeService.requestSingle(coinChildrList.get(position).getCode() + "-" + coinChildrList.get(position).getCodein(), new AwesomeCallback() {
                    @Override
                    public void onSucces(List<CoinChildr> coinChildrList) throws InterruptedException {
                    }

                    @Override
                    public void onSucces(final CoinChildr coinChildr0) throws InterruptedException {
                        txtCoinValue_btFav.setText(convertDataService.convertDecimal(coinChildr0.getBid()));
                        txtName_btFav.setText(coinChildr0.getName());
                        txtHigh_btFav.setText(coinChildr0.getHigh());
                        txtLow_btFav.setText(coinChildr0.getLow());
                        txtPercent_btFav.setText(coinChildr0.getPctChange() + "%");
                        txtCode_btFav.setText(coinChildr0.getCode());
                        txtDate_btFav.setText(convertDataService.convertDate(coinChildr0.getTimestamp()));
                        txtCodeIn_btFav.setText(coinChildr0.getCodein());
                        relative_detais_btFav.setVisibility(View.GONE);
                        Glide.with(FavActivity.this).load(coinChildrList.get(position).getUlrPhoto())
                                .transition(DrawableTransitionOptions.withCrossFade()).into(imgCode_btFav);
                    }
                });
            }

            @Override
            public void onDeleteClick(final int position) {
                final AlertDialog delFav = new AlertDialog.Builder(FavActivity.this).create();
                delFav.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                View v = getLayoutInflater().inflate(R.layout.layout_message, null);
                TextView txtTitle_mesage = v.findViewById(R.id.txtTitle_message);
                TextView txtLabel_message = v.findViewById(R.id.txtLabel_message);
                Button btnDismiss_message = v.findViewById(R.id.btnDismiss_message);
                Button btnNegative_message = v.findViewById(R.id.btnNegative_message);
                txtTitle_mesage.setText("Aviso!");
                txtLabel_message.setText("Deseja emover este item da lista de favoritos?");
                btnDismiss_message.setText("Sim");
                btnNegative_message.setText("Não");
                btnNegative_message.setVisibility(View.VISIBLE);
                btnDismiss_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        favCoinService.delFavCoinService(userEntity.getUserUid(), coinChildrList.get(position).getCoinUid());
                        coinChildrList.remove(position);
                        favAdapter.notifyItemRemoved(position);
                        if (favAdapter.getItemCount() == 0) {
                            relativeNothing_fav.setVisibility(View.VISIBLE);
                        }
                        delFav.dismiss();
                    }
                });
                btnNegative_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delFav.dismiss();
                    }
                });
                delFav.setView(v);
                delFav.show();
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
