package com.example.xisko.testme.Pregunta;

public class Pregunta {

    private int codigo;
    private String enunciado;
    private String categoria;
    private String respuestaCorrecta;
    private String getRespuestaIncorrecta1;
    private String getRespuestaIncorrecta2;
    private String getRespuestaIncorrecta3;


    public Pregunta(String enunciado, String categoria, String respuestaCorrecta, String getRespuestaIncorrecta1, String getRespuestaIncorrecta2, String getRespuestaIncorrecta3) {
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.respuestaCorrecta = respuestaCorrecta;
        this.getRespuestaIncorrecta1 = getRespuestaIncorrecta1;
        this.getRespuestaIncorrecta2 = getRespuestaIncorrecta2;
        this.getRespuestaIncorrecta3 = getRespuestaIncorrecta3;
    }
    public Pregunta(String codigo, String enunciado, String categoria, String respuestaCorrecta, String getRespuestaIncorrecta1, String getRespuestaIncorrecta2, String getRespuestaIncorrecta3) {
        this.codigo = Integer.parseInt(codigo);
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.respuestaCorrecta = respuestaCorrecta;
        this.getRespuestaIncorrecta1 = getRespuestaIncorrecta1;
        this.getRespuestaIncorrecta2 = getRespuestaIncorrecta2;
        this.getRespuestaIncorrecta3 = getRespuestaIncorrecta3;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getRespuestaIncorrecta1() {
        return getRespuestaIncorrecta1;
    }

    public void setGetRespuestaIncorrecta1(String getRespuestaIncorrecta1) {
        this.getRespuestaIncorrecta1 = getRespuestaIncorrecta1;
    }

    public String getRespuestaIncorrecta2() {
        return getRespuestaIncorrecta2;
    }

    public void setRespuestaIncorrecta2(String getRespuestaIncorrecta2) {
        this.getRespuestaIncorrecta2 = getRespuestaIncorrecta2;
    }

    public String getRespuestaIncorrecta3() {
        return getRespuestaIncorrecta3;
    }

    public void setGetRespuestaIncorrecta3(String getRespuestaIncorrecta3) {
        this.getRespuestaIncorrecta3 = getRespuestaIncorrecta3;
    }

}
