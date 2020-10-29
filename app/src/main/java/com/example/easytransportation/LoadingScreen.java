package com.example.easytransportation;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class LoadingScreen extends AppCompatActivity {
    //Loading Screen private
    private static int SPLASH_TIME_OUT = 4000;
    //End
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadingscreen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //        Loading Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(LoadingScreen.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
        //        End of Loading Screen
    }
}
