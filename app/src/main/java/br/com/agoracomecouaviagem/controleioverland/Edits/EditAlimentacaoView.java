package br.com.agoracomecouaviagem.controleioverland.Edits;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import br.com.agoracomecouaviagem.controleioverland.DAO.DeleteDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.InsertDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.UpdateDAO;
import br.com.agoracomecouaviagem.controleioverland.Entities.Alimentacao;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.AlimentacaoRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;

public class EditAlimentacaoView extends AppCompatActivity {

    private Switch switchData;
    private Spinner spinner;
    private Spinner spnTipoAlimentacao;
    private EditText data;
    private EditText valor;
    private EditText local;

    private EditText descricao;
    private Button btnSalvar;
    private Button btnAtualizar;
    private Button btnExcluir;
    private InsertDAO insert;
    private int idViagemAtiva;
    private int idAlimentacaoEditar;
    private String nomeViagemAtiva;
    private ReadDAO read;
    private UpdateDAO update;
    private DeleteDAO delete;

    private boolean changingText = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.insertalimentacao_main);

        insert = new InsertDAO(this);
        read = new ReadDAO(this);
        update = new UpdateDAO(this);
        delete = new DeleteDAO(this);


        // Iniciando Spinner e dando o Array de opções
        spinner = findViewById(R.id.spnPagamento);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.formas_pagamento, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spnTipoAlimentacao = findViewById(R.id.spnTipoAlimentacao);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.tipo_alimentacao, android.R.layout.simple_spinner_dropdown_item);
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
        btnSalvar = findViewById(R.id.btnSalvar);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnExcluir = findViewById(R.id.btnExcluir);

        btnSalvar.setVisibility(View.GONE);
        btnAtualizar.setVisibility(View.VISIBLE);
        btnExcluir.setVisibility(View.VISIBLE);
        switchData.setVisibility(View.GONE);
        data.setVisibility(View.VISIBLE);

        DataTextWatcher dataTextWatcher = new DataTextWatcher(data, changingText);
        data.addTextChangedListener(dataTextWatcher);

        Bundle bundle = getIntent().getExtras();
        idViagemAtiva = bundle.getInt("idViagemAtiva");
        idAlimentacaoEditar = bundle.getInt("idAlimentacaoEditar");
        nomeViagemAtiva = bundle.getString("nomeViagemAtiva");

        Alimentacao alimentacaoEditar = read.readAlimentacaoById(idAlimentacaoEditar);                                  // Aqui

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate dataLocalDate = LocalDate.parse(alimentacaoEditar.getData().toString(), inputFormatter);

            String dataFormatada = dataLocalDate.format(outputFormatter);
            data.setText(dataFormatada);
            valor.setText(alimentacaoEditar.getValor().toString());
            local.setText(alimentacaoEditar.getLocal());

            int posicaoSpinnerPagamento = adapter.getPosition(alimentacaoEditar.getMetodoPagamento());
            spinner.setSelection(posicaoSpinnerPagamento);

            int posicaoSpinnerTipoAlimentacao = adapter1.getPosition(alimentacaoEditar.getTipoAlimentacao());
            spnTipoAlimentacao.setSelection(posicaoSpinnerTipoAlimentacao);

        }

        descricao.setText(alimentacaoEditar.getDescricao());


        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAlimentacaoView.this);
                builder.setTitle("Confirmar Exclusão").setMessage("Gostaria realmente de excluir esta despesa? Este processo não pode ser desfeito");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showConfirmationDialog(EditAlimentacaoView.this);
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(

                findViewById(R.id.main), (v,insets)->

                {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;

                });

    }


    public void atualizarAlimentacao(View view) {

        String stringData = data.getText().toString();
        String stringDescricao = descricao.getText().toString();
        String stringValor = valor.getText().toString();
        stringValor = stringValor.replace(",",".");
        Double valorCorrigido = Double.parseDouble(stringValor);
        String stringCategoria = "Alimentacao";
        String stringPagamento = (String) spinner.getSelectedItem();
        String stringLocal = local.getText().toString();
        String stringTipoAlimentacao = spnTipoAlimentacao.getSelectedItem().toString();


        if (stringData.isEmpty() || stringDescricao.isEmpty() || stringPagamento.isEmpty() || stringLocal.isEmpty() || stringValor.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
           Alimentacao alimentacao = new Alimentacao();                                     // Aqui
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

                                alimentacao.setId(idAlimentacaoEditar);
                                alimentacao.setData(dataLocalDate); // Data
                                alimentacao.setDescricao(stringDescricao); // Descrição
                                alimentacao.setValor(valorCorrigido); // Valor
                                alimentacao.setCategoria(stringCategoria); // Categoria
                                alimentacao.setMetodoPagamento(stringPagamento); // Método de pagamento
                                alimentacao.setLocal(stringLocal); // Local
                                alimentacao.setTipoAlimentacao(stringTipoAlimentacao);
                                alimentacao.setId_viagem(idViagemAtiva);


                                long id = update.updateAlimentacao(alimentacao);

                                Toast.makeText(this, "Alimentacao atualizada com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

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

                    alimentacao.setId(idAlimentacaoEditar);
                    alimentacao.setData(dataLocalDate); // Data
                    alimentacao.setDescricao(stringDescricao); // Descrição
                    alimentacao.setValor(valorCorrigido); // Valor
                    alimentacao.setCategoria(stringCategoria); // Categoria
                    alimentacao.setMetodoPagamento(stringPagamento); // Método de pagamento
                    alimentacao.setLocal(stringLocal); // Local
                    alimentacao.setTipoAlimentacao(stringTipoAlimentacao);
                    alimentacao.setId_viagem(idViagemAtiva);


                    long id = update.updateAlimentacao(alimentacao);

                    Toast.makeText(this, "Alimentacao atualizada com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), AlimentacaoRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                    finish();

                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao atualizar abastecimento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showConfirmationDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmação")
                .setMessage("Você tem certeza de que deseja continuar?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete.deleteAlimentacao(context,idAlimentacaoEditar);
                        Intent intent = new Intent(getApplicationContext(), AlimentacaoRecyclerView.class);
                        intent.putExtra("idViagemAtiva", idViagemAtiva);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog confirmationDialog = builder.create();
        confirmationDialog.show();
    }




}