package br.com.agoracomecouaviagem.controleioverland.Insert;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import br.com.agoracomecouaviagem.controleioverland.Entities.Diario;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.DiarioRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;

public class InsertDiarioView extends AppCompatActivity {

    private Switch switchData, switchFavorito;
    private EditText data;
    private EditText local;
    private EditText comentario;
    private InsertDAO dao;
    private boolean changingText;
    private int idViagemAtiva;
    private String nomeViagemAtiva;
    private Button btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.insertdiario_main);

        dao = new InsertDAO(this);

        data = findViewById(R.id.editData);
        local = findViewById(R.id.editLocal);
        comentario = findViewById(R.id.editDescricao);
        btnExcluir =findViewById(R.id.btnExcluir);
        switchData = findViewById(R.id.switchHoje);
        switchFavorito = findViewById(R.id.switchFavorito);
        btnExcluir.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        nomeViagemAtiva = bundle.getString("nomeViagemAtiva");
        idViagemAtiva = bundle.getInt("idViagemAtiva");

        DataTextWatcher dataTextWatcher = new DataTextWatcher(data, changingText);
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



    public void inserirDiario(View view){

        String stringData = data.getText().toString();
        String stringLocal = local.getText().toString();
        String stringComentario = comentario.getText().toString();
        Boolean favorito = switchFavorito.isChecked();

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


        if(stringData.isEmpty() || stringLocal.isEmpty() || stringComentario.isEmpty()){
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
        }

        try{
            Diario diario = new Diario();
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

                                diario.setData(dataLocalDate); // Data
                                diario.setComentario(stringComentario);
                                diario.setLocal(stringLocal); // Local
                                diario.setId_viagem(idViagemAtiva);
                                diario.setFavorito(favorito);

                                long id = dao.addDiario(diario);

                                Toast.makeText(this, "Diario inserido com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), DiarioRecyclerView.class);
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

                    diario.setData(dataLocalDate); // Data
                    diario.setComentario(stringComentario);
                    diario.setLocal(stringLocal); // Local
                    diario.setId_viagem(idViagemAtiva);
                    diario.setFavorito(favorito);

                    long id = dao.addDiario(diario);

                    Toast.makeText(this, "Diario inserido com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), DiarioRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                    finish();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao inserir diario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


}