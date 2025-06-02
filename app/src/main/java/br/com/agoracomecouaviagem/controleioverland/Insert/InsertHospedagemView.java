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
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.Entities.Hospedagem;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.HospedagemRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;

public class InsertHospedagemView extends AppCompatActivity {

    private Spinner spnMetodoPagamento;
    private Switch switchData;
    private EditText data;
    private EditText descricao;
    private EditText valor;
    private EditText local;
    private Spinner spntipoHospedagem;
    private EditText quantidadeDiarias;
    private EditText nomeHospedagem;
    private InsertDAO dao;
    private boolean changingText = false;
    private int idViagemAtiva;
    private String nomeViagemAtiva;
    private Button btnExcluir;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.inserthospedagem_main);

        dao = new InsertDAO(this);

        spnMetodoPagamento = findViewById(R.id.spnPagamento);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.formas_pagamento, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMetodoPagamento.setAdapter(adapter);

        spntipoHospedagem = findViewById(R.id.spnHospedagem);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.tipo_hospedagem, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spntipoHospedagem.setAdapter(adapter1);

        data = findViewById(R.id.editData);
        descricao = findViewById(R.id.editDescricao);
        valor = findViewById(R.id.editValor);
        local = findViewById(R.id.editLocal);
        quantidadeDiarias = findViewById(R.id.editQuantidadeDiarias);
        nomeHospedagem = findViewById(R.id.editNomeHospedagem);
        switchData = findViewById(R.id.switchHoje);
        Bundle bundle = getIntent().getExtras();
        nomeViagemAtiva = bundle.getString("nomeViagemAtiva");
        idViagemAtiva = bundle.getInt("idViagemAtiva");
        btnExcluir = findViewById(R.id.btnExcluir);

        btnExcluir.setVisibility(View.GONE);
        DataTextWatcher dataTextWatcher = new DataTextWatcher(data,changingText);
        data.addTextChangedListener(dataTextWatcher);

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



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void inserirHospedagem(View view){

        String stringData = data.getText().toString();
        String stringDescricao = descricao.getText().toString();

        String stringValor = valor.getText().toString();
        stringValor = stringValor.replace(",",".");
        Double valorCorrigido = Double.parseDouble(stringValor);

        String stringLocal = local.getText().toString();
        String stringCategoria = "Hospedagem";
        String stringPagamento = (String) spnMetodoPagamento.getSelectedItem().toString();
        String stringTipoHospedagem = (String) spntipoHospedagem.getSelectedItem().toString();
        String stringNomeHospedagem = nomeHospedagem.getText().toString();
        String stringQuantidadeDiarias = quantidadeDiarias.getText().toString();


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


        if (stringData.isEmpty() ||stringPagamento.isEmpty() || stringLocal.isEmpty() || stringValor.isEmpty() || stringQuantidadeDiarias.isEmpty()
            || stringNomeHospedagem.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Hospedagem hospedagem = new Hospedagem();
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
                                hospedagem.setData(dataLocalDate); // Data
                                hospedagem.setDescricao(stringDescricao); // Descrição
                                hospedagem.setValor(valorCorrigido); // Valor
                                hospedagem.setCategoria(stringCategoria); // Categoria
                                hospedagem.setMetodoPagamento(stringPagamento); // Método de pagamento
                                hospedagem.setLocal(stringLocal); // Local
                                hospedagem.setTipoHospedagem(stringTipoHospedagem);
                                hospedagem.setNomeHospedagem(stringNomeHospedagem);
                                hospedagem.setQuantidadeDeDiarias(Integer.parseInt(stringQuantidadeDiarias));
                                hospedagem.setId_viagem(idViagemAtiva);


                                long id = dao.addHospedagem(hospedagem);

                                Toast.makeText(this, "Hospedagem inserida com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), HospedagemRecyclerView.class);
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

                    hospedagem.setData(dataLocalDate); // Data
                    hospedagem.setDescricao(stringDescricao); // Descrição
                    hospedagem.setValor(valorCorrigido); // Valor
                    hospedagem.setCategoria(stringCategoria); // Categoria
                    hospedagem.setMetodoPagamento(stringPagamento); // Método de pagamento
                    hospedagem.setLocal(stringLocal); // Local
                    hospedagem.setTipoHospedagem(stringTipoHospedagem);
                    hospedagem.setNomeHospedagem(stringNomeHospedagem);
                    hospedagem.setQuantidadeDeDiarias(Integer.parseInt(stringQuantidadeDiarias));
                    hospedagem.setId_viagem(idViagemAtiva);
                    long id = dao.addHospedagem(hospedagem);

                    Toast.makeText(this, "Hospedagem inserida com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), HospedagemRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                    finish();

                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao inserir hospedagem: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}