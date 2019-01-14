package com.example.xisko.testme.Pregunta;

import android.graphics.Bitmap;

public class Pregunta {

    //Variables de la clase pregunta
    private int codigo;
    private String enunciado;
    private String categoria;
    private String respuestaCorrecta;
    private String RespuestaIncorrecta1;
    private String RespuestaIncorrecta2;
    private String RespuestaIncorrecta3;
    private String photo;


    //Se crean 3 Constructores uno para el insert sin imange, uno para el insert con imagen y uno para el select de la base de datos
    public Pregunta(String enunciado, String categoria, String respuestaCorrecta, String RespuestaIncorrecta1, String RespuestaIncorrecta2, String RespuestaIncorrecta3, String photo) {
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.respuestaCorrecta = respuestaCorrecta;
        this.RespuestaIncorrecta1 = RespuestaIncorrecta1;
        this.RespuestaIncorrecta2 = RespuestaIncorrecta2;
        this.RespuestaIncorrecta3 = RespuestaIncorrecta3;
        this.photo = photo;

    }
    public Pregunta(String codigo, String enunciado, String categoria, String respuestaCorrecta, String RespuestaIncorrecta1, String RespuestaIncorrecta2, String RespuestaIncorrecta3, String photo) {
        this.codigo = Integer.parseInt(codigo);
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.respuestaCorrecta = respuestaCorrecta;
        this.RespuestaIncorrecta1 = RespuestaIncorrecta1;
        this.RespuestaIncorrecta2 = RespuestaIncorrecta2;
        this.RespuestaIncorrecta3 = RespuestaIncorrecta3;
        this.photo = photo;
    }


    public Pregunta(String enunciado, String categoria, String respuestaCorrecta, String RespuestaIncorrecta1, String RespuestaIncorrecta2, String RespuestaIncorrecta3) {
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.respuestaCorrecta = respuestaCorrecta;
        this.RespuestaIncorrecta1 = RespuestaIncorrecta1;
        this.RespuestaIncorrecta2 = RespuestaIncorrecta2;
        this.RespuestaIncorrecta3 = RespuestaIncorrecta3;

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
        return RespuestaIncorrecta1;
    }



    public String getRespuestaIncorrecta2() {
        return RespuestaIncorrecta2;
    }



    public String getRespuestaIncorrecta3() {
        return RespuestaIncorrecta3;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo){
        this.photo=photo;
    }
}
