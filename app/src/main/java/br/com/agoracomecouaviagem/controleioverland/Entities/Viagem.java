package br.com.agoracomecouaviagem.controleioverland.Entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Viagem {

    private Integer id;
    private String nome;
    private LocalDate dataDeInicio;
    private LocalDate dataDeTermino;
    private String localDePartida;
    private String DestinoFinal;
    private Double kilometragemInicial;
    private Double kilometragemParcial;
    private Double kilometragemFinal;
    private Double gastoParcial;
    private Double gastoTotal;
    private boolean Status;

    private List<Abastecimento> abastecimentos = new ArrayList<>();
    private List <Alimentacao> alimentacoes = new ArrayList<>();
    private List <Despesa> despesas = new ArrayList<>();
    private List <Diario> diarios = new ArrayList<>();
    private List <Estacionamento> estacionamentos = new ArrayList<>();
    private List <Hospedagem> hospedagens = new ArrayList<>();
    private List <Manutencao> manutencoes = new ArrayList<>();
    private List <Pedagio> pedagios = new ArrayList<>();
    private List <Passeio> passeios = new ArrayList<>();


    public Viagem(LocalDate dataDeInicio, LocalDate dataDeTermino, String localDePartida, String DestinoFinal, Double kilometragemInicial,Double kilometragemFinal, Double gastoParcial, Double gastoTotal, boolean Status) {

        this.dataDeInicio = dataDeInicio;
        this.dataDeTermino = dataDeTermino;
        this.localDePartida = localDePartida;
        this.DestinoFinal = DestinoFinal;
        this.kilometragemInicial = kilometragemInicial;
        this.kilometragemParcial = 0.0;
        this.kilometragemFinal = kilometragemFinal;
        this.gastoParcial = gastoParcial;
        this.gastoTotal = gastoTotal;
        this.Status = Status;
    }

    public Viagem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(LocalDate dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public LocalDate getDataDeTermino() {
        return dataDeTermino;
    }

    public void setDataDeTermino(LocalDate dataDeTermino) {
        this.dataDeTermino = dataDeTermino;
    }

    public String getLocalDePartida() {
        return localDePartida;
    }

    public void setLocalDePartida(String localDePartida) {
        this.localDePartida = localDePartida;
    }

    public String getDestinoFinal() {
        return DestinoFinal;
    }

    public void setDestinoFinal(String DestinoFinal) {
        this.DestinoFinal = DestinoFinal;
    }

    public Double getKilometragemInicial() {
        return kilometragemInicial;
    }

    public void setKilometragemInicial(Double kilometragemInicial) {
        this.kilometragemInicial = kilometragemInicial;
    }

    public Double getKilometragemParcial() {
        return kilometragemParcial;
    }

    public void setKilometragemParcial(Double kilometragemParcial) {
        this.kilometragemParcial = kilometragemParcial;
    }

    public void addKilometragemParcial(Double kilometragemParcial) {
        this.kilometragemParcial += kilometragemParcial;
    }

    public Double getKilometragemFinal() {
        return kilometragemFinal;
    }

    public void setKilometragemFinal(Double kilometragemFinal) {
        this.kilometragemFinal = kilometragemFinal;
    }

    public Double getGastoParcial() {
        return gastoParcial;
    }

    public void setGastoParcial(Double gastoParcial) {
        this.gastoParcial = gastoParcial;
    }

    public Double getGastoTotal() {
        return gastoTotal;
    }

    public void setGastoTotal(Double gastoTotal) {
        this.gastoTotal = gastoTotal;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public Boolean getStatus(){return this.Status;}

    public List<Abastecimento> getAbastecimentos() {
        return abastecimentos;
    }

    public void setAbastecimentos(List<Abastecimento> abastecimentos) {
        this.abastecimentos = abastecimentos;
    }

    public List<Alimentacao> getAlimentacoes() {
        return alimentacoes;
    }

    public void setAlimentacoes(List<Alimentacao> alimentacoes) {
        this.alimentacoes = alimentacoes;
    }

    public List<Estacionamento> getEstacionamentos() {
        return estacionamentos;
    }

    public void setEstacionamentos(List<Estacionamento> estacionamentos) {
        this.estacionamentos = estacionamentos;
    }

    public List<Manutencao> getManutencoes() {
        return manutencoes;
    }

    public void setManutencoes(List<Manutencao> manutencoes) {
        this.manutencoes = manutencoes;
    }

    public List<Pedagio> getPedagios() {
        return pedagios;
    }

    public void setPedagios(List<Pedagio> pedagios) {
        this.pedagios = pedagios;
    }

    public List<Passeio> getPasseios() {
        return passeios;
    }

    public void setPasseios(List<Passeio> passeios) {
        this.passeios = passeios;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public List<Diario> getDiarios() {
        return diarios;
    }

    public void setDiarios(List<Diario> diarios) {
        this.diarios = diarios;
    }

    public List<Hospedagem> getHospedagens() {
        return hospedagens;
    }

    public void setHospedagens(List<Hospedagem> hospedagens) {
        this.hospedagens = hospedagens;
    }

    public String isAberta(Boolean status){
        if(status){
            return "Em andamento.";
        }else {
          return  "Encerrada";
        }

    }
    public String getSumarioViagem(Boolean status){



        if(status) {
            return
                    "Nome da Viagem: " + getNome() + "\n" +
                            "Status da viagem: " + isAberta(getStatus()) + "\n" + "\n" +
                            "Data de início: " + getDataDeInicio() + "\n" +
                            "Local de partida: " + getLocalDePartida() + "\n" +
                            "Kilometragem inicial: " + getKilometragemInicial() + "\n" +
                            "Kilometragem percorrida: " + getKilometragemParcial() + "\n" +
                            "Gasto parcial: " + getGastoParcial()+ "\n" + "\n"+

                            "Quantidade de Abastecimentos: " + abastecimentos.size() + "\n" +
                            "Quantidade de Estacionamentos: " + estacionamentos.size() + "\n" +
                            "Quantidade de Hospedagens" + hospedagens.size() + "\n" +
                            "Quantidade de Manutenções: " + manutencoes.size() + "\n" +
                            "Quantidade de Passeios: " + passeios.size() + "\n" +
                            "Quantidade de Pedágios: " + pedagios.size() + "\n";

        }else {

            return
                    "Nome da Viagem: " + getNome() + "\n" +
                            "Status da viagem: " + isAberta(getStatus()) + "\n" + "\n" +
                            "Data de início: " + getDataDeInicio() + "\n" +
                            "Local de partida: " + getLocalDePartida() + "\n" +
                            "Local de destino: " + getDestinoFinal() + "\n" +
                            "Kilometragem inicial: " + getKilometragemInicial() + "\n" +
                            "Kilometragem final: " + getKilometragemFinal() + "\n" +
                            "Gasto total: " + getGastoTotal() + "\n"+ "\n" +

                            "Quantidade de Abastecimentos: " + abastecimentos.size() + "\n" +
                            "Quantidade de Estacionamentos: " + estacionamentos.size() + "\n" +
                            "Quantidade de Hospedagens" + hospedagens.size() + "\n" +
                            "Quantidade de Manutenções: " + manutencoes.size() + "\n" +
                            "Quantidade de Passeios: " + passeios.size() + "\n" +
                            "Quantidade de Pedágios: " + pedagios.size() + "\n";

        }


    }




    public long calcularDiasAteHoje() {
        // Obter a data atual
        LocalDate hoje = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            hoje = LocalDate.now();
            long diasAteHoje = ChronoUnit.DAYS.between(dataDeInicio, hoje);
            return diasAteHoje;
        }
        return 0;
    }

    public long calcularDiasEncerrado(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            long diasAteHoje = ChronoUnit.DAYS.between(dataDeInicio, dataDeTermino);
            return diasAteHoje;
        }
        return 0;

    }














}
