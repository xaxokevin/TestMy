package com.example.xisko.testme.Log;

import static com.example.xisko.testme.Constantes.DEBUG;

public class MyLog {


    /**
     * Metodo que nos muestra informacion en el Log del compilador
     * @param tag
     * @param string
     */
        public static void i(String tag, String string) {
            if (DEBUG) android.util.Log.i(tag, string);
        }

    /**
     * Metodo que nos muestra informacion en el Log del compilador
     * @param tag
     * @param string
     */
        public static void e(String tag, String string) {
            if (DEBUG) android.util.Log.e(tag, string);
        }

    /**
     * Metodo que nos muestra informacion en el Log del compilador
     * @param tag
     * @param string
     */
        public static void d(String tag, String string) {
            if (DEBUG) android.util.Log.d(tag, string);
        }

    /**
     * Metodo que nos muestra informacion en el Log del compilador
     * @param tag
     * @param string
     */
        public static void v(String tag, String string) {
            if (DEBUG) android.util.Log.v(tag, string);
        }

    /**
     * Metodo que nos muestra informacion en el Log del compilador
     * @param tag
     * @param string
     */
        public static void w(String tag, String string) {
            if (DEBUG) android.util.Log.w(tag, string);
        }
    }

