package com.example.xisko.testme.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.database.sqlite.SQLiteDatabase;
import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Persistencia.BasedeDatos;
import com.example.xisko.testme.Persistencia.Repositorio;
import com.example.xisko.testme.Pregunta;
import com.example.xisko.testme.R;

import java.util.ArrayList;

public class CrearEditarPreguntaActivity extends AppCompatActivity implements View.OnClickListener {

    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private Context myContext;
    ConstraintLayout constraint;
    public Repositorio mirepo;
    private ArrayAdapter<String>adapter;
    private Spinner spinnerCategoria;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_pregunta);
        myContext = this;
        constraint = findViewById(R.id.constraint);


        //Obtenemos las categorías y las añadimos al Spinner
        ArrayList<String> items = new ArrayList<String>();

        Repositorio.cargarCategorias(myContext);

        items = Repositorio.getMisCategorias();

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerCategoria = (Spinner) findViewById(R.id.spinner);
        spinnerCategoria.setAdapter(adapter);
//Declaramos los botones que vamos a utilizar en el activity
        Button cat = (Button) findViewById(R.id.mascategoria);
        cat.setOnClickListener(this);


        FloatingActionButton guardar = (FloatingActionButton) findViewById(R.id.guardar);
        guardar.setImageResource(R.drawable.ic_loupe_grey600_48dp);
        guardar.setOnClickListener(this);
    }

    private void compruebaPermisos() {
        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        MyLog.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // Permiso denegado
            // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
            // En las versiones anteriores no es posible hacerlo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(CrearEditarPreguntaActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
                // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
            } else {
                Snackbar.make(constraint, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG)
                        .show();
            }
        } else {
            // Permiso aceptado
            Snackbar.make(constraint, getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    private boolean compruebaPregunta(View v, EditText pregunta, EditText correcta, EditText incorrecta1, EditText incorrecta2, EditText incorrecta3) {
        if(pregunta.getText().toString().isEmpty())  {
            pregunta.setBackgroundColor(Color.rgb(255,64,64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        }
        if (correcta.getText().toString().isEmpty()){

            correcta.setBackgroundColor(Color.rgb(255,64,64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;


        }
        if (incorrecta1.getText().toString().isEmpty()){

            incorrecta1.setBackgroundColor(Color.RED);

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        }
        if (incorrecta2.getText().toString().isEmpty()){

            incorrecta2.setBackgroundColor(Color.rgb(255,64,64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        }
        if (incorrecta3.getText().toString().isEmpty()){

            incorrecta3.setBackgroundColor(Color.rgb(255,64,64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        } else{

            return true;

        }


    }



    public static boolean actualizarPregunta(Pregunta p,Context contexto){

        boolean valor=true;

        //Abrimoslabasededatos'DBPreguntas'enmodoescritura
        BasedeDatos sdbh=
                new BasedeDatos(contexto,"DBPreguntas.db",null,1);

        SQLiteDatabase db= sdbh.getWritableDatabase();

        //Sihemosabiertocorrectamentelabasededatos
        if(db!=null)
        {

        //Establecemosloscampos-valoresaactualizar
            ContentValues valores=new ContentValues();
            //valores.put("codigo",p.getCodigo());
            valores.put("enunciado",p.getEnunciado());
            valores.put("categoria",p.getCategoria());
            valores.put("respuestacorrecta",p.getRespuestaCorrecta());
            valores.put("respuestaincorrecta1",p.getRespuestaIncorrecta1());
            valores.put("respuestaincorrecta2",p.getRespuestaIncorrecta2());
            valores.put("respuestaincorrecta3",p.getRespuestaIncorrecta3());

            //Actualizamoselregistroenlabasededatos
            String[]args=new String[]{Integer.toString(p.getCodigo())};
            db.update("Preguntas",valores,"codigo=?",args);

        //Cerramoslabasededatos
            db.close();
        }
        else{valor=false;}

        return valor;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.guardar:


                final EditText  pregunta = findViewById(R.id.titulo);
                final EditText correcta = findViewById(R.id.titulo2);
                final EditText incorrecta1 = findViewById(R.id.titulo3);
                final EditText incorrecta2 = findViewById(R.id.titulo4);
                final EditText incorrecta3 = findViewById(R.id.titulo5);
                final String spinner = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();

                compruebaPermisos();

                if(compruebaPregunta(v, pregunta, correcta, incorrecta1, incorrecta2, incorrecta3)== true){

                    Pregunta mipregunta = new Pregunta(pregunta.getText().toString(),spinner, correcta.getText().toString(),
                            incorrecta1.getText().toString(), incorrecta2.getText().toString(),incorrecta3.getText().toString());



                    mirepo.insertar(mipregunta,myContext);

                    finish();


                }

                pregunta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {

                            pregunta.setBackgroundColor(Color.rgb(250,250,250));

                        }
                    }
                });

                correcta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {

                            correcta.setBackgroundColor(Color.rgb(250,250,250));

                        }
                    }
                });

                incorrecta1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {

                            incorrecta1.setBackgroundColor(Color.rgb(250,250,250));

                        }
                    }
                });

                incorrecta2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {

                            incorrecta2.setBackgroundColor(Color.rgb(250,250,250));

                        }
                    }
                });

                incorrecta3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {

                            incorrecta3.setBackgroundColor(Color.rgb(250,250,250));

                        }
                    }
                });


                break;

            case R.id.mascategoria:



                //Recuperación de la vista del AlertDialog a partir del layout de la Actividad
                LayoutInflater layoutActivity=LayoutInflater.from(myContext);
                View viewAlertDialog=layoutActivity.inflate(R.layout.alert_dialog,null);

                //Definición del AlertDialog
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(myContext);

                //Asignación del AlertDialog a su vista
                alertDialog.setView(viewAlertDialog);

                //Recuperación del EditTextdel AlertDialog
                final EditText dialogInput=(EditText)viewAlertDialog.findViewById(R.id.dialogInput);

                //Configuración del AlertDialog
                alertDialog
                        .setCancelable(false)
                        //BotónAñadir
                        .setPositiveButton(getResources().getString(R.string.add),
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialogBox,int id){
                                        adapter.add(dialogInput.getText().toString());
                                        spinnerCategoria.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                    }
                                })
                        //BotónCancelar
                        .setNegativeButton(getResources().getString(R.string.cancel),
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialogBox,int id){
                                        dialogBox.cancel();
                                    }
                                }).create()
                        .show();





                break;



            default:
                break;
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permiso aceptado
//                    Snackbar.make(constraint, getResources().getString(R.string.write_permission_accepted), Snackbar.LENGTH_LONG)
//                            .show();
//
//                } else {
//                    // Permiso rechazado
//                    Snackbar.make(constraint, getResources().getString(R.string.write_permission_not_accepted), Snackbar.LENGTH_LONG)
//                            .show();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
// }
}
