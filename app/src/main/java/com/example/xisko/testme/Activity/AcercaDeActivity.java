package com.example.xisko.testme.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.R;
import com.example.xisko.testme.RecycleCode;

import java.util.Locale;

public class AcercaDeActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Locale current = getResources().getConfiguration().locale;
        MyLog.w("idioma", ""+current);
        RecycleCode.setLocale2(current.toLanguageTag(),this);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // añade al menu un item
        getMenuInflater().inflate(R.menu.acercade_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Se crea la accion que se va a realizar al pulsar en el boton atras tanto de la interfaz
        //como de los botones del terminal

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                onNavigateUp();
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();





    }
    @Override
    public void onBackPressed(){

        finish();
    }

    @Override
    public boolean onNavigateUp(){

        finish();
        return true;
    }
}
