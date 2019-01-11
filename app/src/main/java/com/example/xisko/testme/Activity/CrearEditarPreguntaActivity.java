package com.example.xisko.testme.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import com.example.xisko.testme.Log.MyLog;
import com.example.xisko.testme.Persistencia.Repositorio;
import com.example.xisko.testme.Pregunta.Pregunta;
import com.example.xisko.testme.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.xisko.testme.Constantes.CODE_WRITE_EXTERNAL_STORAGE_PERMISSION;

public class CrearEditarPreguntaActivity extends AppCompatActivity implements View.OnClickListener {

    private Context myContext;
    ConstraintLayout constraint;
    public Repositorio mirepo;
    private ArrayAdapter<String> adapter;
    private Spinner spinnerCategoria;
    private int codigoPregunta =-1;
    private static final int REQUEST_CAPTURE_IMAGE = 200;
    private static final int REQUEST_SELECT_IMAGE = 201;
    final String pathFotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/demoAndroid/";
    private Uri uri;



    public int getCodigoPregunta() {
        return codigoPregunta;
    }

    public void setCodigoPregunta(int codigoPregunta) {
        this.codigoPregunta = codigoPregunta;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_pregunta);
        myContext = this;
        constraint = findViewById(R.id.constraint);
        //toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final EditText pregunta = findViewById(R.id.titulo);
        final EditText correcta = findViewById(R.id.titulo2);
        final EditText incorrecta1 = findViewById(R.id.titulo3);
        final EditText incorrecta2 = findViewById(R.id.titulo4);
        final EditText incorrecta3 = findViewById(R.id.titulo5);




        //Obtenemos las categorías y las añadimos al Spinner
        ArrayList<String> items = new ArrayList<String>();

        Repositorio.cargarCategorias(myContext);

        items = Repositorio.getMisCategorias();

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerCategoria = (Spinner) findViewById(R.id.spinner);
        spinnerCategoria.setAdapter(adapter);
        //Declaramos los botones que vamos a utilizar en el activity
        Button cat = (Button) findViewById(R.id.mascategoria);
        cat.setOnClickListener(this);


        FloatingActionButton guardar = (FloatingActionButton) findViewById(R.id.guardar);
        guardar.setImageResource(R.drawable.ic_loupe_grey600_48dp);
        guardar.setOnClickListener(this);

        ImageView camara = (ImageView) findViewById(R.id.camara);
        camara.setOnClickListener(this);

        Button galeria = (Button) findViewById(R.id.galeria);
        galeria.setOnClickListener(this);


