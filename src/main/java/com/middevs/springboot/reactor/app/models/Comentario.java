package com.middevs.springboot.reactor.app.models;

import java.util.ArrayList;
import java.util.List;

public class Comentario {
    private List<String> comentarios;

    public Comentario(){
        comentarios = new ArrayList<>();
    }

    public void addComentario(String comentario){
        this.comentarios.add(comentario);
    }

}
