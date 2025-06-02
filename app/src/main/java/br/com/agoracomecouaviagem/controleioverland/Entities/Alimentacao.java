package br.com.agoracomecouaviagem.controleioverland.Entities;

import java.time.LocalDate;

public class Alimentacao extends Despesa {

    private String tipoAlimentacao;

    public Alimentacao(String tipoAlimentacao, LocalDate data, String descricao, Double valor, String categoria, String metodoPagamento, String local, Integer id_viagem) {
        super(data, descricao, valor, categoria, metodoPagamento, local, id_viagem);
        this.tipoAlimentacao = tipoAlimentacao;
    }

    public Alimentacao(String tipoAlimentacao) {
        this.tipoAlimentacao = tipoAlimentacao;
    }



    public Alimentacao() {
    }

    public String getTipoAlimentacao() {
        return tipoAlimentacao;
    }

    public void setTipoAlimentacao(String tipoAlimentacao) {
        this.tipoAlimentacao = tipoAlimentacao;
    }

    @Override
    public String toString() {
        return  "---------------------------------------" + "\n"+
                "Alimentação:  " + "\n"+
                "Data: " + getData() + "   "+
                "Valor: " + getValor() + "\n"+
                "Metodo de pagamento: " + getMetodoPagamento() + "\n" +
                "Local: " + getLocal() + "\n"+
                "Tipo de alimentação: " + getTipoAlimentacao() + "\n"+
                "Descrição: " + getDescricao() + "\n";
    }


}

