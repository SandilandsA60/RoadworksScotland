package org.me.gcu.sandilands_adam_s2032103;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
   private static int SPLASH_TIME_OUT = 4000 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent homeIntent = new Intent(splash.this, MainActivity.class);
                     startActivity(homeIntent) ;
                finish();
            }

        }, SPLASH_TIME_OUT);
    }
}