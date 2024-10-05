package com.expense.moneytracker.views.activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.expense.moneytracker.R;

public class SplashActivity extends AppCompatActivity {

    TextView appname;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //For Lottie animation
        //appname = findViewById(R.id.appname);
        //lottie = findViewById(R.id.lottie);
        // for animation of text and lotti file on splash screen
        // appname. animate().translationY(-1400).setDuration(2700).setStartDelay(0);
        // lottie. animate().translationX(2000).setDuration(2000).setStartDelay(2900);

//        new Handler().postDelayed(new Runnable() {
//          @Override
//          public void run() {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                  finish();
//          }
//      }, 4000);     // Delay Time in milliseconds of Splash Screen


//   For basic Image on Splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);     // Delay Time in milliseconds of Splash Screen
    }
}