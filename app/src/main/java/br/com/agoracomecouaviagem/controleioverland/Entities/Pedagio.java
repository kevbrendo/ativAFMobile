package br.com.agoracomecouaviagem.controleioverland.Entities;

import java.time.LocalDate;

public class Pedagio extends Despesa {

    private String qualidadeDaVia;

    public Pedagio(String qualidadeDaVia, LocalDate data, String descricao, Double valor, String categoria, String metodoPagamento, String local, Integer id_viagem) {
        super(data, descricao, valor, categoria, metodoPagamento, local, id_viagem);
        this.qualidadeDaVia = qualidadeDaVia;
    }

    public Pedagio() {
    }

    public String getQualidadeDaVia() {
        return qualidadeDaVia;
    }

    public void setQualidadeDaVia(String qualidadeDaVia) {
        this.qualidadeDaVia = qualidadeDaVia;
    }

    @Override
    public String toString() {
        return  "---------------------------------------" + "\n"+
                "Despesa:  " + "\n"+
                "Data: " + getData() + "   "+
                "Valor: " + getValor() + "\n"+
                "Metodo de pagamento: " + getMetodoPagamento() + "\n" +
                "Local: " + getLocal() + "\n"+
                "Qualidade da via: " + getQualidadeDaVia() + "\n"+
                "Descrição: " + getDescricao() + "\n";
    }

}

