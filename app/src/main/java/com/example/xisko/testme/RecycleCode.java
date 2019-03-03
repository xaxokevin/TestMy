package com.example.xisko.testme;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import com.example.xisko.testme.Persistencia.Repositorio;
import com.example.xisko.testme.Pregunta.Pregunta;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Locale;


public class RecycleCode {

    /**
     * Metodo que nos permite exportar a XML las preguntas
     * almacenadas en nuestra BD
     * @param myContext
     */
    public static void exportarXML(Context myContext) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/preguntasExportar");
        String fname = "preguntas.xml";
        File file = new File(myDir, fname);
        try {


            if (!myDir.exists()) {
                myDir.mkdirs();

            }
            if (file.exists())
                file.delete();


            FileWriter fw = new FileWriter(file);
            //Escribimos en el fichero un String
            fw.write(CreateXMLString());


            //Cierro el stream
            fw.close();


        } catch (Exception ex) {

        }


        String cadena = myDir.getAbsolutePath() + "/" + fname;
        Uri path = Uri.parse("file://" + cadena);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "abc@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Preguntas para Moodle");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Adjunto el archivo para importarlas a Moodle");
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
        myContext.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    /**
     * Metodo que nos permite importar desde un archivo XML
     * las preguntas
     * @param myContext
     * @param receivedIntent
     */
    public static void importarXML(Context myContext, Intent receivedIntent){

        //Creamos las variables de la pregunta que vamos a importar
        Pregunta p;
        String enunciado="";
        String categoria="";
        String correcta="";
        String incorrecta="";
        String incorrecta2="";
        String incorrecta3="";
        String foto=null;

        int contador=0;
        int cuentapreguntas =0;

        //Recibimos el intent
        //Intent receivedIntent = getIntent();

        //Si el intent es distinto de null
        if(receivedIntent != null) {
            //Recogemos la accion del intent
            String receivedAction = receivedIntent.getAction();

            //Si la accion del inten es igual a android.intent.action.SEND
            //Se ejecutará la lectura del archivo
            if(receivedAction == "android.intent.action.SEND"){

                Uri data = receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);

                try {

                    InputStream fis = myContext.getContentResolver().openInputStream(data);
                    XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
                    xppf.setNamespaceAware(false);
                    XmlPullParser parser = xppf.newPullParser();
                    parser.setInput(fis, null);

                    parser.nextTag();
                    parser.require(XmlPullParser.START_TAG, null, "quiz");

                    //Leyendo el documento

                    int act;
                    String tag="";

                    while((act=parser.next()) != XmlPullParser.END_DOCUMENT) {

                        switch (act) {
                            case XmlPullParser.START_TAG:

                                tag = parser.getName();

                                break;

                            case XmlPullParser.TEXT:
                                if(tag.equals("text"))
                                {

                                    if(contador==0){
                                        categoria= parser.getText();

                                        contador++;
                                    }
                                    else if(contador==1){
                                        enunciado= parser.getText();

                                        contador++;

                                    }
                                    else if(contador==2){

                                        contador++;
                                    }

                                    else if(contador==3){
                                        correcta= parser.getText();

                                        contador++;

                                    }
                                    else if(contador==4){
                                        incorrecta= parser.getText();

                                        contador++;

                                    }
                                    else if(contador==5){
                                        incorrecta2= parser.getText();



                                        contador++;

                                    }
                                    else if(contador==6){
                                        incorrecta3= parser.getText();



                                        //Como es el último dato que recuperamos de la pregunta la añadimos a la base de datos

                                        if(foto == null){

                                            p= new Pregunta(enunciado,categoria,correcta,incorrecta,incorrecta2,incorrecta3);
                                            Repositorio.insertar(p,myContext);


                                        }else{
                                            p= new Pregunta(enunciado,categoria,correcta,incorrecta,incorrecta2,incorrecta3, foto);
                                            Repositorio.insertarF(p,myContext);


                                        }

                                        cuentapreguntas++;
                                        contador=0;

                                    }
                                    else{

                                    }

                                }

                                if(tag.equals("file"))
                                {
                                    foto= parser.getText();
                                    System.out.println("Imagen: "+ foto);

                                }


                                tag="";
                                break;

                            case XmlPullParser.END_TAG:
                                if(parser.getName().equals("question"))
                                {

                                }
                                break;
                        }

                    }



                    fis.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                Toast.makeText(myContext, "Preguntas insertadas correctamante: "+cuentapreguntas, Toast.LENGTH_LONG).show();
            }
        }



    }

    /**
     * Metodo encargado de Crear el String formateado en XML
     * @return el xml bien formado
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String CreateXMLString() throws IllegalArgumentException, IllegalStateException, IOException
    {
        ArrayList<Pregunta> preguntasXML = new ArrayList<Pregunta>();
        preguntasXML= Repositorio.getMisPreguntas();

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
            xmlSerializer.startTag("", "text");
            xmlSerializer.text(p.getCategoria());
            xmlSerializer.endTag("", "text");
            xmlSerializer.endTag("", "category");

            xmlSerializer.endTag("", "question");

            //Pregunta de eleccion multiple

            xmlSerializer.startTag("", "question");
            xmlSerializer.attribute("", "type", "multichoice");

            xmlSerializer.startTag("", "name");
            xmlSerializer.startTag("", "text");
            xmlSerializer.text(p.getEnunciado());
            xmlSerializer.endTag("", "text");
            xmlSerializer.endTag("", "name");

            xmlSerializer.startTag("","questiontext");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.startTag("", "text");
            xmlSerializer.text("<![CDATA[ <p>"+p.getEnunciado()+"</p><p><img src='imagen.jpg' /></p>]]>");
            xmlSerializer.endTag("", "text");
            xmlSerializer.startTag("","file");
            xmlSerializer.attribute("", "name","imagen.jpg");
            xmlSerializer.attribute("", "path", "/");
            xmlSerializer.attribute("", "encoding", "base64");
            xmlSerializer.text( p.getPhoto());
            xmlSerializer.endTag("", "file");
            xmlSerializer.endTag("", "questiontext");

            xmlSerializer.startTag("","answernumbering");
            xmlSerializer.endTag("", "answernumbering");

            xmlSerializer.startTag("","answer");
            xmlSerializer.attribute("","fraction", "100");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.startTag("", "text");
            xmlSerializer.text(p.getRespuestaCorrecta());
            xmlSerializer.endTag("", "text");
            xmlSerializer.endTag("", "answer");

            xmlSerializer.startTag("","answer");
            xmlSerializer.attribute("","fraction", "0");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.startTag("", "text");
            xmlSerializer.text(p.getRespuestaIncorrecta1());
            xmlSerializer.endTag("", "text");
            xmlSerializer.endTag("", "answer");

            xmlSerializer.startTag("","answer");
            xmlSerializer.attribute("","fraction", "0");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.startTag("", "text");
            xmlSerializer.text(p.getRespuestaIncorrecta2());
            xmlSerializer.endTag("", "text");
            xmlSerializer.endTag("", "answer");

            xmlSerializer.startTag("","answer");
            xmlSerializer.attribute("","fraction", "0");
            xmlSerializer.attribute("", "format", "html");
            xmlSerializer.startTag("", "text");
            xmlSerializer.text(p.getRespuestaIncorrecta3());
            xmlSerializer.endTag("", "text");
            xmlSerializer.endTag("", "answer");

            xmlSerializer.endTag("","question");
        }

        //end tag <file>
        xmlSerializer.endTag("","quiz");



        xmlSerializer.endDocument();



        return writer.toString();


    }


    /**
     * Convierte las imagenes a base 64
     * @param bm
     * @return encodedImage
     */
    public static String conversoraImagen64(Bitmap bm){
        String encodedImage="";
        if(bm!=null) {
            Bitmap resized = Bitmap.createScaledBitmap(bm, 500, 500, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 100, baos);//bmisthebitmapobject
            byte[] b = baos.toByteArray();
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            return encodedImage;
        }else{
            return encodedImage;
        }

    }


  /*  public static void setLocale(Locale locale, Context context){
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
        } else{
            configuration.locale=locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            context.getApplicationContext().createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration,displayMetrics);
        }

    }*/

    /**
     * Metodo que establece el idioma en el que se va a mostrar la apliación
     * @param lng idioma
     * @param context contexto de la actividad
     */
    public static void setLocale2(String lng, Context context){
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lng)); // API 17+ only.
        res.updateConfiguration(conf, dm);
    }


}
