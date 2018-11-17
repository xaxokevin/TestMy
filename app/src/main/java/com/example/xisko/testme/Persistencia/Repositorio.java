package com.example.xisko.testme.Persistencia;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xisko.testme.Activity.ListadoActivity;
import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Pregunta;

import java.util.ArrayList;

public class Repositorio {

    public Repositorio() {
    }

    private ArrayList<Pregunta> misPreguntas;

    public Pregunta editaPregunta(int codigoPr, Context contexto){



        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        BasedeDatos usdbh =
                new BasedeDatos(contexto, "DBPreguntas.db", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Preguntas WHERE codigo = codigoPr", null);

        String Enunciado= c.getString( c.getColumnIndex("enunciado"));
        String Categoria = c.getString( c.getColumnIndex("categoria"));
        String Correcta = c.getString( c.getColumnIndex("respuestaCorrecta"));
        String Incorrecta1 = c.getString( c.getColumnIndex("respuestaIncorrecta1"));
        String Incorrecta2 = c.getString( c.getColumnIndex("respuestaIncorrecta2"));
        String Incorrecta3 = c.getString( c.getColumnIndex("respuestaIncorrecta3"));
        Pregunta p = new Pregunta(Enunciado, Categoria,Correcta,Incorrecta1,Incorrecta2,Incorrecta3);

        MyLog.d("Edita pregunta","Que he creado el objeto putoooss");


        return p;
    }




    public static boolean insertar(Pregunta p, Context contexto) {

        boolean valor = true;

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        BasedeDatos usdbh =
                new BasedeDatos(contexto, "DBPreguntas.db", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            //Insertamos 5 usuarios de ejemplo

            db.execSQL("INSERT INTO Preguntas (enunciado, categoria, respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2, respuestaIncorrecta3)"+
                    "VALUES ('" + p.getEnunciado() + "', '" + p.getCategoria() + "', '"+ p.getRespuestaCorrecta()+"', '"+ p.getRespuestaIncorrecta1()+"', '"+p.getRespuestaIncorrecta2()+"', '"+p.getRespuestaIncorrecta3()+"')");
            //db.execSQL("INSERT INTO Preguntas (enunciado, respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2, respuestaIncorrecta3)"+
            //   "VALUES ('" + p.getEnunciado() + "','"+ p.getRespuestaCorrecta()+"', '"+ p.getRespuestaIncorrecta1()+"', '"+p.getRespuestaIncorrecta2()+"', '"+p.getRespuestaIncorrecta3()+"')");

            //codigo, enunciado,categoria, respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2 , respuestaIncorrecta3
            //Cerramos la base de datos
            db.close();
        } else {
            valor = false;
        }

        return valor;
    }


    public void cargarPreguntas(Context contexto){

        misPreguntas = new ArrayList<>();


        BasedeDatos usdbh =
                new BasedeDatos(contexto, "DBPreguntas.db", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Preguntas", null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String Enunciado= c.getString( c.getColumnIndex("enunciado"));
                String Categoria = c.getString( c.getColumnIndex("categoria"));
                String Correcta = c.getString( c.getColumnIndex("respuestaCorrecta"));
                String Incorrecta1 = c.getString( c.getColumnIndex("respuestaIncorrecta1"));
                String Incorrecta2 = c.getString( c.getColumnIndex("respuestaIncorrecta2"));
                String Incorrecta3 = c.getString( c.getColumnIndex("respuestaIncorrecta3"));
                Pregunta p = new Pregunta(Enunciado, Categoria,Correcta,Incorrecta1,Incorrecta2,Incorrecta3);
                misPreguntas.add(p);
                MyLog.d("Repositoriooooooooooooooooooo", p.getEnunciado());
            } while(c.moveToNext());
        }






    }



    public ArrayList<Pregunta> getMisPreguntas() {

        return misPreguntas;
    }

    public void setMisPreguntas(ArrayList<Pregunta> misPreguntas) {
        this.misPreguntas = misPreguntas;
    }
}
