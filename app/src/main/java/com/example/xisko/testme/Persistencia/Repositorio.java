package com.example.xisko.testme.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Xml;

import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Pregunta.Pregunta;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import static com.example.xisko.testme.Constantes.DB_VERSION;
import static com.example.xisko.testme.Constantes.Preguntas;
import static com.example.xisko.testme.Constantes.basedeDatos;


public class Repositorio  {

    public Repositorio() {


    }

    private static ArrayList<Pregunta> misPreguntas;



    private static ArrayList<String> misCategorias;









    //Metodo que inserta registros en la BD sin imagen
    public static boolean insertar(Pregunta p, Context contexto) {

        boolean valor = true;

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        BasedeDatos usdbh =
                new BasedeDatos(contexto, basedeDatos, null, DB_VERSION);

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

    //Metodo que inserta registros en la BD con imagen
    public static boolean insertarF(Pregunta p, Context contexto) {

        boolean valor = true;

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        BasedeDatos usdbh =
                new BasedeDatos(contexto, basedeDatos, null, DB_VERSION);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {

            db.execSQL("INSERT INTO '"+Preguntas+"' (enunciado, categoria, respuestaCorrecta, respuestaIncorrecta1, respuestaIncorrecta2, respuestaIncorrecta3, photo)"+
                    "VALUES ('" + p.getEnunciado() + "', '" + p.getCategoria() + "', '"+ p.getRespuestaCorrecta()+"', '"+ p.getRespuestaIncorrecta1()+"', '"+p.getRespuestaIncorrecta2()+"', '"+p.getRespuestaIncorrecta3()+"','"+p.getPhoto()+"')");

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
                new BasedeDatos(context, basedeDatos, null, DB_VERSION);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT DISTINCT categoria FROM '"+Preguntas+"'", null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {

                String Categoria = c.getString( c.getColumnIndex("categoria"));


                misCategorias.add(Categoria);

            } while(c.moveToNext());

            c.close();
            db.close();
        }



    }


    //Metodo que guardaa las preguntas de la base de datos en un array
    public  static void cargarPreguntas(Context contexto){

        misPreguntas = new ArrayList<Pregunta>();


        BasedeDatos sdbh =
                new BasedeDatos(contexto, basedeDatos, null, DB_VERSION);
        SQLiteDatabase db= sdbh.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM '"+Preguntas+"'", null);

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
                String photo = c.getString(c.getColumnIndex("photo"));


                Pregunta p = new Pregunta(codigo,Enunciado, Categoria,Correcta,Incorrecta1,Incorrecta2,Incorrecta3,photo);
                misPreguntas.add(p);
                MyLog.d("Repositoriooooooooooooooooooo", p.getEnunciado());
                MyLog.i("Codigo preguntasss",Integer.toString(p.getCodigo()));
            } while(c.moveToNext());

