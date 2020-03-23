package com.automatodev.coinSee.models.firebase;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.automatodev.coinSee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserFirebase {
    private Activity context;
    private FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference reference;
    private ProgressBar progressPick_profile;
    private RelativeLayout relativePick_profile;

    public UserFirebase(Activity context) {
        this.context = context;
        firebaseStorage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressPick_profile = context.findViewById(R.id.progressPick_profile);
        relativePick_profile = context.findViewById(R.id.relativePick_profile);
    }

    public void updatePickUser(final String uid, Uri uri) {
        final AlertDialog alerta = new AlertDialog.Builder(context).create();
        final View v = context.getLayoutInflater().inflate(R.layout.progress_login, null);
        final TextView txtLabel_progress = v.findViewById(R.id.txtLabel_progress);
        final ProgressBar spin_kit = v.findViewById(R.id.spin_kit);
        final LottieAnimationView okAnimation_progress = v.findViewById(R.id.okAnimation_progress);
        txtLabel_progress.setText("Enviando imagem...");
        alerta.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alerta.setCancelable(false);
        alerta.setView(v);
        alerta.show();
      /*  progressPick_profile.setVisibility(View.VISIBLE);
        relativePick_profile.setVisibility(View.VISIBLE);*/
        reference = firebaseStorage.getReference(uid);
        reference.putFile(uri)
                .addOnCompleteListener(context, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        reference.getDownloadUrl()
                                .addOnSuccessListener(context, new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        firestore.collection("users").document(uid)
                                                .update("userPhoto", uri.toString())
                                                .addOnCompleteListener(context, new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        txtLabel_progress.setText("Successo!!");
                                                        spin_kit.setVisibility(View.GONE);
                                                        okAnimation_progress.setVisibility(View.VISIBLE);
                                                        okAnimation_progress.addAnimatorListener(new Animator.AnimatorListener() {
                                                            @Override
                                                            public void onAnimationStart(Animator animation) {
                                                            }

                                                            @Override
                                                            public void onAnimationEnd(Animator animation) {
                                                                try {
                                                                    Thread.sleep(200);
                                                                } catch (InterruptedException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                alerta.dismiss();
                                                            }

                                                            @Override
                                                            public void onAnimationCancel(Animator animation) {
                                                            }

                                                            @Override
                                                            public void onAnimationRepeat(Animator animation) {
                                                            }
                                                        });
                                                       /* progressPick_profile.setVisibility(View.GONE);
                                                        relativePick_profile.setVisibility(View.GONE);*/
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }
}
