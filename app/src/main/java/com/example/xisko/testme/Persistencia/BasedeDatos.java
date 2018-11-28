package com.example.xisko.testme.Persistencia;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class BasedeDatos extends SQLiteOpenHelper {


        //Sentencia SQL para crear la tabla de Preguntas

        String sqlCreate = "CREATE TABLE Preguntas (codigo INTEGER PRIMARY KEY AUTOINCREMENT, enunciado TEXT,categoria TEXT, respuestaCorrecta TEXT, respuestaIncorrecta1 TEXT, respuestaIncorrecta2 TEXT, respuestaIncorrecta3 TEXT)";

    public BasedeDatos(Context contexto, String nombre, CursorFactory factory, int version){
        super(contexto, nombre, factory, version);
    }

    //Se ejecuta la sentencia SQL de creacion de la tabla
        @Override
        public void onCreate (SQLiteDatabase db){

        db.execSQL(sqlCreate);
    }

        @Override
        public void onUpgrade (SQLiteDatabase db,int versionAnterior, int versionNueva){


        //Se elimina la version anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Preguntas");

        //Se crea la nueva version de la tabla
        db.execSQL(sqlCreate);
    }

    }




