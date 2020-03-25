package com.automatodev.coinSee.models.firebase;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.callback.firebase.FAuthCallback;
import com.automatodev.coinSee.controller.callback.firebase.FSaveCallback;
import com.automatodev.coinSee.controller.callback.firebase.FUserCallback;
import com.automatodev.coinSee.controller.entity.UserEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserFirebase {
    private Activity context;
    private FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference reference;
    private FirebaseAuth auth;

    //####################################
    public UserFirebase(Activity context) {
        this.context = context;
        firebaseStorage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    //####################################
    public void updatePickUserFirebase(final String uid, Uri uri) {
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
                                                                alerta.dismiss();
                                                            }

                                                            @Override
                                                            public void onAnimationCancel(Animator animation) {
                                                            }

                                                            @Override
                                                            public void onAnimationRepeat(Animator animation) {
                                                            }
                                                        });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    //####################################
    public void saveUserFirebase(String uid, UserEntity userEntity, final FSaveCallback callback) {
        firestore.collection("users").document(uid)
                .set(userEntity)
                .addOnCompleteListener(context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.onSuccess(task);
                    }
                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e);
            }
        });
    }

    //####################################
    public void getUserFirebase(String uid, final FUserCallback FUserCallback) {
        firestore.collection("users").document(uid)
                .addSnapshotListener(context, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        FUserCallback.onEventListener(documentSnapshot, e);
                    }
                });
    }

    //####################################
    public void createUserFirebase(String email, String pass, final FAuthCallback callback) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        callback.onSuccess(task);
                    }
                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e);
            }
        });
    }

    //####################################
    public void loginUserFirebase(String email, String pass, final FAuthCallback callback) {
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        callback.onSuccess(task);
                    }
                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e);
            }
        });
    }

    //####################################
    public void authSignOut() {
        auth.signOut();
    }

    //####################################
    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

    public void recoveryPass(String email, final FSaveCallback callback){
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.onSuccess(task);
                    }

                }).addOnFailureListener(context, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure(e);
            }
        });
    }
}
