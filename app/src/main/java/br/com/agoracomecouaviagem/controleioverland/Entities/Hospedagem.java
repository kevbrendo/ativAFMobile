package br.com.agoracomecouaviagem.controleioverland.Entities;

import java.time.LocalDate;

public class Hospedagem extends Despesa {

    private String tipoHospedagem;
    private Integer quantidadeDeDiarias;
    private String nomeHospedagem;


    public Hospedagem(String tipoHospedagem, Integer quantidadeDeDiarias, String nomeHospedagem, LocalDate data, String descricao, Double valor, String categoria, String metodoPagamento, String local, Integer id_viagem) {
        super(data, descricao, valor, categoria, metodoPagamento, local, id_viagem);
        this.tipoHospedagem = tipoHospedagem;
        this.quantidadeDeDiarias = quantidadeDeDiarias;
        this.nomeHospedagem = nomeHospedagem;

    }


    public Hospedagem() {

    }

    public String getTipoHospedagem() {
        return tipoHospedagem;
    }

    public void setTipoHospedagem(String tipoHospedagem) {
        this.tipoHospedagem = tipoHospedagem;
    }

    public Integer getQuantidadeDeDiarias() {
        return quantidadeDeDiarias;
    }

    public void setQuantidadeDeDiarias(Integer quantidadeDeDiarias) {
        this.quantidadeDeDiarias = quantidadeDeDiarias;
    }

    public String getNomeHospedagem() {
        return nomeHospedagem;
    }

    public void setNomeHospedagem(String nomeHospedagem) {
        this.nomeHospedagem = nomeHospedagem;
    }

    @Override
    public String toString() {
        return  "=====================================================" + "\n"+
                "Hospedagem:  " + "\n"+
                "Data: " + getData() + "   "+
                "Valor: " + getValor() + "\n"+
                "Metodo de pagamento: " + getMetodoPagamento() + "\n" +
                "Local: " + getLocal() + "\n"+
                "Descrição: " + getDescricao() + "\n";
    }









}

