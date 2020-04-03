package com.automatodev.coinSee.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.API.AwesomeCallback;
import com.automatodev.coinSee.controller.callback.firebase.FCoinCallback;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.service.API.AwesomeService;
import com.automatodev.coinSee.controller.service.firebase.FavCoinService;
import com.automatodev.coinSee.view.activity.DetailsActivity;
import com.automatodev.coinSee.view.activity.MainActivity;
import com.automatodev.coinSee.view.adapter.CoinAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CriptoFragment extends Fragment {
    // private ProgressBar progressChart_main;
    private RecyclerView recyclerCriptoMain;
    private SwipeRefreshLayout sw;

    public static boolean status;
    private int typeCoin = 0;
    String urls[] = {
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fusd.png?alt=media&token=6ee6ec10-ee9c-4483-96d6-e401111f3761",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fusdt.png?alt=media&token=d904bab4-40b0-4372-9fb8-2800fd03bee2",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fcad.png?alt=media&token=c2b2e9e0-958c-4e41-916b-23ab027672c8",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Feur.png?alt=media&token=15973782-f226-47a5-bc17-209c6e348fd0",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fgbp.png?alt=media&token=85cbab11-9085-4824-941b-ab4752ed4386",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fars.png?alt=media&token=7a40539c-4d57-41de-ab11-3160c08d654a",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fbtc.png?alt=media&token=9c5a136a-df75-4ceb-bc4c-8273365e1774",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fltc.png?alt=media&token=9497de55-7f5b-4525-89b4-3f9dd445075d",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fjpy.png?alt=media&token=aaaaa627-af09-492d-a783-5420ce126918",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fchf.png?alt=media&token=c1f3d93c-0819-4dbf-a4eb-984024c25bdb",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Faud.png?alt=media&token=0cdf86c1-81f3-4206-8fca-584306774567",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fcny.png?alt=media&token=d1d72eaf-cab3-43d2-a161-f970c831ed42",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fils.png?alt=media&token=902ea792-01b0-44d9-b5d7-d0f974513ab9",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Feth.png?alt=media&token=03e1d490-c897-4a9a-8a96-3245e5297e7d",
            "https://firebasestorage.googleapis.com/v0/b/coinsee-7cbb3.appspot.com/o/codCoin%2Fxrp.png?alt=media&token=3a238ebf-9e21-43a5-a193-82790d074d27",
    };
    private Animation anim2;
    private CoinAdapter coinAdapter;
    private AwesomeService task;
    private FavCoinService favCoinService;
    private List<CoinChildr> coinCripotList;
    private List<CoinChildr> coinCriptoAuxList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //-------------------------------
        coinAdapter = new CoinAdapter(null, getActivity());
        favCoinService = new FavCoinService(getActivity());
        task = new AwesomeService(getActivity());
        coinCripotList = new ArrayList<>();
        coinCriptoAuxList = new ArrayList<>();
        //---------------------------------
        return getLayoutInflater().inflate(R.layout.fragment_cripto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //--------------------------
        recyclerCriptoMain = view.findViewById(R.id.recyclerCripto_main);
        sw = view.findViewById(R.id.swipeCripto_main);
        //progressChart_main = view.findViewById(R.id.progressChart_main);
        //------------------------------
        anim2 = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_fast);
        //---------------------------------
        recyclerCriptoMain.hasFixedSize();
        recyclerCriptoMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        //---------------------------------
        sw.setColorSchemeResources(R.color.colorPrimaryDark);
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }
    //Metodo que pegara os primeiros dados e/ou atualizara os dados da API vs favoritos do banco de dados
    public void refreshData() {
        if (coinCripotList.size() != 0)
            sw.setRefreshing(true);
        task.requestAll(sw,new AwesomeCallback() {
            @Override
            public void onSucces(final List<CoinChildr> coinChildrList) {
                for (int i = 0; i < coinChildrList.size(); i++)
                    coinChildrList.get(i).setUlrPhoto(urls[i]);
                favCoinService.getFavCoinService(MainActivity.uid, new FCoinCallback() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                    }

                    @Override
                    public void onCompleteReturn(List<CoinChildr> list) {
                        for (int i = 0; i < coinChildrList.size(); i++) {
                            for (int j = 0; j < list.size(); j++) {
                                if (coinChildrList.get(i).getName().equals(list.get(j).getName()))
                                    coinChildrList.get(i).setFav(list.get(j).isFav());
                            }
                        }
                        if (coinCripotList.size() != 0) {
                            for (CoinChildr c : coinChildrList) {
                                if (c.getCode().equals("BTC") || c.getCode().equals("ETH") || c.getCode().equals("XRP") || c.getCode().equals("LTC"))
                                    coinCriptoAuxList.add(c);
                            }

                            for (int i = 0; i < coinCripotList.size(); i++) {
                                if (coinCripotList.get(i) != coinCriptoAuxList.get(i)) {
                                    coinCripotList.remove(i);
                                    coinCripotList.add(i, coinCriptoAuxList.get(i));
                                }
                            }
                            coinCriptoAuxList.clear();
                            coinAdapter.setCoinChildrs(coinCripotList);
                            coinAdapter.notifyDataSetChanged();
                            sClick(coinCripotList);
                            sw.setRefreshing(false);
                            return;
                        }
                        for (CoinChildr c : coinChildrList) {
                            if (c.getCode().equals("BTC") || c.getCode().equals("ETH") || c.getCode().equals("XRP") || c.getCode().equals("LTC"))
                                coinCripotList.add(c);
                        }
                        coinAdapter.setCoinChildrs(coinCripotList);
                        recyclerCriptoMain.setAdapter(coinAdapter);
                        recyclerCriptoMain.setAnimation(anim2);
                        //progressChart_main.setVisibility(View.GONE);
                        sClick(coinCripotList);
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
    private void sClick(final List<CoinChildr> coinChildrList) {
        coinAdapter.setOnItemClickListener(new CoinAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!DetailsActivity.status) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("value", coinChildrList.get(position));
                    intent.putExtra("user", MainActivity.userMain);
                    startActivity(intent);
                }
            }

            @Override
            public void onFavItemClick(int position) {
                if (!coinChildrList.get(position).isFav()) {
                    coinChildrList.get(position).setFav(true);
                    coinChildrList.get(position).setCoinUid(UUID.randomUUID().toString());
                    favCoinService.addFavCoinService(MainActivity.uid, new CoinChildr(
                            coinChildrList.get(position).getCode(),
                            coinChildrList.get(position).getCodein(),
                            coinChildrList.get(position).getName(),
                            coinChildrList.get(position).getUlrPhoto(),
                            coinChildrList.get(position).isFav(),
                            UUID.randomUUID().toString()
                    ));
                    coinAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }
}
