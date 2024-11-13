package com.letterfood.models;

public class Avaliacao {
    private int nota;
    private String comentario;

    // Getter para nota
    public int getNota() {
        return nota;
    }

    // Setter para nota
    public void setNota(int nota) {
        this.nota = nota;
    }

    // Getter para comentario
    public String getComentario() {
        return comentario;
    }

    // Setter para comentario
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
