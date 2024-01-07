package com.middevs.springboot.reactor.app.models;

public class UsuarioComentario {

    private Usuario usuario;
    private Comentario comentarios;


    public UsuarioComentario(Usuario usuario, Comentario comentarios) {
        this.usuario = usuario;
        this.comentarios = comentarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Comentario getComentarios() {
        return comentarios;
    }

    public void setComentarios(Comentario comentarios) {
        this.comentarios = comentarios;
    }


    @Override
    public String toString() {
        return "UsuarioComentario{" +
                "usuario=" + usuario +
                ", comentarios=" + comentarios +
                '}';
    }
}
