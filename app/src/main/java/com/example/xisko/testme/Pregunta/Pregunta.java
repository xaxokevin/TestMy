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


    /**
     * Constructor que recibe todos los parametros de la pregunta menos el id
     * @param enunciado
     * @param categoria
     * @param respuestaCorrecta
     * @param RespuestaIncorrecta1
     * @param RespuestaIncorrecta2
     * @param RespuestaIncorrecta3
     * @param photo
     */
    public Pregunta(String enunciado, String categoria, String respuestaCorrecta, String RespuestaIncorrecta1, String RespuestaIncorrecta2, String RespuestaIncorrecta3, String photo) {
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.respuestaCorrecta = respuestaCorrecta;
        this.RespuestaIncorrecta1 = RespuestaIncorrecta1;
        this.RespuestaIncorrecta2 = RespuestaIncorrecta2;
        this.RespuestaIncorrecta3 = RespuestaIncorrecta3;
        this.photo = photo;

    }

    /**
     * Constructor que recibe todos los parametros de la pregunta
     * @param codigo
     * @param enunciado
     * @param categoria
     * @param respuestaCorrecta
     * @param RespuestaIncorrecta1
     * @param RespuestaIncorrecta2
     * @param RespuestaIncorrecta3
     * @param photo
     */
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


    /**
     * Constructor que recibe todos los parametros menos la foto y el id
     * @param enunciado
     * @param categoria
     * @param respuestaCorrecta
     * @param RespuestaIncorrecta1
     * @param RespuestaIncorrecta2
     * @param RespuestaIncorrecta3
     */
    public Pregunta(String enunciado, String categoria, String respuestaCorrecta, String RespuestaIncorrecta1, String RespuestaIncorrecta2, String RespuestaIncorrecta3) {
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.respuestaCorrecta = respuestaCorrecta;
        this.RespuestaIncorrecta1 = RespuestaIncorrecta1;
        this.RespuestaIncorrecta2 = RespuestaIncorrecta2;
        this.RespuestaIncorrecta3 = RespuestaIncorrecta3;

    }


    /**
     * Metodo que devuelve el codigo
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }



    /**
     * Metodo que devuelve el enunciado
     * @return enunciado
     */
    public String getEnunciado() {
        return enunciado;
    }



    /**
     * Metodo que devuelve la categoria
     * @return categoria
     */
    public String getCategoria() {
        return categoria;
    }


    /**
     * Metodo que devuelve la respuesta correcta
     * @return respuestaCorrecta
     */
    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }



    /**
     * Metodo que devuelve la respuesta icorrecta 1
     * @return RespuestaIncorrecta1
     */
    public String getRespuestaIncorrecta1() {
        return RespuestaIncorrecta1;
    }


    /**
     * Metodo que devuelve la respuesta icorrecta 2
     * @return RespuestaIncorrecta2
     */
    public String getRespuestaIncorrecta2() {
        return RespuestaIncorrecta2;
    }



    /**
     * Metodo que devuelve la respuesta icorrecta 2
     * @return RespuestaIncorrecta2
     */
    public String getRespuestaIncorrecta3() {
        return RespuestaIncorrecta3;
    }

    /**
     * Metodo que devuelve la foto
     * @return photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Metodo que establece la imagen
     * @param photo
     */
    public void setPhoto(String photo){
        this.photo=photo;
    }
}
