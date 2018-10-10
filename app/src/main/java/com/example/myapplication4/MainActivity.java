package com.example.myapplication4;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();

        LoadScreen loadScreen = new LoadScreen();
        loadScreen.start();

    }

    private class LoadScreen extends Thread{
        public void run(){
            try{
                sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Intent intent = new Intent(MainActivity.this,SelectEdge.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }
}
