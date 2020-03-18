package com.automatodev.coinSee.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.FCoinCallback;
import com.automatodev.coinSee.controller.callback.FUserCallback;
import com.automatodev.coinSee.controller.callback.RetrofitCallback;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.controller.service.API.CoinService;
import com.automatodev.coinSee.controller.service.firebase.FavCoinService;
import com.automatodev.coinSee.controller.service.firebase.GetUserService;
import com.automatodev.coinSee.view.adapter.CoinAdapter;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    private ProgressBar progressChart_main;
    private RecyclerView recyclerCoin_main;
    private TextView lblUser_main;
    private CircleImageView imgUser_main;

    public static boolean status;
    private String uid;
    String urls[] = {"https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fusd.png?alt=media&token=6ee6ec10-ee9c-4483-96d6-e401111f3761",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fars.png?alt=media&token=7a40539c-4d57-41de-ab11-3160c08d654a",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Faud.png?alt=media&token=0cdf86c1-81f3-4206-8fca-584306774567",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fbtc.png?alt=media&token=9c5a136a-df75-4ceb-bc4c-8273365e1774",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fcad.png?alt=media&token=c2b2e9e0-958c-4e41-916b-23ab027672c8",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fchf.png?alt=media&token=c1f3d93c-0819-4dbf-a4eb-984024c25bdb",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fcny.png?alt=media&token=d1d72eaf-cab3-43d2-a161-f970c831ed42",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Feth.png?alt=media&token=03e1d490-c897-4a9a-8a96-3245e5297e7d",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Feur.png?alt=media&token=15973782-f226-47a5-bc17-209c6e348fd0",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fgbp.png?alt=media&token=85cbab11-9085-4824-941b-ab4752ed4386",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fils.png?alt=media&token=902ea792-01b0-44d9-b5d7-d0f974513ab9",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fjpy.png?alt=media&token=aaaaa627-af09-492d-a783-5420ce126918",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fltc.png?alt=media&token=9497de55-7f5b-4525-89b4-3f9dd445075d",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fxrp.png?alt=media&token=3a238ebf-9e21-43a5-a193-82790d074d27",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fusdt.png?alt=media&token=d904bab4-40b0-4372-9fb8-2800fd03bee2"};

    private Animation anim2;
    private CoinAdapter coinAdapter;
    private CoinService task;
    private UserEntity userMain;
    private GetUserService getUserService;
    private FavCoinService favCoinService;
    SwipeRefreshLayout sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerCoin_main = findViewById(R.id.recyclerCoin_main);
        progressChart_main = findViewById(R.id.progressChart_main);
        lblUser_main = findViewById(R.id.lblUser_main);
        imgUser_main = findViewById(R.id.imgUser_main);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.push_left);
        sw = findViewById(R.id.swipte_main);
        sw.setColorSchemeResources(R.color.colorPrimary);
        ThreeBounce three = new ThreeBounce();
        progressChart_main.setIndeterminateDrawable(three);
        task = new CoinService(this);
        getUserService = new GetUserService(this);
        coinAdapter = new CoinAdapter(null, MainActivity.this);
        favCoinService = new FavCoinService(this);
        recyclerCoin_main.hasFixedSize();
        recyclerCoin_main.setLayoutManager(new LinearLayoutManager(this));
        getDataUser();
        getData();
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                task.requestAll(new RetrofitCallback() {
                    @Override
                    public void onSucces(final List<CoinChildr> coinChildrList) {
                        favCoinService.getFavCoinService(uid, new FCoinCallback() {
                            @Override
                            public void onComplete(Task<QuerySnapshot> task) {
                            }

                            @Override
                            public void onCompleteReturn(List<CoinChildr> list) {
                                for (int i = 0; i < coinChildrList.size(); i++)
                                    coinChildrList.get(i).setUlrPhoto(urls[i]);
                                for (int i = 0; i < coinChildrList.size(); i++) {
                                    for (int j = 0; j < list.size(); j++) {
                                        if (coinChildrList.get(i).getName().equals(list.get(j).getName()))
                                            coinChildrList.get(i).setFav(list.get(j).isFav());
                                    }
                                }
                                coinAdapter.setCoinChildrs(coinChildrList);
                                progressChart_main.setVisibility(View.GONE);
                                recyclerCoin_main.setAdapter(coinAdapter);
                                sClick(coinChildrList);
                                sw.setRefreshing(false);
                            }

                            @Override
                            public void onFailure(Exception e) {
                            }
                        });
                    }

                    @Override
                    public void onSucces(CoinChildr coinChildr0) throws InterruptedException {
                    }
                });
            }
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        getDataUser();
    }

    private void getData() {
        task.requestAll(new RetrofitCallback() {
            @Override
            public void onSucces(final List<CoinChildr> coinChildrList) {
                favCoinService.getFavCoinService(uid, new FCoinCallback() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                    }

                    @Override
                    public void onCompleteReturn(List<CoinChildr> list) {
                        for (int i = 0; i < coinChildrList.size(); i++)
                            coinChildrList.get(i).setUlrPhoto(urls[i]);
                        for (int i = 0; i < coinChildrList.size(); i++) {
                            for (int j = 0; j < list.size(); j++) {
                                if (coinChildrList.get(i).getName().equals(list.get(j).getName()))
                                    coinChildrList.get(i).setFav(list.get(j).isFav());
                            }
                        }
                        coinAdapter.setCoinChildrs(coinChildrList);
                        recyclerCoin_main.setAdapter(coinAdapter);
                        progressChart_main.setVisibility(View.GONE);
                        recyclerCoin_main.setAnimation(anim2);
                        sClick(coinChildrList);
                    }

                    @Override
                    public void onFailure(Exception e) {
                    }
                });
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

    private void sClick(final List<CoinChildr> coinChildrList) {
        coinAdapter.setOnItemClickListener(new CoinAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                coinAdapter.notifyItemChanged(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("value", coinChildrList.get(position));
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

    public void getDataUser() {
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            uid = bundle.getString("uid");
            getUserService.serviceGetUser(uid, new FUserCallback() {
                @Override
                public void onEventListener(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                }

                @Override
                public void onEventSucccess(UserEntity userEntity) {
                    try {
                        userMain = userEntity;
                        lblUser_main.setText(userEntity.getUserName());
                        Glide.with(MainActivity.this).load(userEntity.getUserPhoto()).into(imgUser_main);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void actMainFav(View view) {
        Intent intent = new Intent(this, FavActivity.class);
        intent.putExtra("user", userMain);
        startActivity(intent);
    }
}
