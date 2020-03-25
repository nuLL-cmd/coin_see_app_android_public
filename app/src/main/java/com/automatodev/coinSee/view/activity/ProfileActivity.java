package com.automatodev.coinSee.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.automatodev.coinSee.controller.service.ConvertDataService;
import com.automatodev.coinSee.models.firebase.UserFirebase;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    public static boolean status;
    private UserEntity userEntity;
    private ConvertDataService convertDataService;

    private CircleImageView imgUser_profile;
    private TextView txtName_profile;
    private TextView txtEmail_prifile;
    private TextView txtCountry_profile;
    private TextView txtPhone_profile;
    private RelativeLayout relativeProgessInfo_profile;
    private ProgressBar progressInfo_prifile;
    private ProgressBar progressPick_profile;
    private RelativeLayout relativePick_profile;
    private TextView txtTitle_profile;
    private UserFirebase userFirebase;
    private Uri uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName_profile = findViewById(R.id.txtName_profile);
        txtEmail_prifile = findViewById(R.id.txtEmail_profile);
        txtCountry_profile = findViewById(R.id.txtCountry_profile);
        txtPhone_profile = findViewById(R.id.txtPhone_profile);
        imgUser_profile = findViewById(R.id.imgUser_profile);
        txtTitle_profile = findViewById(R.id.txtTitle_profile);
        relativeProgessInfo_profile = findViewById(R.id.relativeProgessInfo_profile);
        progressInfo_prifile = findViewById(R.id.progressInfo_prifile);
        progressPick_profile = findViewById(R.id.progressPick_profile);
        relativePick_profile = findViewById(R.id.relativePick_profile);


        userFirebase = new UserFirebase(this);
        convertDataService = new ConvertDataService();
        ThreeBounce three = new ThreeBounce();
        progressInfo_prifile.setIndeterminateDrawable(three);
        progressPick_profile.setIndeterminateDrawable(three);


        getUser();

    }


    @Override
    protected void onStart(){
        super.onStart();
        status = true;
    }
    @Override
    protected void onStop(){
        super.onStop();
        status = false;
    }

    public void actProfileMain(View view){
        NavUtils.navigateUpFromSameTask(this);
    }

    public void logout(View view){
        final AlertDialog alerta = new AlertDialog.Builder(this).create();
        alerta.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View v = getLayoutInflater().inflate(R.layout.layout_message,null);
        TextView txtTitle_message = v.findViewById(R.id.txtTitle_message);
        TextView txtLabel_message = v.findViewById(R.id.txtLabel_message);
        txtTitle_message.setText("Deseja realmente sair?");
        Button btnDismiss_message = v.findViewById(R.id.btnDismiss_message);
        btnDismiss_message.setText("Sim");
        Button btnNegavite_message = v.findViewById(R.id.btnNegative_message);
        btnNegavite_message.setVisibility(View.VISIBLE);
        btnNegavite_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerta.dismiss();
            }
        });
        txtLabel_message.setText("Lembre-se! Você poderá voltar quando quiser!");
        btnDismiss_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userFirebase.authSignOut();
                Intent intent = new Intent(ProfileActivity.this, SplashActivity.class);
                startActivity(intent);
                alerta.dismiss();
                finish();;
            }
        });
        alerta.setView(v);
        alerta.show();


    }

    public void getUser(){
        userEntity = getIntent().getParcelableExtra("user");
        if (userEntity != null){
            txtTitle_profile.setText("Olá "+userEntity.getUserName());
            txtName_profile.setText(userEntity.getUserName()+" "+userEntity.getUserLastName());
            txtEmail_prifile.setText(userEntity.getUserEmail());
            txtCountry_profile.setText(userEntity.getUserCountry());
            txtPhone_profile.setText(convertDataService.convertPhone(String.valueOf(userEntity.getUserPhone())));
            Glide.with(this).load(userEntity.getUserPhoto())
                    .placeholder(R.drawable.ic_teste)
                    .into(imgUser_profile);
            relativeProgessInfo_profile.setVisibility(View.GONE);;
            progressInfo_prifile.setVisibility(View.GONE);
        }
    }

    public void selectImage(View view){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }else
            selectPick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode ==  Activity.RESULT_OK){
            uri = data.getData();
            if (uri != null)
            Glide.with(this).load(uri.toString()).into(imgUser_profile);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "Para esta função, é necessário conceder esta permissão!", Toast.LENGTH_SHORT).show();
        }
        else
            selectPick();
    }
    public void selectPick(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }
    public void updatePick(View view){
        if (uri != null){
            userFirebase.updatePickUserFirebase(userEntity.getUserUid(),uri);
            uri = null;
        }

    }
}
