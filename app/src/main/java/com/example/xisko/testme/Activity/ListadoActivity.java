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
import com.example.xisko.testme.Pregunta.Pregunta;
import com.example.xisko.testme.Pregunta.PreguntaAdapter;
import com.example.xisko.testme.R;

import java.util.ArrayList;
import java.util.Collections;

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

        //Añade un toolbar a la actividad
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Añade un boton flotante
        FloatingActionButton mas = (FloatingActionButton) findViewById(R.id.mas);
        mas.setImageResource(R.drawable.ic_loupe_grey600_48dp);
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ListadoActivity.this, CrearEditarPreguntaActivity.class);
                ListadoActivity.this.startActivity(myIntent);
            }
        });

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

        //Iniciamos el arraylist
        items = new ArrayList<>();
        miRepo.cargarPreguntas(myContext);
        items = miRepo.getMisPreguntas();
        //Damos la vuelta al arraylist para que se muestre la ultima entrada la primera
        Collections.reverse(items);

        //creamos el recyvlerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

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


        //Le asignamos movimiento a los cards, conocido como Swipe
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                if (direction == ItemTouchHelper.LEFT) { //if swipe left

                    Intent editintent = new Intent(ListadoActivity.this, CrearEditarPreguntaActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putInt("Codigo", items.get(position).getCodigo());

                    editintent.putExtras(bundle);

                    startActivity(editintent);

                    MyLog.d("deslizando a la izquierda", "Weputa");

                }


                    if (direction == ItemTouchHelper.RIGHT) { //if swipe right

                   String codigo = Integer.toString(items.get(position).getCodigo());
                   String enunciado =items.get(position).getEnunciado();
                   String Categoria =items.get(position).getCategoria();
                   String rC =items.get(position).getRespuestaCorrecta();
                   String rI1 =items.get(position).getRespuestaIncorrecta1();
                   String rI2 =items.get(position).getRespuestaIncorrecta2();
                   String rI3 =items.get(position).getRespuestaIncorrecta3();

                   Pregunta borrar = new Pregunta(codigo,enunciado,Categoria,rC,rI1,rI2,rI3);

                   Repositorio.eliminaPregunta(borrar,myContext);
                   onResume();

                        MyLog.e("deslizando a la derecha", "Weputa");


                    }

            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView); //establecemos el movimiento al recycler

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Muestra el RecyclerView en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyLog.d(TAG, "Finalizando OnResume");

    }

    @Override
    protected void onPause(





    ) {
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