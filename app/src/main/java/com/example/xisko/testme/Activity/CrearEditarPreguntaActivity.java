package com.example.xisko.testme.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Persistencia.Repositorio;
import com.example.xisko.testme.Pregunta;
import com.example.xisko.testme.R;

public class CrearEditarPreguntaActivity extends AppCompatActivity {

    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private Context myContext;
     ConstraintLayout constraint;
     public Repositorio mirepo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_pregunta);
        myContext = CrearEditarPreguntaActivity.this;
        constraint = findViewById(R.id.constraint);



        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        String[] contenido ={"PHP","Java","Redes","Sistemas","Android"};
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,contenido));

         final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final EditText  pregunta = findViewById(R.id.titulo);
               final EditText correcta = findViewById(R.id.titulo2);
               final EditText incorrecta1 = findViewById(R.id.titulo3);
               final EditText incorrecta2 = findViewById(R.id.titulo4);
               final EditText incorrecta3 = findViewById(R.id.titulo5);

               compruebaPermisos();

        if(compruebaPregunta(v, pregunta, correcta, incorrecta1, incorrecta2, incorrecta3)== true){

            Pregunta mipregunta = new Pregunta(pregunta.getText().toString(), correcta.getText().toString(),
                    incorrecta1.getText().toString(), incorrecta2.getText().toString(),incorrecta3.getText().toString());



            mirepo.insertar(mipregunta,myContext);


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





            }









        });




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
        if(pregunta.getText().toString().isEmpty()){
            pregunta.setBackgroundColor(Color.rgb(255,64,64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        }else
        if (correcta.getText().toString().isEmpty()){

            correcta.setBackgroundColor(Color.rgb(255,64,64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;


        }else
        if (incorrecta1.getText().toString().isEmpty()){

            incorrecta1.setBackgroundColor(Color.rgb(255,64,64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        }else
        if (incorrecta2.getText().toString().isEmpty()){

            incorrecta2.setBackgroundColor(Color.rgb(255,64,64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        }else
        if (incorrecta3.getText().toString().isEmpty()){

            incorrecta3.setBackgroundColor(Color.rgb(255,64,64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        } else{





            return true;

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
