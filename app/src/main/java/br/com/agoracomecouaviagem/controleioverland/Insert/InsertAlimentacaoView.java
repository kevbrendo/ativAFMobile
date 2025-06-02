package br.com.agoracomecouaviagem.controleioverland.Insert;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.agoracomecouaviagem.controleioverland.DAO.InsertDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.UpdateDAO;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.AlimentacaoRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;
import br.com.agoracomecouaviagem.controleioverland.Entities.Alimentacao;

public class InsertAlimentacaoView extends AppCompatActivity {

    private Switch switchData;
    private Spinner spinner;
    private Spinner spnTipoAlimentacao;
    private EditText data;
    private EditText valor;
    private EditText local;

    private EditText descricao;
    private UpdateDAO update;
    private InsertDAO dao;
    private ReadDAO read;


    private boolean changingText = false;
    private int idViagemAtiva;
    private String nomeViagemAtiva;
    private Button btnSalvar;
    private Button btnAtualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.insertalimentacao_main);

        dao = new InsertDAO(this);
        read = new ReadDAO(this);
        update = new UpdateDAO(this);


        // Iniciando Spinner e dando o Array de opções
        spinner = findViewById(R.id.spnPagamento);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.formas_pagamento, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnSalvar =findViewById(R.id.btnSalvar);

        btnAtualizar.setVisibility(View.GONE);
        btnSalvar.setVisibility(View.VISIBLE);

        spnTipoAlimentacao = findViewById(R.id.spnTipoAlimentacao);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.tipo_alimentacao, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoAlimentacao.setAdapter(adapter1);


        data = findViewById(R.id.editData);
        valor = findViewById(R.id.editValor);
        local = findViewById(R.id.editLocal);
        spinner = findViewById(R.id.spnPagamento);
        String formaPagamentoSelecionada = (String) spinner.getSelectedItem();
        spnTipoAlimentacao = findViewById(R.id.spnTipoAlimentacao);
        String tipoAlimentacao = (String) spnTipoAlimentacao.getSelectedItem();
        descricao = findViewById(R.id.editDescricao);
        switchData = findViewById(R.id.switchHoje);

        data.setVisibility(View.INVISIBLE);
        switchData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // Switch desmarcado, torna o EditText de inserção de data visível
                    data.setVisibility(View.VISIBLE);
                    data.requestFocus(); // Foca no EditText para que o teclado apareça automaticamente
                    switchData.setVisibility(View.INVISIBLE);
                } else {
                    // Switch marcado, oculta o EditText de inserção de data
                    data.setVisibility(View.INVISIBLE);

                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idViagemAtiva = bundle.getInt("idViagemAtiva");
            nomeViagemAtiva = bundle.getString("nomeViagemAtiva");

        } else {
            Toast.makeText(this, "O Bundle está nulo. Verifique se o valor de idViagemAtiva foi passado corretamente.", Toast.LENGTH_SHORT).show();
        }


        idViagemAtiva = bundle.getInt("idViagemAtiva");


        DataTextWatcher dataTextWatcher = new DataTextWatcher(data,changingText);
        data.addTextChangedListener(dataTextWatcher);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


    }


    public void inserirAlimentacao(View view) {
        String stringData = data.getText().toString();
        String stringDescricao = descricao.getText().toString();
        String stringValor = valor.getText().toString();
        stringValor = stringValor.replace(",",".");
        Double valorCorrigido = Double.parseDouble(stringValor);
        String stringCategoria = "Alimentacao";
        String stringPagamento = (String) spinner.getSelectedItem();
        String stringLocal = local.getText().toString();
        String stringTipoAlimentacao = spnTipoAlimentacao.getSelectedItem().toString();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataCheck = LocalDate.now();
            if (!switchData.isChecked()) {
                stringData = data.getText().toString();
                String dataConvert =  DataTextWatcher.converterData(stringData);
                LocalDate dataDigitada = LocalDate.parse(dataConvert);
                stringData = dataDigitada.toString();
            } else {
                LocalDate dataAtual = LocalDate.parse(dataCheck.toString(),dtf);
                stringData = dataAtual.toString();
            }
        }

        if (stringData.isEmpty() || stringPagamento.isEmpty() || stringLocal.isEmpty() || stringValor.isEmpty() || stringTipoAlimentacao.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Alimentacao alimentacao = new Alimentacao();
            DateTimeFormatter dtf = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataLocalDate = LocalDate.parse(stringData);
                LocalDate dataAtual = LocalDate.now();

                if (dataLocalDate.isAfter(dataAtual)) {

                    new AlertDialog.Builder(this)
                            .setTitle("Confirmação")
                            .setMessage("Você está adicionando um gasto em uma data futura. Deseja continuar?")
                            .setPositiveButton("Sim", (dialog, which) -> {
                                alimentacao.setData(dataLocalDate); // Data
                                alimentacao.setDescricao(stringDescricao); // Descrição
                                alimentacao.setValor(valorCorrigido); // Valor
                                alimentacao.setCategoria(stringCategoria); // Categoria
                                alimentacao.setMetodoPagamento(stringPagamento); // Método de pagamento
                                alimentacao.setLocal(stringLocal); // Local
                                alimentacao.setTipoAlimentacao(stringTipoAlimentacao);
                                alimentacao.setId_viagem(idViagemAtiva);

                                long id = dao.addAlimentacao(alimentacao);

                                Toast.makeText(this, "Alimentação inserida com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), AlimentacaoRecyclerView.class);
                                intent.putExtra("idViagemAtiva", idViagemAtiva);
                                startActivity(intent);
                                finish();

                            })
                            .setNegativeButton("Não", (dialog, which) -> {
                                // O usuário cancelou a operação
                                Toast.makeText(this, "Operação cancelada", Toast.LENGTH_SHORT).show();
                            })
                            .create()
                            .show();
                } else {

                    alimentacao.setData(dataLocalDate); // Data
                    alimentacao.setDescricao(stringDescricao); // Descrição
                    alimentacao.setValor(valorCorrigido); // Valor
                    alimentacao.setCategoria(stringCategoria); // Categoria
                    alimentacao.setMetodoPagamento(stringPagamento); // Método de pagamento
                    alimentacao.setLocal(stringLocal); // Local
                    alimentacao.setTipoAlimentacao(stringTipoAlimentacao);
                    alimentacao.setId_viagem(idViagemAtiva);


                    long id = dao.addAlimentacao(alimentacao);

                    Toast.makeText(this, "Alimentação inserida com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), AlimentacaoRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                    finish();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao inserir alimentação: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



}