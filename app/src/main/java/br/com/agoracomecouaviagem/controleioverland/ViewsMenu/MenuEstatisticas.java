package br.com.agoracomecouaviagem.controleioverland.ViewsMenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.Entities.Alimentacao;
import br.com.agoracomecouaviagem.controleioverland.Entities.Hospedagem;
import br.com.agoracomecouaviagem.controleioverland.Entities.Manutencao;
import br.com.agoracomecouaviagem.controleioverland.Entities.Passeio;
import br.com.agoracomecouaviagem.controleioverland.Entities.Pedagio;
import br.com.agoracomecouaviagem.controleioverland.Entities.Viagem;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.Utils.SQLConnection;
import br.com.agoracomecouaviagem.controleioverland.Entities.Abastecimento;

public class MenuEstatisticas extends AppCompatActivity {

    private Button btnPDF;
    private Integer idViagemAtiva;
    private Viagem viagemSelecionada;
    private ReadDAO read;
    private com.github.mikephil.charting.charts.BarChart barChart;
    private TextView textAbastecimentos , textAlimentacoes , textDespesas, textEstacionamentos, textHospedagens, textManutencoes, textPasseios, textPedagios, textGastosPorDia;
    private Button btnVoltar;
    //Text Estatisticas viagem
    private TextView textNomeViagem, textStatusViagem, textDiasViajados, textDataInicioViagem, textLocalDePartidaViagem, textKilometragemPercorridaViagem;

    //Text Estatistica Abastecimento
    private TextView textQuantidadeAbastecimentos, textPrecoMedioAbastecimentos, textQuantidadeLitrosAbastecimentos, textCaroValorLitroAbastecimentos,
            textLocalMaisCaroAbastecimentos, textBaratoValorLitroAbastecimentos, textLocalMaisBaratoLitroAbastecimentos , textMediaValorLitro;

    //Text Estatistica Alimentação
    private TextView textTotalFeira, textTotalLanchonete, textTotalMercado, textTotalPadaria, textTotalRestaurante, textTotalVinicola;
    private TextView textQuantidadeFeira, textQuantidadeLanchonete, textQuantidadeMercado, textQuantidadePadaria, textQuantidadeRestaurante, textQuantidadeVinicola;
    private int qteFeira = 0, qteLanchonete = 0 , qteMercado = 0 , qtePadaria = 0 , qteRestaurante = 0 , qteVinicola = 0 ;

    // Text Estatistica Despesa
    private TextView textQuantidadeDespesas, textMaiorGastoDespesas, textMenorGastoDespesas;

    // Text Estatistica Estacionamento

    private TextView textQuantidadeVezesEstacionado, textQuantidadeHorasEstacionado,
            textMaiorValorEstacionamento, textLocalMaiorValorEstacionamento, textMenorValorEstacionamento, textLocalMenorValorEstacionamento;

   //Text Estatistica Hospedagem
    private TextView textQuantidadeVezesHospedagem, textQuantidadeDiariasTotal, textQuantidadeDiariasAirbnb,
    textQuantidadeDiariasCampingM, textQuantidadeDiariasCampingP, textQuantidadeDiariasHostel, textQuantidadeDiariasHotel,
    textQuantidadeDiariasPousada, textQuantidadeDiariasResort, textGastoAirbnb, textGastoCampingM, textGastoCampingP, textGastoHostel, textGastoHotel, textGastoPousada, textGastoResort,
           textMaiorValorHospedagem, textLocalMaiorGastoHospedagem, textValorMedioHospedagem;
    private Integer qteAirbnb = 0, qteCampingM = 0, qteCampingP = 0, qteHostel = 0, qteHotel = 0, qtePousada = 0, qteResort = 0;

    // text Estatistica manutenção
    private TextView textQuantidadeManutencoes, textMaiorValorManuencao, textTipoManutencaoMaiorValor, textValorMedioManutencao;

    // text Estatistica Passeio
    private TextView textQuantidadePasseio, textMaiorValorPasseio, textNomePasseioMaiorValor, textLocalPasseioMaiorValor, textValorMedioPasseio;

    // Text Estatistica Pedagio
    private Double pctPessima, pctRuim, pctRegular, pctBoa, pctExcelente;
    private TextView textQuantidadePedagios, textMaiorValorPedagio, textLocalMaiorValorPedagio, textMenorValorPedagio, textLocalMenorValorPedagio,
    textPCTPessimas, textPCTRuins, textPCTRegulares, textPCTBoas, textPCTExcelentes;


