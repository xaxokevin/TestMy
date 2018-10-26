package com.example.xisko.testme;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

public class NuevaPreguntaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_pregunta);


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
                if(pregunta.getText().toString().isEmpty()){
                    pregunta.setBackgroundColor(Color.rgb(255,64,64));

                    Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                            .show();

                } if (correcta.getText().toString().isEmpty()){

                    correcta.setBackgroundColor(Color.rgb(255,64,64));

                    Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                            .show();


                } if (incorrecta1.getText().toString().isEmpty()){

                    incorrecta1.setBackgroundColor(Color.rgb(255,64,64));

                    Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                            .show();


                }if (incorrecta2.getText().toString().isEmpty()){

                    incorrecta2.setBackgroundColor(Color.rgb(255,64,64));

                    Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                            .show();


                }if (incorrecta3.getText().toString().isEmpty()){

                    incorrecta3.setBackgroundColor(Color.rgb(255,64,64));

                    Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                            .show();


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
}
