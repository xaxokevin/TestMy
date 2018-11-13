package com.example.xisko.testme.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Persistencia.Repositorio;
import com.example.xisko.testme.Pregunta;
import com.example.xisko.testme.PreguntaAdapter;
import com.example.xisko.testme.R;


import java.util.ArrayList;

public class ListadoActivity extends AppCompatActivity {

    private static final String TAG = "ListadoActivity";
    private ArrayList<Pregunta> items;
    private Repositorio miRepo = new Repositorio();
    private Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myContext = ListadoActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton mas = (FloatingActionButton) findViewById(R.id.mas);
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent myIntent = new Intent(ListadoActivity.this, CrearEditarPreguntaActivity.class);
                ListadoActivity.this.startActivity(myIntent);
            }
        });







            // Crea una lista con los elementos a mostrar
            items = new ArrayList<>();
            items.add(new Pregunta( "enuciado 1",  "bien",  "",  "",  "",  ""));
            items.add(new Pregunta( "enuciado 2",  "bien2",  "",  "",  "",  ""));
            items.add(new Pregunta( "enuciado 3",  "bien3",  "",  "",  "",  ""));
            miRepo.cargarPreguntas(myContext);
            items = miRepo.getMisPreguntas();
            // Inicializa el RecyclerView
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

            // Crea el Adaptador con los datos de la lista anterior
            PreguntaAdapter adaptador = new PreguntaAdapter(items);

            // Asocia el elemento de la lista con una acción al ser pulsado
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acción al pulsar el elemento
                    int position = recyclerView.getChildAdapterPosition(v);
                    Toast.makeText(ListadoActivity.this, "Posición: " + String.valueOf(position) + " Id: " + items.get(position).getEnunciado() + " Nombre: " + items.get(position).getCategoria(), Toast.LENGTH_SHORT)
                            .show();
                }
            });

            // Asocia el Adaptador al RecyclerView
            recyclerView.setAdapter(adaptador);

            // Muestra el RecyclerView en vertical
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
