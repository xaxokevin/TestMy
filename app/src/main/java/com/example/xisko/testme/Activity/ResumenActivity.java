package com.example.xisko.testme.Activity;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Persistencia.Repositorio;
import com.example.xisko.testme.Pregunta.Pregunta;
import com.example.xisko.testme.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;


import static com.example.xisko.testme.Constantes.CODE_CAMERA_PERMISSION;
import static com.example.xisko.testme.Constantes.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION;
import static com.example.xisko.testme.Constantes.NETWORK_SWITCH_FILTER;

public class ResumenActivity extends AppCompatActivity {

    Context myContext;
    ConstraintLayout constraint;
    private BroadcastReceiver receiver;





    private static final String TAG = "ResumenActivity";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Intent myIntent;

        switch (item.getItemId()) {
            case R.id.action_nueva:
                MyLog.i("ActionBar", "Nuevo!");
                myIntent = new Intent(ResumenActivity.this, CrearEditarPreguntaActivity.class);
                ResumenActivity.this.startActivity(myIntent);
                return true;
            case R.id.action_settings:


                MyLog.i("ActionBar", "Ajustes!");

                return true;
            case R.id.action_acercade:
                MyLog.i("ActionBar", "Acerca de");

                myIntent = new Intent(ResumenActivity.this, AcercaDeActivity.class);
                ResumenActivity.this.startActivity(myIntent);
                return true;

            case R.id.action_listado:
                MyLog.i("ActionBar", "Listado");

                myIntent = new Intent(ResumenActivity.this, ListadoActivity.class);
                ResumenActivity.this.startActivity(myIntent);
                return true;
            case R.id.action_exportar:
                Repositorio.cargarPreguntas(myContext);
                exportarXML();
                MyLog.i("ActionBar", "Exportar");


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        myContext = this;
        constraint = findViewById(R.id.constraint);
        compruebaPermisosEscritura();
        compruebaPermisosCamera();
        TextView pregunta = findViewById(R.id.numero_preguntas);
        TextView compartido = findViewById(R.id.compartir);
        pregunta.setText("Hay un total de: " + Repositorio.getCantidadPreguntas(myContext) + " preguntas almacenadas en la base de datos.");




        this.importarXML();




    }


    @Override
    protected void onStart() {
        MyLog.d(TAG, "Iniciando OnStart");
        super.onStart();
        MyLog.d(TAG, "Finalizando OnStart");
    }

    @Override
    protected void onResume() {


        MyLog.d(TAG, "Iniciando OnResume");
        super.onResume();
        TextView pregunta = findViewById(R.id.numero_preguntas);
        pregunta.setText("Hay un total de: " + Repositorio.getCantidadPreguntas(myContext) + " preguntas almacenadas en la base de datos.");


        MyLog.d(TAG, "Finalizando OnResume");
    }

    @Override
    protected void onPause() {
        MyLog.d(TAG, "Iniciando OnPause");
        super.onPause();

        MyLog.d(TAG, "Finalizando OnPause");
    }

    @Override
    protected void onStop() {
        MyLog.d(TAG, "Iniciando OnStop");
        super.onStop();
        MyLog.d(TAG, "Finalizando OnStop");
    }

    @Override
    protected void onRestart() {
        MyLog.d(TAG, "Iniciando OnRestart");
        super.onRestart();
        MyLog.d(TAG, "Finalizando OnRestart");
    }

    @Override
    protected void onDestroy() {
        MyLog.d(TAG, "Iniciando OnDestroy");

        super.onDestroy();


        MyLog.d(TAG, "Finalizando OnDestroy");
    }

    // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
    // En las versiones anteriores no es posible hacerlo
    // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
    // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
    private void compruebaPermisosEscritura() {
        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        MyLog.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);


        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(ResumenActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);


            } else {

                MyLog.e("Permisos: ", "Rechazados");

            }
        } else {

            MyLog.e("Permisos: ", "Rechazados");
        }
    }

    private void compruebaPermisosCamera() {
        int CameraPermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.CAMERA);
        MyLog.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + CameraPermission);


        if (CameraPermission != PackageManager.PERMISSION_GRANTED) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                ActivityCompat.requestPermissions(ResumenActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_PERMISSION);

            } else {

                MyLog.e("Permisos: ", "Rechazados");

            }
        } else {

            MyLog.e("Permisos: ", "Rechazados");
        }

    }


    //Maneja la respuesta del compruebaPermisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    MyLog.e("Permisos: ", "Rechazados");

                }

                break;


            case CODE_CAMERA_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                    MyLog.e("Permisos: ", "Rechazados");

                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void exportarXML() {
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
            MyLog.d("aqui estoy", Repositorio.CreateXMLString());
            fw.write(Repositorio.CreateXMLString());


            //Cierro el stream
            fw.close();


        } catch (Exception ex) {
            MyLog.e("Ficheros", "Error al escribir fichero a memoria interna");
        }


        String cadena = myDir.getAbsolutePath() + "/" + fname;
        Uri path = Uri.parse("file://" + cadena);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "abc@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Preguntas para Moodle");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Adjunto el archivo para importarlas a Moodle");
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    public void importarXML(){

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

        //Recibimos el intent
        Intent receivedIntent = getIntent();

        //Si el intent es distinto de null
        if(receivedIntent != null) {
            //Recogemos la accion del intent
            String receivedAction = receivedIntent.getAction();

            //Si la accion del inten es igual a android.intent.action.SEND
            //Se ejecutará la lectura del archivo
            if(receivedAction == "android.intent.action.SEND"){

                Uri data = receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);

                try {

                    InputStream fis = getContentResolver().openInputStream(data);
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
                                        System.out.println("categoria: "+ categoria);
                                        contador++;
                                    }
                                    else if(contador==1){
                                        enunciado= parser.getText();
                                        System.out.println("Enunciado: "+ enunciado);
                                        contador++;

                                    }
                                    else if(contador==2){

                                        contador++;
                                    }

                                    else if(contador==3){
                                        correcta= parser.getText();
                                        System.out.println("Correcta: "+ correcta);
                                        contador++;

                                    }
                                    else if(contador==4){
                                        incorrecta= parser.getText();
                                        System.out.println("Incorrecta1: "+ incorrecta);
                                        contador++;

                                    }
                                    else if(contador==5){
                                        incorrecta2= parser.getText();
                                        System.out.println("Incorrecta2: "+ incorrecta2);


                                        contador++;

                                    }
                                    else if(contador==6){
                                        incorrecta3= parser.getText();
                                        System.out.println("Incorrecta3: "+ incorrecta3);


                                        //Como es el último dato que recuperamos de la pregunta la añadimos a la base de datos

                                        if(foto == null){

                                            p= new Pregunta(enunciado,categoria,correcta,incorrecta,incorrecta2,incorrecta3);
                                            Repositorio.insertar(p,myContext);
                                            MyLog.w(TAG,"Insertado correctamente en BD");

                                        }else{
                                            p= new Pregunta(enunciado,categoria,correcta,incorrecta,incorrecta2,incorrecta3, foto);
                                            Repositorio.insertarF(p,myContext);
                                            MyLog.w(TAG,"Insertado correctamente en BD");

                                        }

                                        contador=0;

                                    }
                                    else{
                                        MyLog.w(TAG,"Error insertando en BD");
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
                                    MyLog.w(TAG,"Finalizado el archivo");
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
            }
        }


    }



    }

