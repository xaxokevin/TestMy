package com.example.xisko.testme.Activity;

import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Persistencia.Repositorio;
import com.example.xisko.testme.Pregunta.Pregunta;
import com.example.xisko.testme.Pregunta.PreguntaAdapter;
import com.example.xisko.testme.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ListadoActivity extends AppCompatActivity {


    private static final String TAG = "ListadoActivity";
    private ArrayList<Pregunta> items;
    private Repositorio miRepo = new Repositorio();
    private Context myContext;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myContext = ListadoActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        textView =  findViewById(R.id.no_preguntas);

        //Añade un toolbar a la actividad
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        if(!items.isEmpty()) {
            textView.setVisibility(View.INVISIBLE);

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
                    Toast.makeText(ListadoActivity.this, "Desliza a la izquierda para editar " + " o " + " Desliza a la derecha para eliminar", Toast.LENGTH_SHORT)
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
                    final int position = viewHolder.getAdapterPosition(); //obtiene la posicion

                    if (direction == ItemTouchHelper.LEFT) {
                        //si deslizamos a la izquierda vamos a editar la pregunta

                        Intent editintent = new Intent(ListadoActivity.this, CrearEditarPreguntaActivity.class);

                        Bundle bundle = new Bundle();

                        bundle.putInt("Codigo", items.get(position).getCodigo());

                        editintent.putExtras(bundle);

                        startActivity(editintent);

                        MyLog.d("deslizando a la izquierda", "Weputa");

                    }


                    if (direction == ItemTouchHelper.RIGHT) {
                        //si deslizamos a la derecha vamos a eliminar la pregunta,
                        // pero antes debemos confirmar esta accion


                        //Recuperación de la vista del AlertDialog a partir del layout de la Actividad
                        LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                        View viewAlertDialog = layoutActivity.inflate(R.layout.eliminar_dialog, null);

                        //Definición del AlertDialog
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

                        //Asignación del AlertDialog a su vista
                        alertDialog.setView(viewAlertDialog);


                        //Configuración del AlertDialog
                        alertDialog
                                .setCancelable(false)
                                //BotónAñadir
                                .setPositiveButton(getResources().getString(R.string.delete),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {

                                                String codigo = Integer.toString(items.get(position).getCodigo());
                                                String enunciado = items.get(position).getEnunciado();
                                                String Categoria = items.get(position).getCategoria();
                                                String rC = items.get(position).getRespuestaCorrecta();
                                                String rI1 = items.get(position).getRespuestaIncorrecta1();
                                                String rI2 = items.get(position).getRespuestaIncorrecta2();
                                                String rI3 = items.get(position).getRespuestaIncorrecta3();
                                                String photo = items.get(position).getPhoto();

                                                Pregunta borrar = new Pregunta(codigo, enunciado, Categoria, rC, rI1, rI2, rI3,photo);

                                                Repositorio.eliminaPregunta(borrar, myContext);

                                                onResume();

                                            }
                                        })
                                //BotónCancelar
                                .setNegativeButton(getResources().getString(R.string.cancel),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                dialogBox.cancel();
                                                onResume();
                                            }
                                        }).create()
                                .show();


                    }

                }

            };

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView); //establecemos el movimiento al recycler

            // Muestra el RecyclerView en vertical
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        }else{

            textView.setVisibility(View.VISIBLE);

        }

        MyLog.d(

                TAG, "Finalizando OnResume");

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // añade al menu un item
        getMenuInflater().inflate(R.menu.listado_menu, menu);
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
    public void onBackPressed(){

        finish();
    }

    @Override
    public boolean onNavigateUp(){

        finish();
        return true;
    }



}