            c.close();

        }
        db.close();






    }

    //Actualiza la pregunta seleccionada en el recyclerView
    public static boolean actualizarPregunta(Pregunta p,Context contexto){

        boolean valor=true;

        //Abrimos la basededatos en modo escritura
        BasedeDatos sdbh=
                new BasedeDatos(contexto,basedeDatos,null,DB_VERSION);

        SQLiteDatabase db= sdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db!=null)
        {

            //Establecemos los campos-valores a actualizar
            ContentValues valores=new ContentValues();

            valores.put("enunciado",p.getEnunciado());
            valores.put("categoria",p.getCategoria());
            valores.put("respuestaCorrecta",p.getRespuestaCorrecta());
            valores.put("respuestaIncorrecta1",p.getRespuestaIncorrecta1());
            valores.put("respuestaIncorrecta2",p.getRespuestaIncorrecta2());
            valores.put("respuestaIncorrecta3",p.getRespuestaIncorrecta3());
            valores.put("photo",p.getPhoto());

            //Actualizamoselregistroenlabasededatos
            String[]args=new String[]{Integer.toString(p.getCodigo())};
            MyLog.e("Valor del codigo de actualizar",Integer.toString(p.getCodigo()));
            db.update("Preguntas",valores,"codigo=?",args);

            //Cerramoslabasededatos
            db.close();
        }
        else{valor=false;}

        return valor;
    }


    public static void eliminaPregunta(Pregunta p, Context context){

        //Abrimos la basededatos en modo escritura
        BasedeDatos sdbh=
                new BasedeDatos(context,basedeDatos,null,DB_VERSION);

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
                new BasedeDatos(context, basedeDatos, null, DB_VERSION);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Preguntas WHERE codigo='"+codi+"'", null);



        if (c.moveToFirst()) {
            String Enunciado = c.getString(c.getColumnIndex("enunciado"));
            String Categoria = c.getString(c.getColumnIndex("categoria"));
            String Correcta = c.getString(c.getColumnIndex("respuestaCorrecta"));
            String Incorrecta1 = c.getString(c.getColumnIndex("respuestaIncorrecta1"));
            String Incorrecta2 = c.getString(c.getColumnIndex("respuestaIncorrecta2"));
            String Incorrecta3 = c.getString(c.getColumnIndex("respuestaIncorrecta3"));
            String foto = c.getString(c.getColumnIndex("photo"));
            p = new Pregunta(Enunciado, Categoria, Correcta, Incorrecta1, Incorrecta2, Incorrecta3, foto);
            MyLog.d("Pregunta encontrada", p.getEnunciado());


        }
        c.close();

        db.close();
        return p;



    }





    public static ArrayList<Pregunta> getMisPreguntas() {



        return misPreguntas;
    }

    public static ArrayList<String>getMisCategorias(){

        return misCategorias;
    }


    //Nos devuelve la cantidad de preguntas que tenemos almacenadas
    public static String getCantidadPreguntas(Context context) {



        BasedeDatos sdbh =
                new BasedeDatos(context, basedeDatos, null, DB_VERSION);
        SQLiteDatabase db= sdbh.getWritableDatabase();



        Cursor c = db.rawQuery("SELECT count(distinct codigo) FROM '"+Preguntas+"';", null);
        String cantidaP="";

        if (c.moveToFirst()) {
            cantidaP = c.getString(0);
        }

        c.close();

        db.close();

        return cantidaP;
    }

    @SuppressWarnings("null")
    public static String CreateXMLString() throws IllegalArgumentException, IllegalStateException, IOException
    {
        ArrayList<Pregunta> preguntasXML = new ArrayList<Pregunta>();
        preguntasXML= getMisPreguntas();


        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        xmlSerializer.setOutput(writer);

        //Start Document
        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);




        //Open Tag <file>
        xmlSerializer.startTag("", "quiz");

        for (Pregunta p: preguntasXML) {
            //Categoria de cada pregunta

            xmlSerializer.startTag("", "question");
            xmlSerializer.attribute("", "type", p.getCategoria());

            xmlSerializer.startTag("", "category");
            xmlSerializer.text(p.getCategoria());
            xmlSerializer.endTag("", "category");

            xmlSerializer.endTag("", "question");

            //Pregunta de eleccion multiple

            xmlSerializer.startTag("", "question");
            xmlSerializer.attribute("", "type", "multichoice");

            xmlSerializer.startTag("", "name");
            xmlSerializer.text(p.getEnunciado());
            xmlSerializer.endTag("", "name");

            xmlSerializer.startTag("","questiontext");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.text(p.getEnunciado());
            xmlSerializer.startTag("","file");
            xmlSerializer.attribute("", "name", p.getPhoto());
            xmlSerializer.attribute("", "path", "/");
            xmlSerializer.attribute("", "encoding", "base64");
            xmlSerializer.endTag("", "file");
            xmlSerializer.endTag("", "questiontext");

            xmlSerializer.startTag("","answernumbering");
            xmlSerializer.endTag("", "answernumbering");

            xmlSerializer.startTag("","answer");
            xmlSerializer.attribute("","fraction", "100");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.text(p.getRespuestaCorrecta());
            xmlSerializer.endTag("", "answer");

            xmlSerializer.startTag("","answer");
            xmlSerializer.attribute("","fraction", "0");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.text(p.getRespuestaIncorrecta1());
            xmlSerializer.endTag("", "answer");

            xmlSerializer.startTag("","answer");
            xmlSerializer.attribute("","fraction", "0");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.text(p.getRespuestaIncorrecta2());
            xmlSerializer.endTag("", "answer");

            xmlSerializer.startTag("","answer");
            xmlSerializer.attribute("","fraction", "0");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.text(p.getRespuestaIncorrecta3());
            xmlSerializer.endTag("", "answer");

            xmlSerializer.endTag("","question");
        }

        //end tag <file>
        xmlSerializer.endTag("","quiz");



        xmlSerializer.endDocument();

        MyLog.d("XML", writer.toString());

        return writer.toString();


    }




    ////////////////////////////////////PERMISOS///////////////////////////////





}
