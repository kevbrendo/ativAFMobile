package br.com.agoracomecouaviagem.controleioverland.Entities;

import java.time.LocalDate;

public class Manutencao extends Despesa{

    private String tipoDeManutencao;

    public Manutencao(String tipoDeManutencao, LocalDate data, String descricao, Double valor, String categoria, String metodoPagamento, String local, Integer id_viagem) {
        super(data, descricao, valor, categoria, metodoPagamento, local, id_viagem);
        this.tipoDeManutencao = tipoDeManutencao;
    }

    public Manutencao() {
    }

    public String getTipoDeManutencao() {
        return tipoDeManutencao;
    }

    public void setTipoDeManutencao(String tipoDeManutencao) {
        this.tipoDeManutencao = tipoDeManutencao;
    }

    @Override
    public String toString() {
        return  "---------------------------------------" + "\n"+
                "Manutenção:  " + "\n"+
                "Data: " + getData() + "   "+
                "Valor: " + getValor() + "\n"+
                "Metodo de pagamento: " + getMetodoPagamento() + "\n" +
                "Local: " + getLocal() + "\n"+
                "Tipo de manutencção: " + getTipoDeManutencao() + "\n" +
                "Descrição: " + getDescricao() + "\n";
    }


}

