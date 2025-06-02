package br.com.agoracomecouaviagem.controleioverland.ViewsMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.Edits.EditViagemView;
import br.com.agoracomecouaviagem.controleioverland.Entities.Viagem;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.AbastecimentoRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.AlimentacaoRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.DespesaRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.DiarioRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.EstacionamentoRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.HospedagemRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.ManutencaoRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.PasseioRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.PedagioRecyclerView;

public class MenuDadosIcones extends AppCompatActivity {
    //<color name="colorBG">#CBFFC107</color>
    //<color name="CorBotoes">#5E715F</color>

    private Spinner spnMes;
    private TextView textGastoDoMes;
    private ImageView iconeAbastecimento;
    private ImageView iconeAlimentacao;
    private ImageView iconeDespesa;
    private ImageView iconeDiario;
    private ImageView iconeEstacionamento;
    private ImageView iconeHospedagem;
    private ImageView iconeManutencao;
    private ImageView iconePasseio;
    private ImageView iconePedagio;
    private ImageView iconeEditarViagem;
    private ImageView iconeEstatistica;
    private Integer idViagemAtiva;
    private Viagem viagemAtiva;
    private ReadDAO read;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.layout_menu_icones);

        iconeAbastecimento = findViewById(R.id.iconeAbastecimento);
        iconeAlimentacao = findViewById(R.id.iconeAlimentacao);
        iconeDespesa = findViewById(R.id.iconeDespesa);
        iconeDiario = findViewById(R.id.iconeDiario);
        iconeEstacionamento = findViewById(R.id.iconeEstacionamento);
        iconeHospedagem = findViewById(R.id.iconeHospedagem);
        iconeManutencao = findViewById(R.id.iconeManutencao);
        iconePasseio = findViewById(R.id.iconePasseio);
        iconePedagio = findViewById(R.id.iconePedagio);
        iconeEstatistica = findViewById(R.id.iconeEstatisticas);
        iconeEditarViagem = findViewById(R.id.iconeEditarViagem);
        spnMes = findViewById(R.id.spinnerEscolherMes);
        textGastoDoMes = findViewById(R.id.textGastoDoMes);

        read = new ReadDAO(this);
        Bundle bundle = getIntent().getExtras();
        idViagemAtiva = bundle.getInt("idViagemAtiva");

        viagemAtiva = read.readViagemById(idViagemAtiva);



        //POPULANDO O SPINNER COM OS MESES E MOSTRANDO


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate dataAtual = LocalDate.now();
            LocalDate dataDeInicio = viagemAtiva.getDataDeInicio();
            ArrayList<String> mesesViagem = new ArrayList<>();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");

            while (!dataAtual.isBefore(dataDeInicio)) {
                mesesViagem.add(dtf.format(dataAtual));
                dataAtual = dataAtual.minusMonths(1);
            }

            if (!mesesViagem.contains(dtf.format(dataDeInicio))) {
                mesesViagem.add(dtf.format(dataDeInicio));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, mesesViagem);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spnMes.setAdapter(adapter);


            spnMes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedDate = parent.getItemAtPosition(position).toString();
                    String[] dateParts = selectedDate.split("/");
                    int mes = Integer.parseInt(dateParts[0]);
                    int ano = Integer.parseInt(dateParts[1]);
                    double gastos = read.CalcularGastosPorMesByIdViagem(viagemAtiva.getId(),mes, ano);
                    textGastoDoMes.setText("Gastos de " + selectedDate + " R$: " + String.format("%.2f", gastos));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });
        }


            iconeAbastecimento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuDadosIcones.this, AbastecimentoRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                }
            });

            // Alimentação
            iconeAlimentacao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuDadosIcones.this, AlimentacaoRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                }
            });

            // Despesa
            iconeDespesa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuDadosIcones.this, DespesaRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                }
            });

            // Diario
            iconeDiario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuDadosIcones.this, DiarioRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                }
            });

            // Estacionamento
            iconeEstacionamento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuDadosIcones.this, EstacionamentoRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                }
            });

            // Hospedagem
            iconeHospedagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuDadosIcones.this, HospedagemRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                }
            });

            // Manutencao
            iconeManutencao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuDadosIcones.this, ManutencaoRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                }
            });

            // Passeio
            iconePasseio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuDadosIcones.this, PasseioRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                }
            });

            // Pedagio
            iconePedagio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuDadosIcones.this, PedagioRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                }
            });

            // Editar Viagem
            iconeEditarViagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        Intent intent = new Intent(MenuDadosIcones.this, EditViagemView.class);
                        intent.putExtra("idViagemAtiva", idViagemAtiva);
                        startActivity(intent);
                    }
                }
            });

            //Estatisticas
            iconeEstatistica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuDadosIcones.this, MenuEstatisticas.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                }
            });


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
