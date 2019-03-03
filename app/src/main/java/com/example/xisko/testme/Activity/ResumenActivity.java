package com.example.xisko.testme.Activity;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Persistencia.Repositorio;
import com.example.xisko.testme.Pregunta.Pregunta;
import com.example.xisko.testme.R;
import com.example.xisko.testme.RecycleCode;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;


import static com.example.xisko.testme.Constantes.CODE_CAMERA_PERMISSION;
import static com.example.xisko.testme.Constantes.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION;
import static com.example.xisko.testme.Constantes.NETWORK_SWITCH_FILTER;

public class ResumenActivity extends AppCompatActivity {

    Context myContext;
    ConstraintLayout constraint;

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

        Intent myIntent;

        switch (item.getItemId()) {
            case R.id.action_nueva:
                MyLog.i("ActionBar", "Nuevo!");
                myIntent = new Intent(ResumenActivity.this, CrearEditarPreguntaActivity.class);
                ResumenActivity.this.startActivity(myIntent);
                return true;
            case R.id.action_settings:
                myIntent = new Intent(ResumenActivity.this, SettingsActivity.class);
                ResumenActivity.this.startActivity(myIntent);


                MyLog.i("ActionBar", "Ajustes!");

                return true;
            case R.id.action_acercade:
                MyLog.i("ActionBar", "Acerca de");

                myIntent = new Intent(ResumenActivity.this, AcercaDeActivity.class);
                ResumenActivity.this.startActivity(myIntent);
                return true;

            case R.id.action_listado:
                MyLog.i("ActionBar", "Listado");

                myIntent = new Intent(ResumenActivity.this, ListadoActivity.class);
                ResumenActivity.this.startActivity(myIntent);
                return true;
            case R.id.action_exportar:
                Repositorio.cargarPreguntas(myContext);
                RecycleCode.exportarXML(myContext);
                MyLog.i("ActionBar", "Exportar");


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        myContext = this;
        constraint = findViewById(R.id.constraint);
        compruebaPermisos();
        TextView pregunta = findViewById(R.id.numero_preguntas);
        pregunta.setText("Hay un total de: " + Repositorio.getCantidadPreguntas(myContext) + " preguntas almacenadas en la base de datos.");

        Intent myIntent = getIntent();
        RecycleCode.importarXML(myContext, myIntent);
    }


    @Override
    protected void onStart() {
        MyLog.d(TAG, "Iniciando OnStart");
        super.onStart();
        MyLog.d(TAG, "Finalizando OnStart");
    }


    @Override
    protected void onResume() {
//        Locale current = getResources().getConfiguration().locale;
//        String idioma = current.toString();
//        Locale locale = new Locale(idioma);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config,
//        getBaseContext().getResources().getDisplayMetrics());
//        this.setContentView(R.layout.activity_resumen);
       Locale current = getResources().getConfiguration().locale;
        String idioma = current.toString();
        MyLog.w("idioma", ""+current);
        RecycleCode.setLocale2(idioma,this);
        this.setContentView(R.layout.activity_resumen);
        MyLog.d(TAG, "Iniciando OnResume");
        super.onResume();
        TextView pregunta = findViewById(R.id.numero_preguntas);
        pregunta.setText("Hay un total de: " + Repositorio.getCantidadPreguntas(myContext) + " preguntas almacenadas en la base de datos.");
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

    /**
     * A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
     * En las versiones anteriores no es posible hacerlo
     * Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
     * Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
     */
    private void compruebaPermisos (){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            verifyPermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void verifyPermission() {
        int permsRequestCode = 100;
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        int writePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = checkSelfPermission(Manifest.permission.CAMERA);

        if (cameraPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED) {
            //se realiza metodo si es necesario...
        } else {
            requestPermissions(perms, permsRequestCode);
        }
    }


    //Maneja la respuesta del compruebaPermisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case 100:

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    MyLog.e("Permisos: ", "Rechazados");

                }

                if (grantResults.length > 0  &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    MyLog.e("Permisos: ", "Rechazados");

                }

                break;
        }
    }



    }

