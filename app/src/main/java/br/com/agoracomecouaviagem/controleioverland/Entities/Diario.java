package br.com.agoracomecouaviagem.controleioverland.Entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Diario implements Serializable {

    private Integer id;
    private LocalDate data;
    private String local;
    private String comentario;
    private Integer id_viagem;
    private Boolean favorito;

    public Diario(Integer id, LocalDate data, String local, String comentario, Integer id_viagem, Boolean favorito) {
        this.id = id;
        this.data = data;
        this.local = local;
        this.comentario = comentario;
        this.id_viagem = id_viagem;
        this.favorito = favorito;
    }



    public Diario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getId_viagem() {
        return id_viagem;
    }

    public void setId_viagem(Integer id_viagem) {
        this.id_viagem = id_viagem;
    }


    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }
}

