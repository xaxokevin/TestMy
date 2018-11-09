package com.example.xisko.testme.Persistencia;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BasedeDatos extends SQLiteOpenHelper {

    //Data base version
    private static final int DATABASE_VERSION = 1;

    //Data base name

    private static String DATABASE_NAME = "preguntas.db";

    //Nombre columna
    private static final String COLUMN_ID = "id";
    private static  final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_CORRECTA = "correcta";
    private static final String COLUMN_INCORRECTA1 = "incorrecta1";
    private static final String COLUMN_INCORRECTA2 = "incorrecta2";
    private static final String COLUMN_INCORRECTA3 = "incorrecta3";


    //Nombre tabla
    private static String TABLE_NAME = "pregunta";

    SQLiteDatabase database;





    public BasedeDatos(Context contexto, String preguntas, Object o, int i) {
        super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_NOMBRE+" TEXT, "+ COLUMN_CORRECTA + " TEXT, " + COLUMN_INCORRECTA1 + " TEXT, " + COLUMN_INCORRECTA2 + " TEXT, " + COLUMN_INCORRECTA3 + " TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);


    }
}


