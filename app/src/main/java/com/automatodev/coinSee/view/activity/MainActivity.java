package com.automatodev.coinSee.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.firebase.FCoinCallback;
import com.automatodev.coinSee.controller.callback.firebase.FUserCallback;
import com.automatodev.coinSee.controller.callback.API.RetrofitCallback;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.controller.service.API.AwesomeService;
import com.automatodev.coinSee.controller.service.firebase.FavCoinService;
import com.automatodev.coinSee.controller.service.firebase.UserService;
import com.automatodev.coinSee.view.adapter.CoinAdapter;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    private ProgressBar progressChart_main;
    private RecyclerView recyclerCoin_main;
    private TextView lblUser_main;
    private CircleImageView imgUser_main;
    private RelativeLayout relativeLayoutProgressUser_main;

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
    private AwesomeService task;
    private UserEntity userMain;
    private FavCoinService favCoinService;
    private List<CoinChildr> coinLocal;
    private SwipeRefreshLayout sw;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerCoin_main = findViewById(R.id.recyclerCoin_main);
        progressChart_main = findViewById(R.id.progressChart_main);
        relativeLayoutProgressUser_main = findViewById(R.id.relativeProgressUser_main);
        lblUser_main = findViewById(R.id.lblUser_main);
        imgUser_main = findViewById(R.id.imgUser_main);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.push_left);
        sw = findViewById(R.id.swipte_main);
        sw.setColorSchemeResources(R.color.colorPrimary);
        ThreeBounce three = new ThreeBounce();
        progressChart_main.setIndeterminateDrawable(three);
        task = new AwesomeService(this);
        userService = new UserService(this);
        coinAdapter = new CoinAdapter(null, MainActivity.this);
        favCoinService = new FavCoinService(this);
        recyclerCoin_main.hasFixedSize();
        recyclerCoin_main.setLayoutManager(new LinearLayoutManager(this));

        coinLocal = new ArrayList<>();
        getDataUser();
        //getData();
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
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
        refreshData();
    }

    public void actMainProfile(View view) {
        if (!ProfileActivity.status) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("user", userMain);
            startActivity(intent);
        }
    }

    private void sClick(final List<CoinChildr> coinChildrList) {
        coinAdapter.setOnItemClickListener(new CoinAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                coinAdapter.notifyItemChanged(position);
                if (!DetailsActivity.status) {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("value", coinChildrList.get(position));
                    intent.putExtra("user", userMain);
                    startActivity(intent);
                }
            }

            @Override
            public void onFavItemClick(int position) {
                if (!coinChildrList.get(position).isFav()) {
                    coinChildrList.get(position).setFav(true);
                    coinChildrList.get(position).setCoinUid(UUID.randomUUID().toString());
                    favCoinService.addFavCoinService(uid, new CoinChildr(
                            coinChildrList.get(position).getCode(),
                            coinChildrList.get(position).getCodein(),
                            coinChildrList.get(position).getName(),
                            coinChildrList.get(position).getUlrPhoto(),
                            coinChildrList.get(position).isFav(),
                            UUID.randomUUID().toString()
                    ));
                    coinAdapter.notifyItemChanged(position);
                }
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
            userService.getUserService(uid, new FUserCallback() {
                @Override
                public void onEventListener(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                }

                @Override
                public void onSuccess(UserEntity userEntity) {
                    try {
                        userMain = userEntity;
                        lblUser_main.setText(userEntity.getUserName());
                        Glide.with(MainActivity.this).load(userEntity.getUserPhoto()).placeholder(R.drawable.ic_user_round).into(imgUser_main);
                        relativeLayoutProgressUser_main.setVisibility(View.GONE);
                    } catch (Exception e) {
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
    //Metodo que pegara os primeiros dados e/ou atualizara os dados da API vs favoritos do banco de dados
    public void refreshData() {
        /*Se a lista auxiliar for diferente de zero, inicia o refresh lyaout, ou seja isto nao se aplica a inicializaçao
        do programa, somente durante sua execução
        se a lista for igual a zero, uma progressbar estara visivel indicando assim a 1º inicialização do app*/
        if (coinLocal.size() != 0)
            sw.setRefreshing(true);
        //invoca o metodo que fara a requisição de todos os dados da Api
        task.requestAll(new RetrofitCallback() {
            @Override
            public void onSucces(final List<CoinChildr> coinChildrList) {
                /*Ao terminar seu trabalho teremos uma lista de dados pronta para uso assim invocando agora
                o metodo que captura a lista de favoritos do banco de dados da google
                passando como parametro o uid do usuario e um callback para transações assincronas*/
                favCoinService.getFavCoinService(uid, new FCoinCallback() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                    }

                    @Override
                    public void onCompleteReturn(List<CoinChildr> list) {
                        //ao completar sua tarefa agora temos  uma lista de moedas e uma lista de favoritos de moedas

                        /*O primeiro forLoop associa a cada item da lista, um link para download das bandeiras dos paises
                        ja que a api nao traz as imagens da interent*/
                        for (int i = 0; i < coinChildrList.size(); i++)
                            coinChildrList.get(i).setUlrPhoto(urls[i]);

                        /*O segundo forLoop faz uma comparação entre um dado da lista vinda da api e da lista de favoritos
                        vindo do servidor usando como base o nome da moeda, ja que a API nao tras duas moedas repetidas
                        Para cada item da lista vinda da api eu faço  um outro forLooP na lista de favoritos, onde se o dado da API na posição i
                        for igual ao dado vindo do banco de dados onde estao os favoritos na posição J, entao o campo favorito
                        da lista vinda da api é atualizado com o campo favorito vindo do banco de dados
                         tendo agora uma lista vinda da API com url de imagens e booleanos indicando se é favorito ou nao*/
                        for (int i = 0; i < coinChildrList.size(); i++) {
                            for (int j = 0; j < list.size(); j++) {
                                if (coinChildrList.get(i).getName().equals(list.get(j).getName()))
                                    coinChildrList.get(i).setFav(list.get(j).isFav());
                            }
                        }

                        /*Atualizações sao feitas na API de 30 em 30 segundos e alterações de favorito = true ou false tambem sao realizadas
                        pelo usuario, tendo a necessidade de atualizar apenas a celula afetada ou seja apenas o dado afetado
                        sem precisar carregar toda a lista e atualizar a view consumindo mais processamento e recursos do dispositivo
                        ja que nem todos os dados serao atualizados*/

                        /*Se a lista local for vazia ou seja Se aplica em tempo de execução do programa nao sendo valido
                        para a primeira inicialização*/
                        if (coinLocal.size() != 0) {
                            /*Faz um forLoop onde para cada item da lista local eu comparo os dados que tenho na lista local
                             com os dados vindos da api ja com os dados da url e favoritos
                             por isso nao se aplica a primeira inicialização pois a lista na primeira inicialização ainda estara vazia*/
                            for (int i = 0; i < coinLocal.size(); i++) {
                                /*Se os dados na lista local na posição i forem diferentes dos dados da API na posição i
                                deleta o dado antigo na lisita local na posição i
                                e adiciona na mesma posição (i) o dado atualizaçdo da lista vinda do sevidor na posição i*/
                                if (coinLocal.get(i) != coinChildrList.get(i)) {
                                    coinLocal.remove(i);
                                    coinLocal.add(i, coinChildrList.get(i));
                                }
                            }
                            /*Notifica a view dizendo que houve alterações no repositorio de dados (Lista local)
                            e atualiza esses dados em cada celula
                            chamando o metodo que ativa os clicques nas celulas passando como parametro a lista local pronta e atualizada*/
                            coinAdapter.notifyDataSetChanged();
                            sClick(coinLocal);
                            sw.setRefreshing(false);
                            return; // para a execução do bloco pois o progrmaa ja esta em execuçap e uma lista local ja esta preenchida
                        }

                        /*Se for a primeira inicialização do programa
                         atribuo os dados vindo da api para minha lista local
                         atribuo meu objeto de verificação de estado ao meu componente de listagem
                         coloco uma animação tosca no meu compoente de listagem
                         desabilito minha progressbar
                         chamo o metodo sclick passando minha lista local como parametro*/
                        coinLocal = coinChildrList;
                        coinAdapter.setCoinChildrs(coinLocal);
                        recyclerCoin_main.setAdapter(coinAdapter);
                        recyclerCoin_main.setAnimation(anim2);
                        progressChart_main.setVisibility(View.GONE);

                        sClick(coinLocal);
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

    public void refreshList(View view) {
        if (coinLocal.size() != 0) {
            refreshData();

        }
    }
}
