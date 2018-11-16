package com.example.xisko.testme.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Persistencia.Repositorio;
import com.example.xisko.testme.Pregunta;
import com.example.xisko.testme.PreguntaAdapter;
import com.example.xisko.testme.R;
import com.example.xisko.testme.SwipeController;


import java.util.ArrayList;
import java.util.Collections;

public class ListadoActivity extends AppCompatActivity {

    SwipeController swipeController = new SwipeController();

    private RecyclerView.LayoutManager lManager;



    private static final String TAG = "ListadoActivity";
    private ArrayList<Pregunta> items;
    private PreguntaAdapter adapter = new PreguntaAdapter(items);

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
        miRepo.cargarPreguntas(myContext);
        items = miRepo.getMisPreguntas();





    }


    @Override
    protected void onStart() {
        MyLog.d(TAG, "Iniciando OnStart");
        super.onStart();
        MyLog.d(TAG, "Finalizando OnStart");
    }

    @Override
    public  void onResume() {
        MyLog.d(TAG, "Iniciando OnResume");
        super.onResume();
        MyLog.d(TAG, "Finalizando OnResume");


        // Crea una lista con los elementos a mostrar
        items = new ArrayList<>();
        miRepo.cargarPreguntas(myContext);
        items = miRepo.getMisPreguntas();
        Collections.reverse(items);
        // Inicializa el RecyclerView
        // Inicializa el RecyclerView
        final RecyclerView rv = (RecyclerView)findViewById(R.id.recyclerView);

        rv.setHasFixedSize(true);

        //Swipe
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(rv);




        // Usar un administrador para LinearLayout
        LinearLayoutManager llm = new LinearLayoutManager(myContext);
        rv.setLayoutManager(llm);

        // Crea el Adaptador con los datos de la lista anterior
        PreguntaAdapter adaptador = new PreguntaAdapter(items);

        // Asocia el elemento de la lista con una acción al ser pulsado
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al pulsar el elemento
                int position = rv.getChildAdapterPosition(v);
                Toast.makeText(ListadoActivity.this, "Posición: " + String.valueOf(position) + " Id: " + items.get(position).getEnunciado() + " Nombre: " + items.get(position).getCategoria(), Toast.LENGTH_SHORT)
                        .show();
            }
        });

        // Asocia el Adaptador al RecyclerView
        rv.setAdapter(adaptador);

        // Muestra el RecyclerView en vertical
        rv.setLayoutManager(new LinearLayoutManager(this));
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
