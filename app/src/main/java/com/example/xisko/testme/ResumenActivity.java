package com.example.xisko.testme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ResumenActivity extends AppCompatActivity {


    private static final String TAG = "ResumenActivity";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.action_nueva:
                MyLog.i("ActionBar", "Nuevo!");
                return true;
            case R.id.action_settings:
                MyLog.i("ActionBar", "Ajustes!");

                return true;
            case R.id.action_acercade:
                MyLog.i("ActionBar", "Acerca de");

                Intent myIntent = new Intent(ResumenActivity.this, AcercaDeActivity.class);
                ResumenActivity.this.startActivity(myIntent);
                return true;

            case R.id.action_listado:
                MyLog.i("ActionBar", "Listado");

                myIntent = new Intent(ResumenActivity.this, ListadoActivity.class);
                ResumenActivity.this.startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
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
