package com.automatodev.coinSee.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.firebase.FUserCallback;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.controller.service.firebase.UserService;
import com.automatodev.coinSee.view.fragment.CriptoFragment;
import com.automatodev.coinSee.view.fragment.ForexFragment;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    public static ProgressBar progressChart_main;
    private TextView lblUser_main;
    private CircleImageView imgUser_main;
    private RelativeLayout relativeLayoutProgressUser_main;
    private SmartTabLayout viewpagertab;
    private ViewPager viewpager;

    public static boolean status;
    public static String uid;


    public static UserEntity userMain;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblUser_main = findViewById(R.id.lblUser_main);
        imgUser_main = findViewById(R.id.imgUser_main);
        relativeLayoutProgressUser_main = findViewById(R.id.relativeProgressUser_main);
        viewpagertab = findViewById(R.id.viewpagertab);
        viewpager = findViewById(R.id.viewpager);
        progressChart_main = findViewById(R.id.progressData_main);

        userService = new UserService(this);


        FragmentPagerItemAdapter fr = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Forex", ForexFragment.class)
                .add("Cripto", CriptoFragment.class)
                .create());


        viewpager.setAdapter(fr);
        viewpagertab.setViewPager(viewpager);;

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
        relativeLayoutProgressUser_main.setVisibility(View.VISIBLE);
        getDataUser();

    }

    public void actMainProfile(View view) {
        if (!ProfileActivity.status) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("user", userMain);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    public void getDataUser() {
        relativeLayoutProgressUser_main.setVisibility(View.VISIBLE);
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


}