    private SQLConnection conn;
    private SQLiteDatabase db;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.estatisticas_layout);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        conn = new SQLConnection(this);
        db = conn.getWritableDatabase();

        Bundle bundle = getIntent().getExtras();
        idViagemAtiva=  bundle.getInt("idViagemAtiva");
        barChart = findViewById(R.id.BarChart);
        read = new ReadDAO(this);
        viagemSelecionada = viagemSelecionada();

        btnPDF = findViewById(R.id.btnPDF);

        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });




        // ESTATISTICAS VIAGEM

        textAbastecimentos = findViewById(R.id.textAbastecimentos);
        textAlimentacoes = findViewById(R.id.textAlimentacoes);
        textDespesas = findViewById(R.id.textDespesas);
        textEstacionamentos = findViewById(R.id.textEstacionamentos);
        textHospedagens = findViewById(R.id.textHospedagens);
        textManutencoes = findViewById(R.id.textManutencoes);
        textPasseios = findViewById(R.id.textPasseios);
        textPedagios = findViewById(R.id.textPedagios);
        textGastosPorDia = findViewById(R.id.textMediaGastoDia);



        textAbastecimentos.setText("Gasto em abastecimentos: R$:" + String.format("%.2f",read.calcularGastoTotalTabela("abastecimento",idViagemAtiva)));
        textAlimentacoes.setText("Gasto em alimentações: R$:" + String.format("%.2f",read.calcularGastoTotalTabela("alimentacao",idViagemAtiva)));
        textDespesas.setText("Gasto em despesas diversas: R$:" + String.format("%.2f",read.calcularGastoTotalTabela("despesa",idViagemAtiva)));
        textEstacionamentos.setText("Gasto em estacionamentos: R$:" + String.format("%.2f",read.calcularGastoTotalTabela("estacionamento",idViagemAtiva)));
        textHospedagens.setText("Gasto em hospedagens: R$:" + String.format("%.2f",read.calcularGastoTotalTabela("hospedagem",idViagemAtiva)));
        textManutencoes.setText("Gasto em manutenções: R$:" + String.format("%.2f",read.calcularGastoTotalTabela("manutencao",idViagemAtiva)));
        textPasseios.setText("Gasto em passeios: R$:" + String.format("%.2f",read.calcularGastoTotalTabela("passeio",idViagemAtiva)));
        textPedagios.setText("Gasto em pedagios: R$:" + String.format("%.2f",read.calcularGastoTotalTabela("pedagio",idViagemAtiva)));
        if(viagemSelecionada.getStatus()){
            textGastosPorDia.setText("Media de gasto por dia: R$:"+ String.format("%.2f",(read.calcularGastoTotal(viagemSelecionada.getId())/viagemSelecionada.calcularDiasAteHoje())) );
        }else {
            textGastosPorDia.setText("Media de gasto por dia: R$:"+ String.format("%.2f", (read.calcularGastoTotal(viagemSelecionada.getId())/viagemSelecionada.calcularDiasEncerrado())) );
        }


        // ESTATISTICAS VIAGEM

        textNomeViagem = findViewById(R.id.textNomeViagem);
        textStatusViagem = findViewById(R.id.textStatusViagem);
        textDiasViajados = findViewById(R.id.textDiasViajados);
        textDataInicioViagem = findViewById(R.id.textDataInicioViagem);
        textLocalDePartidaViagem = findViewById(R.id.textLocalPartidaViagem);
        textKilometragemPercorridaViagem = findViewById(R.id.textKilometragemPercorridaViagem);
        btnVoltar = findViewById(R.id.btnVoltar);

        textNomeViagem.setText("Viagem: " + viagemSelecionada.getNome());
        if(viagemSelecionada.getStatus()){
            textStatusViagem.setText("Status: Em andamento" );
            textDiasViajados.setText("Dias viajados: " + viagemSelecionada.calcularDiasAteHoje());
        }else{
            textStatusViagem.setText("Status: Encerrada" );
            textDiasViajados.setText("Dias viajados: " + viagemSelecionada.calcularDiasEncerrado());
        }

        textDataInicioViagem.setText("Data de inicio: " + formatDate(viagemSelecionada.getDataDeInicio()));
        textLocalDePartidaViagem.setText("Local de partida: " + viagemSelecionada.getLocalDePartida());
        textKilometragemPercorridaViagem.setText("Kilometragem percorrida: " + viagemSelecionada.getKilometragemParcial());

        // ESTATISTICAS ABASTECIMENTO

        textQuantidadeAbastecimentos = findViewById(R.id.textQuantidadeAbastecimentos);
        textPrecoMedioAbastecimentos = findViewById(R.id.textPrecoMedioAbastecimentos);
        textQuantidadeLitrosAbastecimentos = findViewById(R.id.textQuantidadeLitrosAbastecimentos);
        textCaroValorLitroAbastecimentos = findViewById(R.id.textCaroValorLitroAbastecimentos);
        textLocalMaisCaroAbastecimentos = findViewById(R.id.textLocalMaisCaroAbastecimentos);
        textBaratoValorLitroAbastecimentos = findViewById(R.id.textBaratoValorLitroAbastecimentos);
        textLocalMaisBaratoLitroAbastecimentos = findViewById(R.id.textLocalMaisBaratoLitroAbastecimentos);
        textMediaValorLitro = findViewById(R.id.textMediaValorLitro);

        textQuantidadeAbastecimentos.setText("Quantidade de abastecimentos: " + viagemSelecionada.getAbastecimentos().size());
        textPrecoMedioAbastecimentos.setText("Preço médio de abastecimento: R$:" + String.format("%.2f", calcularValorMedioAbastecimento(viagemSelecionada.getAbastecimentos())));
        textQuantidadeLitrosAbastecimentos.setText("Quantidade de litros abastecidos: " + String.format("%.2f", obterQuantidadeDeLitrosAbastecidos(viagemSelecionada.getId())));
        textCaroValorLitroAbastecimentos.setText("Valor por litro mais caro: R$:" + String.format("%.2f", obterMaiorValorPorLitro("abastecimento", viagemSelecionada.getId())));
        textLocalMaisCaroAbastecimentos.setText("Local mais caro: "+ obterLocalMaiorValorPorLitro(viagemSelecionada.getId()));
        textBaratoValorLitroAbastecimentos.setText("Valor por litro mais barato: R$:" + String.format("%.2f", obterMenorValorPorLitro("abastecimento", viagemSelecionada.getId())));
        textLocalMaisBaratoLitroAbastecimentos.setText("Local mais barato: "+ obterLocalMenorValorPorLitro(viagemSelecionada.getId()));
        textMediaValorLitro.setText("Media de valor por litro: R$:" + String.format("%.2f", calcularValorMedioLitros(viagemSelecionada.getAbastecimentos())));

        // ESTATISTICAS ALIMENTACAO

        definirQuantidadesTipoAlimentacao(viagemSelecionada);

        textTotalFeira = findViewById(R.id.textTotalFeira);
        textTotalLanchonete = findViewById(R.id.textTotalLanchonete);
        textTotalMercado = findViewById(R.id.textTotalMercado);
        textTotalPadaria = findViewById(R.id.textTotalPadaria);
        textTotalRestaurante = findViewById(R.id.textTotalRestauntes);
        textTotalVinicola = findViewById(R.id.textTotalVinicolas);

        textQuantidadeFeira = findViewById(R.id.textQuantidadeFeiras);
        textQuantidadeLanchonete = findViewById(R.id.textQuantidadeLanchonetes);
        textQuantidadeMercado = findViewById(R.id.textQuantidadeMercados);
        textQuantidadePadaria = findViewById(R.id.textQuantidadePadarias);
        textQuantidadeRestaurante = findViewById(R.id.textQuantidadeRestaurantes);
        textQuantidadeVinicola = findViewById(R.id.textQuantidadeVinicolas);

        textTotalFeira.setText("Gasto total com Feiras: R$:"+ String.format("%.2f",valorPorTipoAlimentacao("Feira",idViagemAtiva)));
        textTotalLanchonete.setText("Gasto total com Lanchonetes: R$:"+ String.format("%.2f",valorPorTipoAlimentacao("Lanchonete",idViagemAtiva)));
        textTotalMercado.setText("Gasto total com Mercados: R$:"+ String.format("%.2f",valorPorTipoAlimentacao("Mercado",idViagemAtiva)));
        textTotalPadaria.setText("Gasto total com Padarias: R$:"+ String.format("%.2f",valorPorTipoAlimentacao("Padaria",idViagemAtiva)));
        textTotalRestaurante.setText("Gasto total com Restaurantes: R$:"+ String.format("%.2f",valorPorTipoAlimentacao("Restaurante",idViagemAtiva)));
        textTotalVinicola.setText("Gasto total com Vinícolas: R$:"+ String.format("%.2f",valorPorTipoAlimentacao("Vinícola",idViagemAtiva)));

        textQuantidadeFeira.setText("Quantidade de idas a feiras: " + qteFeira);
        textQuantidadeLanchonete.setText("Quantidade de idas a lanchonetes: " + qteLanchonete);
        textQuantidadeMercado.setText("Quantidade de idas a mercados: " + qteMercado);
        textQuantidadePadaria.setText("Quantidade de idas a padarias: " + qtePadaria);
        textQuantidadeRestaurante.setText("Quantidade de idas a restaurantes: " + qteRestaurante);
        textQuantidadeVinicola.setText("Quantidade de idas a vinícolas: " + qteVinicola);


        // ESTATISTICAS DESPESAS GERAIS

        textQuantidadeDespesas = findViewById(R.id.textQuantidadeDespesasExtras);
        textMaiorGastoDespesas = findViewById(R.id.textMaiorGastoDespesas);
        textMenorGastoDespesas = findViewById(R.id.textMenorGastoDespesas);

        textQuantidadeDespesas.setText("Quantidade de despesas extras: " + viagemSelecionada.getDespesas().size());
        textMaiorGastoDespesas.setText("Maior gasto com despesas extras: R$" + String.format("%.2f",obterMaiorValorTabela("despesa", viagemSelecionada.getId())));
        textMenorGastoDespesas.setText("Menor gasto com despesas extras: R$" + String.format("%.2f", obterMenorValorTabela("despesa", viagemSelecionada.getId())));

        // ESTATISTICA ESTACIONAMENTO

        textQuantidadeVezesEstacionado = findViewById(R.id.textQuantidadeVezesEstacionado);
        textQuantidadeHorasEstacionado = findViewById(R.id.textQuantidadeHorasEstacionado);
        textMaiorValorEstacionamento = findViewById(R.id.textMaiorValorEstacionamento);
        textLocalMaiorValorEstacionamento = findViewById(R.id.textLocalMaiorValorEstacionamento);
        textMenorValorEstacionamento = findViewById(R.id.textMenorValorEstacionamento);
        textLocalMenorValorEstacionamento = findViewById(R.id.textLocalMenorValorEstacionamento);

        textQuantidadeVezesEstacionado.setText("Quantidades de vezes estacionado: " + viagemSelecionada.getEstacionamentos().size());
        textQuantidadeHorasEstacionado.setText("Quantidade de horas estacionadas: " + quantidadeHorasEstacionado(viagemSelecionada.getId()));
        textMaiorValorEstacionamento.setText("Maior valor de estacionamento: R$:" + String.format("%.2f", obterMaiorValorTabela("estacionamento", viagemSelecionada.getId())));
        textLocalMaiorValorEstacionamento.setText("Local de maior valor: " + obterLocalComMaiorValorTabela("estacionamento", viagemSelecionada.getId()));
        textMenorValorEstacionamento.setText("Menor valor de estacionamento: R$:" + String.format("%.2f", obterMenorValorTabela("estacionamento", viagemSelecionada.getId())));
        textLocalMenorValorEstacionamento.setText("Local de menor valor: " + obterLocalComMenorValorTabela("estacionamento", viagemSelecionada.getId()));


        // ESTATISTICA HOSPEDAGEM

        definirQuantidadesTipoHospedagem(viagemSelecionada);

        textQuantidadeVezesHospedagem = findViewById(R.id.textQuantidadeVezesHospedado);
        textQuantidadeDiariasTotal = findViewById(R.id.textQuantidadeDiariasTotal);
        textQuantidadeDiariasAirbnb = findViewById(R.id.textQuantidadeDiariasAirbnb);
        textQuantidadeDiariasCampingM = findViewById(R.id.textQuantidadeDiariasCampingM);
        textQuantidadeDiariasCampingP = findViewById(R.id.textQuantidadeDiariasCampingP);
        textQuantidadeDiariasHostel = findViewById(R.id.textQuantidadeDiariasHostel);
        textQuantidadeDiariasHotel = findViewById(R.id.textQuantidadeDiariasHotel);
        textQuantidadeDiariasPousada = findViewById(R.id.textQuantidadeDiariasPousada);
        textQuantidadeDiariasResort = findViewById(R.id.textQuantidadeDiariasResort);

        textGastoAirbnb = findViewById(R.id.textGastoAirbnb);
        textGastoCampingM = findViewById(R.id.textGastoCampingM);
        textGastoCampingP = findViewById(R.id.textGastoCampingP);
        textGastoHostel = findViewById(R.id.textGastoHostel);
        textGastoHotel = findViewById(R.id.textGastoHotel);
        textGastoPousada = findViewById(R.id.textGastoPousada);
        textGastoResort = findViewById(R.id.textGastoResort);

        textMaiorValorHospedagem = findViewById(R.id.textMaiorValordeHospedagem);
        textLocalMaiorGastoHospedagem = findViewById(R.id.textLocalMaiorGastoHospedagem);
        textValorMedioHospedagem = findViewById(R.id.textValorMedioHospedagem);

        textQuantidadeVezesHospedagem.setText("Quantidade de vezes hospedado: " + viagemSelecionada.getHospedagens().size());
        textQuantidadeDiariasTotal.setText("Quantidade total de diárias: " + obterQuantidadeDiariasTotal(viagemSelecionada));
        textQuantidadeDiariasAirbnb.setText("Quantidade de diárias em AirBnB: " + obterQuantidadeDiariasPorTipo(viagemSelecionada, "AirBnB"));
        textQuantidadeDiariasCampingM.setText("Quantidade de diárias em Camping Municipal: " + obterQuantidadeDiariasPorTipo(viagemSelecionada, "Camping Municipal"));
        textQuantidadeDiariasCampingP.setText("Quantidade de diárias em Camping Particular: " + obterQuantidadeDiariasPorTipo(viagemSelecionada, "Camping Privado"));
        textQuantidadeDiariasHostel.setText("Quantidade de diárias em Hostel: " + obterQuantidadeDiariasPorTipo(viagemSelecionada, "Hostel"));
        textQuantidadeDiariasHotel.setText("Quantidade de diárias em Hotel: " + obterQuantidadeDiariasPorTipo(viagemSelecionada, "Hotel"));
        textQuantidadeDiariasPousada.setText("Quantidade de diárias em Pousada: " + obterQuantidadeDiariasPorTipo(viagemSelecionada, "Pousada"));
        textQuantidadeDiariasResort.setText("Quantidade de diárias em Resort: " + obterQuantidadeDiariasPorTipo(viagemSelecionada, "Resort"));

        textGastoAirbnb.setText("Gasto em AirBnB: R$:" + String.format("%.2f", obterValorHospedagemPorTipo(viagemSelecionada, "AirBnB")));
        textGastoCampingM.setText("Gasto em Camping Municipal: R$:" + String.format("%.2f", obterValorHospedagemPorTipo(viagemSelecionada, "Camping Municipal")));
        textGastoCampingP.setText("Gasto em Camping Privado: R$:" + String.format("%.2f", obterValorHospedagemPorTipo(viagemSelecionada, "Camping Privado")));
        textGastoHostel.setText("Gasto em Hostel: R$:" + String.format("%.2f", obterValorHospedagemPorTipo(viagemSelecionada, "Hostel")));
        textGastoHotel.setText("Gasto em Hotel: R$:" + String.format("%.2f", obterValorHospedagemPorTipo(viagemSelecionada, "Hotel")));
        textGastoPousada.setText("Gasto em Pousada: R$:" + String.format("%.2f", obterValorHospedagemPorTipo(viagemSelecionada, "Pousada")));
        textGastoResort.setText("Gasto em Resort: R$:" + String.format("%.2f", obterValorHospedagemPorTipo(viagemSelecionada, "Resort")));

        textMaiorValorHospedagem.setText("Maior valor de hospedagem: R$:" + obterMaiorValorTabela("hospedagem", viagemSelecionada.getId()));
        textLocalMaiorGastoHospedagem.setText("Local de maior gasto: "+ obterLocalComMaiorValorTabela("hospedagem", viagemSelecionada.getId()) );
        textValorMedioHospedagem.setText("Valor médio de hospedagem: R$:" + String.format("%.2f", calcularValorMedioHospedagem(viagemSelecionada.getHospedagens())));


        // ESTATISCA MANUTENÇÕES

        textQuantidadeManutencoes = findViewById(R.id.textQuantidadeManutencoes);
        textMaiorValorManuencao = findViewById(R.id.textMaiorValorGastoManutencao);
        textTipoManutencaoMaiorValor = findViewById(R.id.textTipoManutencaoMaiorGasto);
        textValorMedioManutencao = findViewById(R.id.textValorMedioManutencao);

        textQuantidadeManutencoes.setText("Quantidade de manutenções feitas: " + viagemSelecionada.getManutencoes().size());
        textMaiorValorManuencao.setText("Maior valor gasto: R$:" + String.format("%.2f", obterMaiorValorTabela("manutencao", viagemSelecionada.getId())));
        textTipoManutencaoMaiorValor.setText("Tipo de manutenção de maior gasto: " + obterTipoManutencaoMaiorValor(viagemSelecionada.getId()));
        textValorMedioManutencao.setText("Valor medio de manutenção: R$:" + String.format("%.2f", calcularValorMedioManutencao(viagemSelecionada.getManutencoes())) );

        // ESTATISTICA PASSEIO

        textQuantidadePasseio = findViewById(R.id.textQuantidadePasseio);
        textMaiorValorPasseio = findViewById(R.id.textMaiorValorPasseio);
        textNomePasseioMaiorValor = findViewById(R.id.textNomePasseioMaiorValor);
        textLocalPasseioMaiorValor = findViewById(R.id.textLocalPasseioMaiorValor);
        textValorMedioPasseio = findViewById(R.id.textValorMedioPasseio);

        textQuantidadePasseio.setText("Quantidade de passeios realizados: " + viagemSelecionada.getPasseios().size());
        textMaiorValorPasseio.setText("Maior valor gasto em passeio: R$:" + String.format("%.2f", obterMaiorValorTabela("passeio", viagemSelecionada.getId())));
        textNomePasseioMaiorValor.setText("Nome do passeio de maior valor: " + obterNomePasseioMaiorValor(viagemSelecionada.getId()));
        textLocalPasseioMaiorValor.setText("Local do passeio de maior valor: " + obterLocalComMaiorValorTabela("passeio", viagemSelecionada.getId()));
        textValorMedioPasseio.setText("Valor médio de passeio: " + String.format("%.2f",calcularValorMedioPasseio(viagemSelecionada.getPasseios())));


      //ESTATISTICA PEDAGIO

        calcularPorcentagens(viagemSelecionada.getPedagios());
        textQuantidadePedagios = findViewById(R.id.textQuantidadePedagios);
        textMaiorValorPedagio = findViewById(R.id.textMaiorValorPedagio);
        textLocalMaiorValorPedagio = findViewById(R.id.textLocalMaiorValorPedagio);
        textMenorValorPedagio = findViewById(R.id.textMenorValorPedagio);
        textLocalMenorValorPedagio = findViewById(R.id.textLocalMenorValorPedagio);
        textPCTPessimas = findViewById(R.id.textPCTPessimas);
        textPCTRuins = findViewById(R.id.textPCTRuins);
        textPCTRegulares = findViewById(R.id.textPCTRegulares);
        textPCTBoas = findViewById(R.id.textPCTBoas);
        textPCTExcelentes = findViewById(R.id.textPCTExcelentes);


        textQuantidadePedagios.setText("Quantidade de pedágios pagos: " + viagemSelecionada.getPedagios().size());
        textMaiorValorPedagio.setText("Maior valor gasto com pedágio: R$:" + String.format("%.2f", obterMaiorValorTabela("pedagio", viagemSelecionada.getId())));
        textLocalMaiorValorPedagio.setText("Local maior valor: " + obterLocalComMaiorValorTabela("pedagio", viagemSelecionada.getId()));
        textMenorValorPedagio.setText("Menor valor gasto com pedágio: R$:" + String.format("%.2f", obterMenorValorTabela("pedagio", viagemSelecionada.getId())));
        textLocalMenorValorPedagio.setText("Local menor valor: " + obterLocalComMenorValorTabela("pedagio", viagemSelecionada.getId()));
        textPCTPessimas.setText("Porcentagem de vias péssimas: " + String.format("%.2f",pctPessima) +"%");
        textPCTRuins.setText("Porcentagem de vias ruins: " + String.format("%.2f",pctRuim) +"%");
        textPCTRegulares.setText("Porcentagem de vias regulares: " + String.format("%.2f",pctRegular) +"%");
        textPCTBoas.setText("Porcentagem de vias boas: " + String.format("%.2f",pctBoa) +"%");
        textPCTExcelentes.setText("Porcentagem de vias excelentes: " + String.format("%.2f",pctExcelente) +"%");


        //CRIAÇÃO DO GRAFICO

        BarDataSet barDataSet = new BarDataSet(dataValues(),"Gastos");
        barDataSet.setColors(colorClassArray);
        BarData barData = new BarData(barDataSet);

        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter(){
            private final String[] labels = new String[]{
                    "Abastecimento", "Alimentação", "Despesas Gerais", "Estacionamento",
                    "Hospedagem", "Manutenção", "Passeio", "Pedagio"
            };

            @Override
            public String getFormattedValue(float value) {
                return labels[(int) value - 1];
            }

        });

        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(45);
        xAxis.setTextColor(Color.WHITE);

        barChart.setClickable(true);
        barChart.setPinchZoom(false);
        barChart.setHighlightFullBarEnabled(true);
        barChart.getDescription().setEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);


        barChart.invalidate();


        // BOTÃO VOLTAR

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuEstatisticas.this, MenuDadosIcones.class);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                startActivity(intent);
                finish();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }





    // -------------------------------------- METODOS AUXILIARES ---------------------------------------

    public Viagem viagemSelecionada(){

        Viagem viagem = read.readViagemById(idViagemAtiva);

        viagem.setAbastecimentos(read.readAbastecimentoByIdViagem(viagem.getId()));
        viagem.setAlimentacoes(read.readAlimentacaoByIdViagem(viagem.getId()));
        viagem.setDespesas(read.readDespesaByIdViagem(viagem.getId()));
        viagem.setDiarios(read.readDiarioByIdViagem(viagem.getId()));
        viagem.setEstacionamentos(read.readEstacionamentoByIdViagem(viagem.getId()));
        viagem.setHospedagens(read.readHospedagemByIdViagem(viagem.getId()));
        viagem.setManutencoes(read.readManutencaoByIdViagem(viagem.getId()));
        viagem.setPasseios(read.readPasseioByIdViagem(viagem.getId()));
        viagem.setPedagios(read.readPedagioByIdViagem(viagem.getId()));

        return viagem;
    }

    public static String formatDate(LocalDate date) {
        String dataFormatada = "";
        DateTimeFormatter outputFormatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataFormatada = date.format(outputFormatter);
        }
        return dataFormatada;
    }

    private ArrayList<BarEntry> dataValues(){
        ArrayList<BarEntry> dataChart = new ArrayList<BarEntry>();

        dataChart.add(new BarEntry(1,read.calcularGastoTotalTabela("abastecimento",idViagemAtiva).floatValue(),"Abastecimento"));
        dataChart.add(new BarEntry(2,read.calcularGastoTotalTabela("alimentacao",idViagemAtiva).floatValue(), "Alimentação"));
        dataChart.add(new BarEntry(3,read.calcularGastoTotalTabela("despesa",idViagemAtiva).floatValue(), "Despesas Gerais"));
        dataChart.add(new BarEntry(4,read.calcularGastoTotalTabela("estacionamento",idViagemAtiva).floatValue(), "Estacionamento"));
        dataChart.add(new BarEntry(5,read.calcularGastoTotalTabela("hospedagem",idViagemAtiva).floatValue(), "Hospedagem"));
        dataChart.add(new BarEntry(6,read.calcularGastoTotalTabela("manutencao",idViagemAtiva).floatValue(), "Manutenção"));
        dataChart.add(new BarEntry(7,read.calcularGastoTotalTabela("passeio",idViagemAtiva).floatValue(), "Passeio"));
        dataChart.add(new BarEntry(8,read.calcularGastoTotalTabela("pedagio",idViagemAtiva).floatValue(), "Pedagio"));


        return dataChart;
    }

    @SuppressLint("Range")
    public Double obterMaiorValorPorLitro(String tabela, int idViagemAtiva) {

        String query = "SELECT valorPorLitro FROM " + tabela + " WHERE id_viagem = ? ORDER BY valorPorLitro DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagemAtiva)});
        Double valor = 0.0;

        if (cursor != null && cursor.moveToFirst()) {
            valor = cursor.getDouble(cursor.getColumnIndex("valorPorLitro"));


        } else {

        }

        return valor;
    }

    @SuppressLint("Range")
    public String obterLocalMenorValorPorLitro(int idViagemAtiva) {
        String local = null;

        // Consulta SQL para obter o campo local com o menor valorPorLitro
        String query = "SELECT local FROM abastecimento WHERE valorPorLitro = (SELECT MIN(valorPorLitro) FROM abastecimento WHERE id_viagem = ?) AND id_viagem = ?";

        // Executar a consulta
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagemAtiva), String.valueOf(idViagemAtiva)});

        // Verificar se há resultados
        if (cursor != null && cursor.moveToFirst()) {
            // Recuperar o valor do campo local
            local = cursor.getString(cursor.getColumnIndex("local"));
            cursor.close(); // fechar o cursor
        }

        return local;
    }

    @SuppressLint("Range")
    public String obterLocalMaiorValorPorLitro(int idViagemAtiva) {
        String local = null;

        // Consulta SQL para obter o campo local com o menor valorPorLitro
        String query = "SELECT local FROM abastecimento WHERE valorPorLitro = (SELECT MAX(valorPorLitro) FROM abastecimento WHERE id_viagem = ?) AND id_viagem = ?";

        // Executar a consulta
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagemAtiva), String.valueOf(idViagemAtiva)});

        // Verificar se há resultados
        if (cursor != null && cursor.moveToFirst()) {
            // Recuperar o valor do campo local
            local = cursor.getString(cursor.getColumnIndex("local"));
            cursor.close(); // fechar o cursor
        }

        return local;
    }

    @SuppressLint("Range")
    public Double obterMenorValorPorLitro(String tabela, int idViagemAtiva) {

        String query = "SELECT valorPorLitro FROM " + tabela + " WHERE id_viagem = ? ORDER BY valorPorLitro ASC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagemAtiva)});
        Double valor = 0.0;

        if (cursor != null && cursor.moveToFirst()) {
            valor = cursor.getDouble(cursor.getColumnIndex("valorPorLitro"));


        } else {

        }

        return valor;
    }

    @SuppressLint("Range")
    public Double obterMaiorValorTabela(String tabela, int idViagemAtiva) {

        String query = "SELECT valor FROM " + tabela + " WHERE id_viagem = ? ORDER BY Valor DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagemAtiva)});
        Double valor = 0.0;

        if (cursor != null && cursor.moveToFirst()) {
            valor = cursor.getDouble(cursor.getColumnIndex("valor"));


        } else {

        }

        return valor;
    }

    @SuppressLint("Range")
    public Double obterMenorValorTabela(String tabela, int idViagemAtiva) {

        String query = "SELECT valor FROM " + tabela + " WHERE id_viagem = ? ORDER BY Valor ASC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagemAtiva)});
        Double valor = 0.0;

        if (cursor != null && cursor.moveToFirst()) {
            valor = cursor.getDouble(cursor.getColumnIndex("valor"));


        } else {

        }

        return valor;
    }

    @SuppressLint("Range")
    public Double obterQuantidadeDeLitrosAbastecidos(int idViagemAtiva){
        String query = "SELECT SUM(quantidadeCombustivel) as somaLitros FROM abastecimento WHERE id_viagem =?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagemAtiva)});
        Double quantidadeAbastecida = 0.0;

        if(cursor !=null && cursor.moveToFirst()){
            quantidadeAbastecida = cursor.getDouble(cursor.getColumnIndex("somaLitros"));
        }
        return quantidadeAbastecida;
    }
    public double calcularValorMedioAbastecimento(List<Abastecimento> listaAbastecimentos) {
        // Verificar se a lista não é nula e não está vazia
        if (listaAbastecimentos != null && !listaAbastecimentos.isEmpty()) {
            // Inicializar a variável para armazenar a soma dos valores
            double soma = 0;

            // Percorrer a lista e somar os valores
            for (Abastecimento abastecimento : listaAbastecimentos) {
                soma += abastecimento.getValor();
            }

            // Calcular o valor médio dividindo a soma pelo número de abastecimentos
            return soma / listaAbastecimentos.size();
        } else {
            // Retornar 0 se a lista estiver vazia
            return 0;
        }
    }

    public double calcularValorMedioHospedagem(List<Hospedagem> lista) {
        if (lista != null && !lista.isEmpty()) {
            double soma = 0.0;
            for (Hospedagem h : lista) {
                soma += h.getValor();
            }
            return soma / lista.size();
        } else {
            return 0.0;
        }
    }

    public double calcularValorMedioManutencao(List<Manutencao> lista) {
        if (lista != null && !lista.isEmpty()) {
            double soma = 0.0;
            for (Manutencao m : lista) {
                soma += m.getValor();
            }
            return soma / lista.size();
        } else {
            return 0.0;
        }
    }

    public double calcularValorMedioPasseio(List<Passeio> lista) {
        if (lista != null && !lista.isEmpty()) {
            double soma = 0.0;
            for (Passeio p : lista) {
                soma += p.getValor();
            }
            return soma / lista.size();
        } else {
            return 0.0;
        }
    }

    public double calcularValorMedioLitros(List<Abastecimento> listaAbastecimentos) {

        if (listaAbastecimentos != null && !listaAbastecimentos.isEmpty()) {

            double soma = 0;

            for (Abastecimento abastecimento : listaAbastecimentos) {
                soma += abastecimento.getValorPorLitro();
            }

            return soma / listaAbastecimentos.size();
        } else {
            return 0;
        }
    }

    int[] colorClassArray = new int[]{
            Color.parseColor("#024265"),
            Color.parseColor("#05af9b"),
            Color.parseColor("#fb7268"),
            Color.parseColor("#FFA726"),
            Color.parseColor("#66BB6A"),
            Color.parseColor("#29B6F6"),
            Color.parseColor("#FF009688"),
            Color.parseColor("#FFF30606")
            };

    @SuppressLint("Range")
    public Double valorPorTipoAlimentacao(String tipo, int idViagemAtiva){
        String query = "SELECT SUM(valor) AS somaValor FROM alimentacao  WHERE tipoAlimentacao = ? AND id_viagem = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tipo,String.valueOf(idViagemAtiva)});
        Double valor = 0.0;

        if (cursor != null && cursor.moveToFirst()) {
            valor = cursor.getDouble(cursor.getColumnIndex("somaValor"));


        } else {

        }

        return valor;



    }


    public void definirQuantidadesTipoAlimentacao(Viagem viagem){

        for(Alimentacao a : viagem.getAlimentacoes()){

            if(a.getTipoAlimentacao().equals("Feira")){
                qteFeira++;
            } else if (a.getTipoAlimentacao().equals("Lanchonete")){
                qteLanchonete++;
            }else if (a.getTipoAlimentacao().equals("Mercado")){
                qteMercado++;
            }else if(a.getTipoAlimentacao().equals("Padaria")){
                qtePadaria++;
            } else if (a.getTipoAlimentacao().equals("Restaurante")) {
                qteRestaurante++;
            }else if(a.getTipoAlimentacao().equals("Vinícola")){
                qteVinicola++;
            }

        }

    }

    public void definirQuantidadesTipoHospedagem (Viagem viagem){

        for(Hospedagem h : viagem.getHospedagens()){

            if(h.getTipoHospedagem().equals("AirBnB")){
                qteAirbnb++;
            } else if (h.getTipoHospedagem().equals("Camping Municipal")){
                qteCampingM++;
            }else if (h.getTipoHospedagem().equals("Camping Privado")){
                qteCampingP++;
            }else if(h.getTipoHospedagem().equals("Hostel")){
                qteHostel++;
            } else if (h.getTipoHospedagem().equals("Hotel")) {
                qteHotel++;
            }else if(h.getTipoHospedagem().equals("Pousada")){
                qtePousada++;
            }else if(h.getTipoHospedagem().equals("Resort")){
                qteResort++;
            }

        }

    }

    @SuppressLint("Range")
    public Integer quantidadeHorasEstacionado(int idViagemAtiva){
        String query = "SELECT SUM(valor) as somaValor FROM estacionamento WHERE id_viagem = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagemAtiva)});
        Integer valor = 0;

        if(cursor!=null && cursor.moveToFirst()){
            valor = cursor.getInt(cursor.getColumnIndex("somaValor"));
        }
        return valor;
    }


    public String obterLocalComMaiorValorTabela(String tabela, int idViagem) {
        String local = null;

        String query = "SELECT local, valor FROM "+ tabela +" WHERE id_viagem = ? ORDER BY valor DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagem)});

        if (cursor.moveToFirst()) {
            local = cursor.getString(cursor.getColumnIndexOrThrow("local"));
        }
        cursor.close();

        return local;
    }

    public String obterLocalComMenorValorTabela(String tabela, int idViagem) {
        String local = null;

        String query = "SELECT local, valor FROM " + tabela + " WHERE id_viagem = ? ORDER BY valor ASC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagem)});

        if (cursor.moveToFirst()) {
            local = cursor.getString(cursor.getColumnIndexOrThrow("local"));
        }
        cursor.close();

        return local;
    }

    @SuppressLint("Range")
    public String obterTipoManutencaoMaiorValor(Integer idViagem){
        String query = "SELECT tipoDeManutencao from manutencao WHERE id_viagem = ? ORDER BY valor DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagem)});
        String tipo = null;

        if(cursor.moveToFirst()){
            tipo = cursor.getString(cursor.getColumnIndex("tipoDeManutencao"));
        }

        return tipo;
    }

    @SuppressLint("Range")
    public String obterNomePasseioMaiorValor(Integer idViagem){

        String query = "SELECT nomePasseio from passeio where id_viagem = ? ORDER BY valor DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagem)});
        String nome = null;

        if(cursor.moveToFirst()){
            nome = cursor.getString(cursor.getColumnIndex("nomePasseio"));
        }
        return nome;
    }



    public Integer obterQuantidadeDiariasTotal(Viagem viagem){
        Integer quantidadeDiarias = 0;

        for(Hospedagem h : viagem.getHospedagens()){
            quantidadeDiarias += h.getQuantidadeDeDiarias();
        }

        return quantidadeDiarias;
    }

    public Integer obterQuantidadeDiariasPorTipo(Viagem viagem, String tipo){
        Integer quantidadeDiarias = 0;

        for(Hospedagem h : viagem.getHospedagens()){
            if(h.getTipoHospedagem().equals(tipo)){
                quantidadeDiarias+= h.getQuantidadeDeDiarias();
            }
        }
        return quantidadeDiarias;
    }

    public Double obterValorHospedagemPorTipo(Viagem viagem, String tipo){

        Double valorTotal = 0.0;

        for(Hospedagem h : viagem.getHospedagens()){
            if(h.getTipoHospedagem().equals(tipo)){
                valorTotal+=h.getValor();
            }
        }
        return valorTotal;
    }

    public void calcularPorcentagens(List<Pedagio> pedagios) {
        if (pedagios == null || pedagios.isEmpty()) {
            return;
        }

        int total = pedagios.size();
        int countPessima = 0;
        int countRuim = 0;
        int countRegular = 0;
        int countBoa = 0;
        int countExcelente = 0;

        for (Pedagio pedagio : pedagios) {
            String qualidade = pedagio.getQualidadeDaVia();

            switch (qualidade) {
                case "Péssima":
                    countPessima++;
                    break;
                case "Ruim":
                    countRuim++;
                    break;
                case "Regular":
                    countRegular++;
                    break;
                case "Boa":
                    countBoa++;
                    break;
                case "Excelente":
                    countExcelente++;
                    break;
            }
        }

        pctPessima = (countPessima / (double) total) * 100;
        pctRuim = (countRuim / (double) total) * 100;
        pctRegular = (countRegular / (double) total) * 100;
        pctBoa = (countBoa / (double) total) * 100;
        pctExcelente = (countExcelente / (double) total) * 100;
    }



}




