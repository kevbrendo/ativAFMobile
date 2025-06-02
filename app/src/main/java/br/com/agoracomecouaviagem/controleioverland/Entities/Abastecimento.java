package br.com.agoracomecouaviagem.controleioverland.Entities;

import java.time.LocalDate;


public class Abastecimento extends Despesa  {

    private Double quantidadeCombustivel;
    private Double valorPorLitro;

    public Abastecimento(Double quantidadeCombustivel, Double valorPorLitro, LocalDate data, String descricao, Double valor, String categoria, String metodoPagamento, String local, Integer id_viagem) {
        super(data, descricao, valor, categoria, metodoPagamento, local, id_viagem);
        this.quantidadeCombustivel = quantidadeCombustivel;
        this.valorPorLitro = valorPorLitro;
    }


    public Abastecimento() {
    }

    public Double getQuantidadeCombustivel() {
        return quantidadeCombustivel;
    }

    public void setQuantidadeCombustivel(Double quantidadeCombustivel) {
        this.quantidadeCombustivel = quantidadeCombustivel;
    }

    public Double getValorPorLitro() {
        return valorPorLitro;
    }

    public void setValorPorLitro(Double valorPorLitro) {
        this.valorPorLitro = valorPorLitro;
    }


    @Override
    public String toString() {
        return  "---------------------------------------" + "\n"+
                "Abastecimento:  " + "\n"+
                "Data: " + getData() + "   "+
                "Valor: " + getValor() + "\n"+
                "Metodo de pagamento: " + getMetodoPagamento() + "\n" +
                "Local: " + getLocal() + "\n"+
                "Quantidade de Combustivel: " + getQuantidadeCombustivel() + "\n"+
                "Valor por Litro: " + getValorPorLitro() + "\n"+
                "Descrição: " + getDescricao() + "\n";
    }
}
