package com.example.xisko.testme.Pregunta;

import android.graphics.Bitmap;

public class Pregunta {

    //Variables de la clase pregunta
    private int codigo;
    private String enunciado;
    private String categoria;
    private String respuestaCorrecta;
    private String getRespuestaIncorrecta1;
    private String getRespuestaIncorrecta2;
    private String getRespuestaIncorrecta3;
    private String photo;


    //Se crean 2 Constructores uno para el insert y uno para el select de la base de datos
    public Pregunta(String enunciado, String categoria, String respuestaCorrecta, String getRespuestaIncorrecta1, String getRespuestaIncorrecta2, String getRespuestaIncorrecta3, String photo) {
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.respuestaCorrecta = respuestaCorrecta;
        this.getRespuestaIncorrecta1 = getRespuestaIncorrecta1;
        this.getRespuestaIncorrecta2 = getRespuestaIncorrecta2;
        this.getRespuestaIncorrecta3 = getRespuestaIncorrecta3;
        this.photo = photo;
    }
    public Pregunta(String codigo, String enunciado, String categoria, String respuestaCorrecta, String getRespuestaIncorrecta1, String getRespuestaIncorrecta2, String getRespuestaIncorrecta3, String photo) {
        this.codigo = Integer.parseInt(codigo);
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.respuestaCorrecta = respuestaCorrecta;
        this.getRespuestaIncorrecta1 = getRespuestaIncorrecta1;
        this.getRespuestaIncorrecta2 = getRespuestaIncorrecta2;
        this.getRespuestaIncorrecta3 = getRespuestaIncorrecta3;
        this.photo = photo;
    }

    //Metodos geters de la clase
    public int getCodigo() {
        return codigo;
    }



    public String getEnunciado() {
        return enunciado;
    }



    public String getCategoria() {
        return categoria;
    }



    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }



    public String getRespuestaIncorrecta1() {
        return getRespuestaIncorrecta1;
    }



    public String getRespuestaIncorrecta2() {
        return getRespuestaIncorrecta2;
    }



    public String getRespuestaIncorrecta3() {
        return getRespuestaIncorrecta3;
    }

    public String getPhoto() {
        return photo;
    }
}
