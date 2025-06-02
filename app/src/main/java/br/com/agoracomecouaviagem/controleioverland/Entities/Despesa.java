package br.com.agoracomecouaviagem.controleioverland.Entities;

import java.io.Serializable;
import java.time.LocalDate;


public class Despesa implements Serializable {

    private Integer id;
    private LocalDate data;
    private String descricao;
    private Double valor;
    private String categoria;
    private String metodoPagamento;
    private String local;
    private Integer id_viagem;

    public Despesa(LocalDate data, String descricao, Double valor, String categoria, String metodoPagamento, String local, Integer id_viagem) {
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.metodoPagamento = metodoPagamento;
        this.local = local;
        this.id_viagem = id_viagem;
    }

    public Despesa() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getId_viagem() {
        return id_viagem;
    }

    public void setId_viagem(Integer id_viagem) {
        this.id_viagem = id_viagem;
    }


    public String toString() {
        return  "---------------------------------------" + "\n"+
                "Despesa:  " + "\n"+
                "Data: " + getData() + "   "+
                "Valor: " + getValor() + "\n"+
                "Metodo de pagamento: " + getMetodoPagamento() + "\n" +
                "Local: " + getLocal() + "\n"+
                "Descrição: " + getDescricao() + "\n";
    }



}
