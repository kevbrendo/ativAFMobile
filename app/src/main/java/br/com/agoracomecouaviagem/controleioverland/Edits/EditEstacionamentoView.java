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
import br.com.agoracomecouaviagem.controleioverland.Entities.Estacionamento;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.EstacionamentoRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;

public class EditEstacionamentoView extends AppCompatActivity {
    private Switch switchData;
    private Spinner spinner;
    private EditText data;
    private EditText valor;
    private EditText local;

    private EditText descricao;
    private EditText editHoras;
    private Button btnSalvar;
    private Button btnAtualizar;
    private Button btnExcluir;
    private InsertDAO insert;
    private int idViagemAtiva;
    private String nomeViagemAtiva;
    private int idEstacionamentoEditar;
    private ReadDAO read;
    private UpdateDAO update;
    private DeleteDAO delete;



    private boolean changingText = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.insertestacionamento_main);

        insert = new InsertDAO(this);
        read = new ReadDAO(this);
        update = new UpdateDAO(this);
        delete = new DeleteDAO(this);



        // Iniciando Spinner e dando o Array de opções
        spinner = findViewById(R.id.spnPagamento);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.formas_pagamento, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        data = findViewById(R.id.editData);
        valor = findViewById(R.id.editValor);
        local = findViewById(R.id.editLocal);
        editHoras = findViewById(R.id.editHoras);
        spinner = findViewById(R.id.spnPagamento);
        descricao = findViewById(R.id.editDescricao);
        switchData = findViewById(R.id.switchHoje);

        btnSalvar = findViewById(R.id.btnSalvar);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnExcluir = findViewById(R.id.btnExcluir);

        data.setVisibility(View.VISIBLE);
        switchData.setVisibility(View.GONE);
        btnSalvar.setVisibility(View.GONE);
        btnAtualizar.setVisibility(View.VISIBLE);
        btnExcluir.setVisibility(View.VISIBLE);


        DataTextWatcher dataTextWatcher = new DataTextWatcher(data, changingText);
        data.addTextChangedListener(dataTextWatcher);

        Bundle bundle = getIntent().getExtras();
        idViagemAtiva = bundle.getInt("idViagemAtiva");
        idEstacionamentoEditar = bundle.getInt("idEstacionamentoEditar");
        nomeViagemAtiva = bundle.getString("nomeViagemAtiva");

        Estacionamento estacionamentoEditar = read.readEstacionamentoById(idEstacionamentoEditar);                                  // Aqui

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate dataLocalDate = LocalDate.parse(estacionamentoEditar.getData().toString(), inputFormatter);

            String dataFormatada = dataLocalDate.format(outputFormatter);
            data.setText(dataFormatada);
            valor.setText(estacionamentoEditar.getValor().toString());
            local.setText(estacionamentoEditar.getLocal());

            int posicaoSpinnerPagamento = adapter.getPosition(estacionamentoEditar.getMetodoPagamento());
            spinner.setSelection(posicaoSpinnerPagamento);
            descricao.setText(estacionamentoEditar.getDescricao());
            editHoras.setText(estacionamentoEditar.getQuantidadeHoras().toString());
        }

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditEstacionamentoView.this);
                builder.setTitle("Confirmar Exclusão").setMessage("Gostaria realmente de excluir esta despesa? Este processo não pode ser desfeito");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showConfirmationDialog(EditEstacionamentoView.this);
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


    public void atualizarEstacionamento(View view) {

        String stringData = data.getText().toString();
        String stringDescricao = descricao.getText().toString();
        String stringValor = valor.getText().toString();
        stringValor = stringValor.replace(",",".");
        Double valorCorrigido = Double.parseDouble(stringValor);
        String stringCategoria = "Estacionamento";
        String stringPagamento = (String) spinner.getSelectedItem();
        String stringLocal = local.getText().toString();
        String stringQuantidadeHoras = editHoras.getText().toString();



        if (stringData.isEmpty() || stringDescricao.isEmpty() || stringPagamento.isEmpty() || stringLocal.isEmpty() || stringValor.isEmpty() || stringQuantidadeHoras.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
           Estacionamento estacionamento = new Estacionamento();                                     // Aqui
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

                                estacionamento.setId(idEstacionamentoEditar);           // AQUI
                                estacionamento.setData(dataLocalDate); // Data
                                estacionamento.setDescricao(stringDescricao); // Descrição
                                estacionamento.setValor(valorCorrigido); // Valor
                                estacionamento.setCategoria(stringCategoria); // Categoria
                                estacionamento.setMetodoPagamento(stringPagamento); // Método de pagamento
                                estacionamento.setLocal(stringLocal); // Local
                                estacionamento.setQuantidadeHoras(Integer.parseInt(stringQuantidadeHoras));
                                estacionamento.setId_viagem(idViagemAtiva);


                                long id = update.updateEstacionamento(estacionamento);

                                Toast.makeText(this, "Estacionamento atualizada com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), EstacionamentoRecyclerView.class);
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

                    estacionamento.setId(idEstacionamentoEditar);           // AQUI
                    estacionamento.setData(dataLocalDate); // Data
                    estacionamento.setDescricao(stringDescricao); // Descrição
                    estacionamento.setValor(valorCorrigido); // Valor
                    estacionamento.setCategoria(stringCategoria); // Categoria
                    estacionamento.setMetodoPagamento(stringPagamento); // Método de pagamento
                    estacionamento.setLocal(stringLocal); // Local
                    estacionamento.setQuantidadeHoras(Integer.parseInt(stringQuantidadeHoras));
                    estacionamento.setId_viagem(idViagemAtiva);


                    long id = update.updateEstacionamento(estacionamento);

                    Toast.makeText(this, "Estacionamento atualizada com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), EstacionamentoRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                    finish();

                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao atualizar estacionamento: " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    private void showConfirmationDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmação")
                .setMessage("Você tem certeza de que deseja continuar?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete.deleteEstacionamento(context,idEstacionamentoEditar);
                        Intent intent = new Intent(getApplicationContext(), EstacionamentoRecyclerView.class);
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