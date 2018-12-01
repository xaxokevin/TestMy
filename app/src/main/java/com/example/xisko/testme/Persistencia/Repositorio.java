package com.example.xisko.testme.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Pregunta.Pregunta;

import java.util.ArrayList;

import static com.example.xisko.testme.Constantes.Preguntas;
import static com.example.xisko.testme.Constantes.basedeDatos;


public class Repositorio {

    public Repositorio() {
    }

    private ArrayList<Pregunta> misPreguntas;

    private static ArrayList<String> misCategorias;






    //Metodo que inserta registros en la BD
    public static boolean insertar(Pregunta p, Context contexto) {

        boolean valor = true;

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        BasedeDatos usdbh =
                new BasedeDatos(contexto, basedeDatos, null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {

            db.execSQL("INSERT INTO '"+Preguntas+"' (enunciado, categoria, respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2, respuestaIncorrecta3)"+
                    "VALUES ('" + p.getEnunciado() + "', '" + p.getCategoria() + "', '"+ p.getRespuestaCorrecta()+"', '"+ p.getRespuestaIncorrecta1()+"', '"+p.getRespuestaIncorrecta2()+"', '"+p.getRespuestaIncorrecta3()+"')");

            //Cerramos la base de datos
            db.close();
        } else {
            valor = false;
        }

        return valor;
    }


    //Metodo que saca unicamente las categorias que tenemos almacenadas en la BD.
    //Luego seran cargadas en un spinner
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


    //Metodo que guardaa las preguntas de la base de datos en un array
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

    //Actualiza la pregunta seleccionada en el recyclerView
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
            valores.put("respuestaCorrecta",p.getRespuestaCorrecta());
            valores.put("respuestaIncorrecta1",p.getRespuestaIncorrecta1());
            valores.put("respuestaIncorrecta2",p.getRespuestaIncorrecta2());
            valores.put("respuestaIncorrecta3",p.getRespuestaIncorrecta3());

            //Actualizamoselregistroenlabasededatos
            //String[]args=new String[]{Integer.toString(p.getCodigo())};
            MyLog.e("Valor del codigo de actualizar",Integer.toString(p.getCodigo()));
            db.update("Preguntas",valores,"codigo='"+p.getCodigo()+"'",null);

            //Cerramoslabasededatos
            db.close();
        }
        else{valor=false;}

        return valor;
    }


    public static void eliminaPregunta(Pregunta p, Context context){

        //Abrimos la basededatos en modo escritura
        BasedeDatos sdbh=
                new BasedeDatos(context,basedeDatos,null,1);

        SQLiteDatabase db= sdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db!=null)
        {


            MyLog.e("Valor del codigo a eliminar",Integer.toString(p.getCodigo()));
            db.execSQL("DELETE FROM '"+Preguntas+"' WHERE codigo='"+p.getCodigo()+"' ");
            //Cerramoslabasededatos
            db.close();
        }
        else{

            MyLog.v("Error al eliminar","Codigo de pregunta: '"+Integer.toString(p.getCodigo()));
        }


    }

    //Nos devuelva la pregunta que quermos actualizar
    public static Pregunta buscarPregunta(int codi, Context context){

        Pregunta p = null;



        BasedeDatos usdbh =
                new BasedeDatos(context, basedeDatos, null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Preguntas WHERE codigo='"+codi+"'", null);



        if (c.moveToFirst()) {
            String Enunciado = c.getString(c.getColumnIndex("enunciado"));
            String Categoria = c.getString(c.getColumnIndex("categoria"));
            String Correcta = c.getString(c.getColumnIndex("respuestaCorrecta"));
            String Incorrecta1 = c.getString(c.getColumnIndex("respuestaIncorrecta1"));
            String Incorrecta2 = c.getString(c.getColumnIndex("respuestaIncorrecta2"));
            String Incorrecta3 = c.getString(c.getColumnIndex("respuestaIncorrecta3"));
            p = new Pregunta(Enunciado, Categoria, Correcta, Incorrecta1, Incorrecta2, Incorrecta3);
            MyLog.d("Pregunta encontrada", p.getEnunciado());


        }
        c.close();
        return p;

    }





    public ArrayList<Pregunta> getMisPreguntas() {

        return misPreguntas;
    }

    public static ArrayList<String>getMisCategorias(){

        return misCategorias;
    }


    //Nos devuelve la cantidad de preguntas que tenemos almacenadas
    public String getCantidadPreguntas(Context context) {

        String numero = "";

        BasedeDatos sdbh =
                new BasedeDatos(context, basedeDatos, null, 1);
        SQLiteDatabase db= sdbh.getWritableDatabase();

        String countQuery = "SELECT COUNT(codigo)  FROM "+ Preguntas;

        Cursor c = db.rawQuery(countQuery, null);

        if (c.moveToFirst()) {
            numero = c.getString(0);
        }

        c.close();

        return numero;
    }

}
