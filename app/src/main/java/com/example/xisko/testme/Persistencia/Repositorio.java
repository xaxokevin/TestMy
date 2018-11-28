package com.example.xisko.testme.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Pregunta.Pregunta;

import java.util.ArrayList;

import static com.example.xisko.testme.Constantes.basedeDatos;

public class Repositorio {

    public Repositorio() {
    }

    private ArrayList<Pregunta> misPreguntas;

    private static ArrayList<String> misCategorias;




    public static boolean insertar(Pregunta p, Context contexto) {

        boolean valor = true;

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        BasedeDatos usdbh =
                new BasedeDatos(contexto, basedeDatos, null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            //Insertamos 5 usuarios de ejemplo

            db.execSQL("INSERT INTO Preguntas (enunciado, categoria, respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2, respuestaIncorrecta3)"+
                    "VALUES ('" + p.getEnunciado() + "', '" + p.getCategoria() + "', '"+ p.getRespuestaCorrecta()+"', '"+ p.getRespuestaIncorrecta1()+"', '"+p.getRespuestaIncorrecta2()+"', '"+p.getRespuestaIncorrecta3()+"')");

            //Cerramos la base de datos
            db.close();
        } else {
            valor = false;
        }

        return valor;
    }


    public static void cargarCategorias(Context context){


       misCategorias= new ArrayList<>();


        BasedeDatos usdbh =
                new BasedeDatos(context, basedeDatos, null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT DISTINCT categoria FROM Preguntas", null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                String Categoria = c.getString( c.getColumnIndex("categoria"));


                misCategorias.add(Categoria);

            } while(c.moveToNext());
        }



    }


    public void cargarPreguntas(Context contexto){

        misPreguntas = new ArrayList<Pregunta>();


        BasedeDatos usdbh =
                new BasedeDatos(contexto, basedeDatos, null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Preguntas", null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String codigo=c.getString( c.getColumnIndex("codigo"));
                String Enunciado= c.getString( c.getColumnIndex("enunciado"));
                String Categoria = c.getString( c.getColumnIndex("categoria"));
                String Correcta = c.getString( c.getColumnIndex("respuestaCorrecta"));
                String Incorrecta1 = c.getString( c.getColumnIndex("respuestaIncorrecta1"));
                String Incorrecta2 = c.getString( c.getColumnIndex("respuestaIncorrecta2"));
                String Incorrecta3 = c.getString( c.getColumnIndex("respuestaIncorrecta3"));
                Pregunta p = new Pregunta(codigo,Enunciado, Categoria,Correcta,Incorrecta1,Incorrecta2,Incorrecta3);
                misPreguntas.add(p);
                MyLog.d("Repositoriooooooooooooooooooo", p.getEnunciado());
                MyLog.i("Codigo preguntasss",Integer.toString(p.getCodigo()));
            } while(c.moveToNext());

        }






    }

    public static boolean actualizarPregunta(Pregunta p,Context contexto){

        boolean valor=true;

        //Abrimos la basededatos en modo escritura
        BasedeDatos sdbh=
                new BasedeDatos(contexto,basedeDatos,null,1);

        SQLiteDatabase db= sdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db!=null)
        {

            //Establecemosloscampos-valoresaactualizar
            ContentValues valores=new ContentValues();

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



    public ArrayList<Pregunta> getMisPreguntas() {

        return misPreguntas;
    }

    public static ArrayList<String>getMisCategorias(){

        return misCategorias;
    }

    public void setMisPreguntas(ArrayList<Pregunta> misPreguntas) {
        this.misPreguntas = misPreguntas;
    }
}
