package com.example.absensipegawai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    private ImageView logosplash;
    private static int splashTimeOut = 4000;
    SharedPreferences sharedPreferences;
    private String fcmToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        logosplash = (ImageView) findViewById(R.id.logosplash);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FirebaseToken", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        Log.d("FirebaseToken", token);
                        fcmToken = token;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("fcm_token", fcmToken);
                        editor.apply();
                    }
                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userNip = sharedPreferences.getString("nip", null);
                Intent intent;

                if(userNip == null) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent.putExtra("nip", userNip);
                }
                startActivity(intent);
                finish();
            }
        }, splashTimeOut);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mysplashanimation);
        logosplash.startAnimation(myanim);
    }
}
