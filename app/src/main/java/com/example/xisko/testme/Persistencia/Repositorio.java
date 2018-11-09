package com.example.xisko.testme.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.xisko.testme.Pregunta;

public class Repositorio {


    public void insertar(Pregunta pregunta, Context Mycontext) {


        //Abrimos la base de datos 'DBPreguntas' en modo escritura
        BasedeDatos bd = new BasedeDatos(Mycontext, "preguntas", null, 1);

        SQLiteDatabase db = bd.getWritableDatabase();


        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("COLUMN_NOMBRE",pregunta.getNombre() );
        nuevoRegistro.put("COLUMN_CORRECTA", pregunta.getCorrecta());
        nuevoRegistro.put("COLUMN_INCORRECTA1", pregunta.getIncorrecta1());
        nuevoRegistro.put("COLUMN_INCORRECTA2", pregunta.getIncorrecta2());
        nuevoRegistro.put("COLUMN_INCORRECTA3", pregunta.getIncorrecta3());
        db.insert("TABLE_NAME", null, nuevoRegistro);
    }
}