        //Si al abrir el activity de crear/editar el bundle no viene vacio se hace este if
        if(this.getIntent().getExtras()!=null) {
            //Recuperamos la información pasada en el intent
            Bundle bundle = this.getIntent().getExtras();

            //Construimos el mensaje a mostrar
            codigoPregunta = bundle.getInt("Codigo");
            setCodigoPregunta(codigoPregunta);

            MyLog.i("Codigo pregunta pasada: ",Integer.toString(codigoPregunta));

            //Llamams a la funcion buscar pregunta, pasandole el codigo de la pregunta y el context
            // al recuperarla rellenamos todos los campos mostrados en pantalla con los valores de la pregunta.
            Pregunta p= Repositorio.buscarPregunta(codigoPregunta,myContext);
            pregunta.setText(p.getEnunciado());
            correcta.setText(p.getRespuestaCorrecta());
            incorrecta1.setText(p.getRespuestaIncorrecta1());
            incorrecta2.setText(p.getRespuestaIncorrecta2());
            incorrecta3.setText(p.getRespuestaIncorrecta3());
            spinnerCategoria.setSelection(Repositorio.getMisCategorias().indexOf(p.getCategoria()));


        }


    }


    // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
    // En las versiones anteriores no es posible hacerlo
    // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
    // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
    private void compruebaPermisos() {
        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        MyLog.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(CrearEditarPreguntaActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);

            } else {
                Snackbar.make(constraint, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG)
                        .show();
            }
        } else {
            // Permiso aceptado
            Snackbar.make(constraint, getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    //Comprueba que todos los campos de la pregunta este relleno,
    // no permite que guardes hasta que este todo completo
    //rellena de rojo los campos que estan vacios si se pulsa guardar
    private boolean compruebaPregunta(View v, EditText pregunta, EditText correcta, EditText incorrecta1, EditText incorrecta2, EditText incorrecta3) {
        if (pregunta.getText().toString().isEmpty()) {
            pregunta.setBackgroundColor(Color.rgb(255, 64, 64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        }
        if (correcta.getText().toString().isEmpty()) {

            correcta.setBackgroundColor(Color.rgb(255, 64, 64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;


        }
        if (incorrecta1.getText().toString().isEmpty()) {

            incorrecta1.setBackgroundColor(Color.RED);

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        }
        if (incorrecta2.getText().toString().isEmpty()) {

            incorrecta2.setBackgroundColor(Color.rgb(255, 64, 64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        }
        if (incorrecta3.getText().toString().isEmpty()) {

            incorrecta3.setBackgroundColor(Color.rgb(255, 64, 64));

            Snackbar.make(v, "Comprueba que todos los campos esten rellenos", Snackbar.LENGTH_LONG)
                    .show();
            return false;

        } else {

            return true;

        }


    }


    //Administra los botones del crear / editar pregunta
    //al pulsarlos hara la funcion especificada para cada uno de ellos
    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.guardar:

                ArrayList categorias =Repositorio.getMisCategorias();
                final EditText pregunta = findViewById(R.id.titulo);
                final EditText correcta = findViewById(R.id.titulo2);
                final EditText incorrecta1 = findViewById(R.id.titulo3);
                final EditText incorrecta2 = findViewById(R.id.titulo4);
                final EditText incorrecta3 = findViewById(R.id.titulo5);
                final ImageView photo = findViewById(R.id.camara);

                Bitmap bm = BitmapFactory.decodeFile(pathFotos);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


                final String spinner;
                if(categorias.isEmpty()){

                    Snackbar.make(constraint, getResources().getString(R.string.anadir_spinner), Snackbar.LENGTH_LONG)
                            .show();

                    break;
                }else{
                   spinner = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
                }


                compruebaPermisos();

                if (compruebaPregunta(v, pregunta, correcta, incorrecta1, incorrecta2, incorrecta3) == true) {





                    if(getCodigoPregunta()!=-1){

                        Pregunta actualizaPregunta = new Pregunta(Integer.toString(getCodigoPregunta()),pregunta.getText().toString(), spinner, correcta.getText().toString(),
                                incorrecta1.getText().toString(), incorrecta2.getText().toString(), incorrecta3.getText().toString()
                        );

                        Repositorio.actualizarPregunta(actualizaPregunta,myContext);
                        MyLog.i("Pregunta", "Actualizada");
                    }else{

                        Pregunta mipregunta = new Pregunta(pregunta.getText().toString(), spinner, correcta.getText().toString(),
                                incorrecta1.getText().toString(), incorrecta2.getText().toString(), incorrecta3.getText().toString(), encodedImage);

                        mirepo.insertar(mipregunta, myContext);

                        MyLog.i("Pregunta", "Creada");
                    }


                    //mirepo.insertar(mipregunta, myContext);

                    finish();


                }

                pregunta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {

                            pregunta.setBackgroundColor(Color.rgb(250, 250, 250));

                        }
                    }
                });

                correcta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {

                            correcta.setBackgroundColor(Color.rgb(250, 250, 250));

                        }
                    }
                });

                incorrecta1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {

                            incorrecta1.setBackgroundColor(Color.rgb(250, 250, 250));

                        }
                    }
                });

                incorrecta2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {

                            incorrecta2.setBackgroundColor(Color.rgb(250, 250, 250));

                        }
                    }
                });

                incorrecta3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {

                            incorrecta3.setBackgroundColor(Color.rgb(250, 250, 250));

                        }
                    }
                });


                break;

            case R.id.mascategoria:


                //Recuperación de la vista del AlertDialog a partir del layout de la Actividad
                LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);

                //Definición del AlertDialog
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

                //Asignación del AlertDialog a su vista
                alertDialog.setView(viewAlertDialog);

                //Recuperación del EditTextdel AlertDialog
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.dialogInput);

                //Configuración del AlertDialog
                alertDialog
                        .setCancelable(false)
                        //BotónAñadir
                        .setPositiveButton(getResources().getString(R.string.add),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        adapter.add(dialogInput.getText().toString());
                                        spinnerCategoria.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                    }
                                })
                        //BotónCancelar
                        .setNegativeButton(getResources().getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                }).create()
                        .show();


                break;


            case R.id.camara:

                takePicture();


                break;


            case R.id.galeria:


                selectPicture();

                break;


            default:
                break;
        }

    }

    //Maneja la respuesta del comprueba permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso aceptado
                    Snackbar.make(constraint, getResources().getString(R.string.write_permission_accepted), Snackbar.LENGTH_LONG)
                            .show();

                } else {
                    // Permiso rechazado
                    Snackbar.make(constraint, getResources().getString(R.string.write_permission_not_accepted), Snackbar.LENGTH_LONG)
                            .show();


                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void takePicture() {
        try {
            // Se crea el directorio para las fotografías
            File dirFotos = new File(pathFotos);
            dirFotos.mkdirs();

            // Se crea el archivo para almacenar la fotografía
            File fileFoto = File.createTempFile(getFileCode(),".jpg", dirFotos);

            // Se crea el objeto Uri a partir del archivo
            // A partir de la API 24 se debe utilizar FileProvider para proteger
            // con permisos los archivos creados
            // Con estas funciones podemos evitarlo
            // https://stackoverflow.com/questions/42251634/android-os-fileuriexposedexception-file-jpg-exposed-beyond-app-through-clipdata
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            uri = Uri.fromFile(fileFoto);
            Log.d("foto hecha", uri.getPath().toString());

            // Se crea la comunicación con la cámara
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Se le indica dónde almacenar la fotografía
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            // Se lanza la cámara y se espera su resultado
            startActivityForResult(cameraIntent, REQUEST_CAPTURE_IMAGE);

        } catch (IOException ex) {

            Log.d("foto no hecha", "Error: " + ex);
            CoordinatorLayout coordinatorLayout = findViewById(R.id.constraint);
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getResources().getString(R.string.error_files), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void selectPicture(){
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.choose_picture)),
                REQUEST_SELECT_IMAGE);
    }

    private String getFileCode()
    {
        // Se crea un código a partir de la fecha y hora
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss", java.util.Locale.getDefault());
        String date = dateFormat.format(new Date());
        // Se devuelve el código
        return "pic_" + date;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case (REQUEST_CAPTURE_IMAGE):
                if(resultCode == Activity.RESULT_OK){
                    // Se carga la imagen desde un objeto URI al imageView
                    ImageView imageView = findViewById(R.id.camara);
                    imageView.setImageURI(uri);

                    // Se le envía un broadcast a la Galería para que se actualice
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(uri);
                    sendBroadcast(mediaScanIntent);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // Se borra el archivo temporal
                    File file = new File(uri.getPath());
                    file.delete();
                }
                break;

            case (REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto Bitmap
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        // Se leen los bytes de la imagen
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Se transformam los bytes de la imagen a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                        // Se carga el Bitmap en el ImageView
                        ImageView imageView = findViewById(R.id.camara);
                        imageView.setImageBitmap(bmp);
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // añade al menu un item
        getMenuInflater().inflate(R.menu.crear_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Se crea la accion que se va a realizar al pulsar en el boton atras tanto de la interfaz
        //como de los botones del terminal
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                onNavigateUp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        finish();
    }
    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }
}
