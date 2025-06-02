package br.com.agoracomecouaviagem.controleioverland.Entities;

import java.time.LocalDate;

public class Estacionamento extends Despesa {

    private Integer quantidadeHoras;


    public Estacionamento(Integer quantidadeHoras, Integer quantidadeMinutos, LocalDate data, String descricao, Double valor, String categoria, String metodoPagamento, String local, Integer id_viagem) {
        super(data, descricao, valor, categoria, metodoPagamento, local, id_viagem);
        this.quantidadeHoras = quantidadeHoras;

    }


    public Estacionamento() {
    }

    public Integer getQuantidadeHoras() {
        return quantidadeHoras;
    }

    public void setQuantidadeHoras(Integer quantidadeHoras) {
        if (quantidadeHoras < 0) {
            throw new IllegalArgumentException("Quantidade de horas não pode ser negativa.");
        }
        this.quantidadeHoras = quantidadeHoras;
    }

    @Override
    public String toString() {
        return  "---------------------------------------" + "\n"+
                "Estacionamento:  " + "\n"+
                "Data: " + getData() + "   "+
                "Valor: " + getValor() + "\n"+
                "Metodo de pagamento: " + getMetodoPagamento() + "\n" +
                "Local: " + getLocal() + "\n"+
                "Quantidade de Horas: " + getQuantidadeHoras() + "\n" +
                "Descrição: " + getDescricao() + "\n";
    }





}

