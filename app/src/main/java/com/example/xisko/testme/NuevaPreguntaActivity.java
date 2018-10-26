package com.example.xisko.testme;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;

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
                EditText pregunta = findViewById(R.id.titulo);
                if(pregunta.getText().toString().isEmpty()){
                    pregunta.setBackgroundColor(0xFF00FF00);

                    Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                            .show();

                }

            }
        });



    }
}
