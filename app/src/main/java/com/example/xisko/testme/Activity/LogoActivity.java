package com.example.xisko.testme.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.R;

public class LogoActivity extends AppCompatActivity {
    //VARIABLES PARA CAMBIAR EL SPLAHSCREEN POR LA PANTALLA DE INICIO
    private static final String TAG = "LogoActivity";
    private static int TIEMPO = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyLog.d(TAG,"Iniciando OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {

            public void run() {
                Intent it = new Intent(LogoActivity.this, ResumenActivity.class);
                startActivity(it);
                finish();
            }
        }
        ,TIEMPO);
        MyLog.d(TAG, "Finalizando OnCreate");
    }

    @Override
    protected void onStart() {
        MyLog.d(TAG, "Iniciando OnStart");
        super.onStart();
        MyLog.d(TAG, "Finalizando OnStart");
    }

    @Override
    protected void onResume() {
        MyLog.d(TAG, "Iniciando OnResume");
        super.onResume();
        MyLog.d(TAG, "Finalizando OnResume");
    }

    @Override
    protected void onPause() {
        MyLog.d(TAG, "Iniciando OnPause");
        super.onPause();
        MyLog.d(TAG, "Finalizando OnPause");
    }

    @Override
    protected void onStop() {
        MyLog.d(TAG, "Iniciando OnStop");
        super.onStop();
        MyLog.d(TAG, "Finalizando OnStop");
    }

    @Override
    protected void onRestart() {
        MyLog.d(TAG, "Iniciando OnRestart");
        super.onRestart();
        MyLog.d(TAG, "Finalizando OnRestart");
    }

    @Override
    protected void onDestroy() {
        MyLog.d(TAG, "Iniciando OnDestroy");
        super.onDestroy();
        MyLog.d(TAG, "Finalizando OnDestroy");
    }
}
