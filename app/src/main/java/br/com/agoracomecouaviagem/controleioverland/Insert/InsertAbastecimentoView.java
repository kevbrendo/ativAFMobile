package br.com.agoracomecouaviagem.controleioverland.Insert;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import br.com.agoracomecouaviagem.controleioverland.Fragments.AbastecimentoReadFragment;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.Entities.Abastecimento;
import br.com.agoracomecouaviagem.controleioverland.DAO.InsertDAO;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.AbastecimentoRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;
import br.com.agoracomecouaviagem.controleioverland.ViewsMenu.MenuDadosViagem;

public class InsertAbastecimentoView extends AppCompatActivity {

    private Spinner spinner;
    private EditText data;
    private EditText valor;
    private EditText combustivel;
    private EditText local;
    private Switch switchData;
    private EditText descricao;

    private InsertDAO dao;
    private int idViagemAtiva;
    private Button btnSalvar;
    private Button btnAtualizar;
    private boolean changingText = false;
    private AbastecimentoReadFragment abastecimentoReadFragment;
    private String nomeViagemAtiva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.insertabastecimendo_main);

        dao = new InsertDAO(this);



        // Iniciando Spinner e dando o Array de opções
        spinner = findViewById(R.id.spnPagamento);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.formas_pagamento, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        data = findViewById(R.id.editData);
        valor = findViewById(R.id.editValor);
        combustivel = findViewById(R.id.editHoras);
        local = findViewById(R.id.editLocal);
        spinner = findViewById(R.id.spnPagamento);
        descricao = findViewById(R.id.editDescricao);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnSalvar =findViewById(R.id.btnSalvar);
        switchData = findViewById(R.id.switchHoje);


        btnAtualizar.setVisibility(View.GONE);
        btnSalvar.setVisibility(View.VISIBLE);

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


        DataTextWatcher dataTextWatcher = new DataTextWatcher(data,changingText);
        data.addTextChangedListener(dataTextWatcher);

        Bundle bundle = getIntent().getExtras();
        idViagemAtiva = bundle.getInt("idViagemAtiva");
        nomeViagemAtiva = bundle.getString("nomeViagemAtiva");


        abastecimentoReadFragment = new AbastecimentoReadFragment();
        abastecimentoReadFragment.setArguments(bundle);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


    }

    public void inserirAbastecimento(View view) {
        String stringData = data.getText().toString();
        String stringDescricao = descricao.getText().toString();
        String stringCategoria = "Abastecimento";
        String stringPagamento = (String) spinner.getSelectedItem();
        String stringLocal = local.getText().toString();
        String stringValor = valor.getText().toString();
        stringValor = stringValor.replace(",",".");
        Double valorCorrigido = Double.parseDouble(stringValor);
        String stringCombustivel = combustivel.getText().toString();
        stringCombustivel = stringCombustivel.replace(",",".");


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


        if (stringData.isEmpty() || stringPagamento.isEmpty() || stringLocal.isEmpty() || stringValor.isEmpty() || stringCombustivel.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Abastecimento abastecimento = new Abastecimento();
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
                                abastecimento.setData(dataLocalDate); // Data
                                abastecimento.setDescricao(stringDescricao); // Descrição
                                abastecimento.setValor(valorCorrigido); // Valor
                                abastecimento.setCategoria(stringCategoria); // Categoria
                                abastecimento.setMetodoPagamento(stringPagamento); // Método de pagamento
                                abastecimento.setLocal(stringLocal); // Local
                                abastecimento.setQuantidadeCombustivel(Double.parseDouble(combustivel.getText().toString())); // Combustível
                                abastecimento.setValorPorLitro(abastecimento.getValor() / abastecimento.getQuantidadeCombustivel()); // Valor por litro
                                abastecimento.setId_viagem(idViagemAtiva);

                                Log.d("Categoria: ",abastecimento.getCategoria());
                                long id = dao.addAbastecimento(abastecimento);

                                Toast.makeText(this, "Abastecimento inserido com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), MenuDadosViagem.class);
                                intent.putExtra("fragment_id", 1);
                                intent.putExtra("idViagemAtiva", idViagemAtiva);
                                intent.putExtra("nomeViagemAtiva", nomeViagemAtiva);
                                startActivity(intent);
                                finish();
                                getSupportFragmentManager().popBackStack();

                            })
                            .setNegativeButton("Não", (dialog, which) -> {
                                // O usuário cancelou a operação
                                Toast.makeText(this, "Operação cancelada", Toast.LENGTH_SHORT).show();
                            })
                            .create()
                            .show();
                } else {

                    abastecimento.setData(dataLocalDate); // Data
                    abastecimento.setDescricao(stringDescricao); // Descrição
                    abastecimento.setValor(valorCorrigido); // Valor
                    abastecimento.setCategoria(stringCategoria); // Categoria
                    abastecimento.setMetodoPagamento(stringPagamento); // Método de pagamento
                    abastecimento.setLocal(stringLocal); // Local
                    abastecimento.setQuantidadeCombustivel(Double.parseDouble(combustivel.getText().toString())); // Combustível
                    abastecimento.setValorPorLitro(abastecimento.getValor() / abastecimento.getQuantidadeCombustivel()); // Valor por litro
                    abastecimento.setId_viagem(idViagemAtiva);
                    long id = dao.addAbastecimento(abastecimento);
                    Log.d("Categoria: ",abastecimento.getCategoria());

                    Toast.makeText(this, "Abastecimento inserido com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), AbastecimentoRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                    finish();
                    getSupportFragmentManager().popBackStack();

                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao inserir abastecimento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void atualizarAbastecimento(View view) {
        String stringData = data.getText().toString();
        String stringDescricao = descricao.getText().toString();
        String stringCategoria = "Abastecimento";
        String stringPagamento = (String) spinner.getSelectedItem();
        String stringLocal = local.getText().toString();
        String stringValor = valor.getText().toString();
        stringValor = stringValor.replace(",",".");
        Double valorCorrigido = Double.parseDouble(stringValor);
        String stringCombustivel = combustivel.getText().toString();
        stringCombustivel = stringCombustivel.replace(",",".");


        if (stringData.isEmpty() || stringDescricao.isEmpty() || stringPagamento.isEmpty() || stringLocal.isEmpty() || stringValor.isEmpty() || stringCombustivel.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Abastecimento abastecimento = new Abastecimento();
            DateTimeFormatter dtf = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataLocalDate = LocalDate.parse(DataTextWatcher.converterData(stringData));
                LocalDate dataAtual = LocalDate.now();

                if (dataLocalDate.isAfter(dataAtual)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Confirmação")
                            .setMessage("Você está adicionando um gasto em uma data futura. Deseja continuar?")
                            .setPositiveButton("Sim", (dialog, which) -> {
                                abastecimento.setData(dataLocalDate); // Data
                                abastecimento.setDescricao(stringDescricao); // Descrição
                                abastecimento.setValor(valorCorrigido); // Valor
                                abastecimento.setCategoria(stringCategoria); // Categoria
                                abastecimento.setMetodoPagamento(stringPagamento); // Método de pagamento
                                abastecimento.setLocal(stringLocal); // Local
                                abastecimento.setQuantidadeCombustivel(Double.parseDouble(combustivel.getText().toString())); // Combustível
                                abastecimento.setValorPorLitro(abastecimento.getValor() / abastecimento.getQuantidadeCombustivel()); // Valor por litro
                                abastecimento.setId_viagem(idViagemAtiva);

                                Log.d("Categoria: ",abastecimento.getCategoria());
                                long id = dao.addAbastecimento(abastecimento);

                                Toast.makeText(this, "Abastecimento atualizado com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), AbastecimentoRecyclerView.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

                    abastecimento.setData(dataLocalDate); // Data
                    abastecimento.setDescricao(stringDescricao); // Descrição
                    abastecimento.setValor(valorCorrigido); // Valor
                    abastecimento.setCategoria(stringCategoria); // Categoria
                    abastecimento.setMetodoPagamento(stringPagamento); // Método de pagamento
                    abastecimento.setLocal(stringLocal); // Local
                    abastecimento.setQuantidadeCombustivel(Double.parseDouble(combustivel.getText().toString())); // Combustível
                    abastecimento.setValorPorLitro(abastecimento.getValor() / abastecimento.getQuantidadeCombustivel()); // Valor por litro
                    abastecimento.setId_viagem(idViagemAtiva);
                    long id = dao.addAbastecimento(abastecimento);
                    Log.d("Categoria: ",abastecimento.getCategoria());
                    Toast.makeText(this, "Abastecimento atualizado com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), AbastecimentoRecyclerView.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                    finish();

                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao inserir abastecimento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }




}