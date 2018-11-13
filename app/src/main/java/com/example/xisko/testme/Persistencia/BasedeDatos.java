package com.example.xisko.testme.Persistencia;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class BasedeDatos extends SQLiteOpenHelper {


        //Sentencia SQL para crear la tabla de Usuarios
        //String sqlCreate = "CREATE TABLE Preguntas (codigo INTEGER PRIMARY KEY AUTOINCREMENT, enunciado TEXT, respuestaCorrecta TEXT, respuestaIncorrecta1 TEXT, respuestaIncorrecta2 TEXT, respuestaIncorrecta3 TEXT)";
        String sqlCreate = "CREATE TABLE Preguntas (codigo INTEGER PRIMARY KEY AUTOINCREMENT, enunciado TEXT,categoria TEXT, respuestaCorrecta TEXT, respuestaIncorrecta1 TEXT, respuestaIncorrecta2 TEXT, respuestaIncorrecta3 TEXT)";

    public BasedeDatos(Context contexto, String nombre, CursorFactory factory, int version){
        super(contexto, nombre, factory, version);
    }

        @Override
        public void onCreate (SQLiteDatabase db){
        //Se ejecuta la sentencia SQL de creaciÃ³n de la tabla
        db.execSQL(sqlCreate);
    }

        @Override
        public void onUpgrade (SQLiteDatabase db,int versionAnterior, int versionNueva){
        //NOTA: Por simplicidad del ejemplo aquÃ­ utilizamos directamente la opciÃ³n de
        //      eliminar la tabla anterior y crearla de nuevo vacÃ­a con el nuevo formato.
        //      Sin embargo lo normal serÃ¡ que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este mÃ©todo deberÃ­a ser mÃ¡s elaborado.

        //Se elimina la versiÃ³n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Preguntas");

        //Se crea la nueva versiÃ³n de la tabla
        db.execSQL(sqlCreate);
    }

    }




