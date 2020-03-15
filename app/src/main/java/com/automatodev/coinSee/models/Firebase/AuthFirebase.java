package com.automatodev.coinSee.models.Firebase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.automatodev.coinSee.view.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthFirebase {
    private Activity context;
    private FirebaseAuth auth;


    public AuthFirebase(Activity context) {
        this.context = context;

        auth = FirebaseAuth.getInstance();
    }

    public void login(String email, String pass){
        final AlertDialog alerta = new AlertDialog.Builder(context).create();
        alerta.setTitle("Titulo");
        alerta.setMessage("Aguarde...");
        alerta.show();

        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            alerta.dismiss();
                            actLoginMain();
                        }
                    }
                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                alerta.dismiss();
                AlertDialog.Builder alerta = new AlertDialog.Builder(context);
                alerta.setTitle("Usuario");
                alerta.setMessage("Verifique seu email e/ou senha \nVerifique tambem sua conexão com a internet :D");
                alerta.setPositiveButton("Entendi", null);
                alerta.show();;
            }
        });

    }
    public void actLoginMain(){
        if (auth.getCurrentUser() != null && !MainActivity.status){
            String uid = auth.getUid();
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("uid",uid);
            context.startActivity(intent);
            context.finish();
        }
    }
    public void authSignOut(){
        auth.signOut();
    }
    public FirebaseUser getUser(){
        return auth.getCurrentUser();
    }
}
