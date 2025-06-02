package br.com.agoracomecouaviagem.controleioverland.Entities;

import java.time.LocalDate;

public class Passeio extends Despesa {

    private String nomePasseio;

    public Passeio(String nomePasseio, LocalDate data, String descricao, Double valor, String categoria, String metodoPagamento, String local, Integer id_viagem) {
        super(data, descricao, valor, categoria, metodoPagamento, local, id_viagem);
        this.nomePasseio = nomePasseio;
    }

    public Passeio() {
    }

    public String getNomePasseio() {
        return nomePasseio;
    }

    public void setNomePasseio(String nomePasseio) {
        this.nomePasseio = nomePasseio;
    }

    @Override
    public String toString() {
        return  "---------------------------------------" + "\n"+
                "Passeio:  " + "\n"+
                "Data: " + getData() + "   "+
                "Valor: " + getValor() + "\n"+
                "Metodo de pagamento: " + getMetodoPagamento() + "\n" +
                "Local: " + getLocal() + "\n"+
                "Nome do passeio: " + getNomePasseio() + "\n" +
                "Descrição: " + getDescricao() + "\n";
    }


}
