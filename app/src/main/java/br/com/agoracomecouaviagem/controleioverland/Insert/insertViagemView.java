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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import br.com.agoracomecouaviagem.controleioverland.DAO.InsertDAO;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.ViewsMenu.MenuDeViagens;
import br.com.agoracomecouaviagem.controleioverland.Entities.Viagem;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;


@RequiresApi(api = Build.VERSION_CODES.O)
public class insertViagemView extends AppCompatActivity {

    Switch switchData;
    private EditText editDataViagem;
    private EditText editLocalViagem;
    private EditText editKilometragemInicial;
    private EditText editNomeViagem;
    private EditText editLocalDestino;
    private Button btnSalvar;
    private InsertDAO dao;



    Boolean changingText = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.insertviagem_main);


        editNomeViagem = findViewById(R.id.editNomeViagem);
        editDataViagem = findViewById(R.id.editDataViagem);
        editLocalViagem = findViewById(R.id.editLocalPartida);
        editLocalDestino = findViewById(R.id.editLocalDestino);
        editKilometragemInicial = findViewById(R.id.editKilometragemInicial);
        switchData = findViewById(R.id.switchData);
        switchData.setChecked(true);
        btnSalvar = findViewById(R.id.btnSalvar);


        dao = new InsertDAO(this);

        btnSalvar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inserirViagem();

                    }
                }
        );



        switchData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // Switch desmarcado, torna o EditText de inserção de data visível
                    editDataViagem.setVisibility(View.VISIBLE);
                    editDataViagem.requestFocus(); // Foca no EditText para que o teclado apareça automaticamente
                    switchData.setVisibility(View.INVISIBLE);
                } else {
                    // Switch marcado, oculta o EditText de inserção de data
                    editDataViagem.setVisibility(View.INVISIBLE);

                }
            }
        });

        DataTextWatcher dataTextWatcher = new DataTextWatcher(editDataViagem,changingText);
        editDataViagem.addTextChangedListener(dataTextWatcher);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void inserirViagem() {
        String stringNome = editNomeViagem.getText().toString();
        String stringLocal = editLocalViagem.getText().toString();
        String stringKilometragem = editKilometragemInicial.getText().toString();
        String stringLocalDestino = editLocalDestino.getText().toString();
        String stringData = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataCheck = LocalDate.now();
            if (!switchData.isChecked()) {
                stringData = editDataViagem.getText().toString();
               String dataConvert =  DataTextWatcher.converterData(stringData);
                LocalDate dataDigitada = LocalDate.parse(dataConvert);
                stringData = dataDigitada.toString();
            } else {
               LocalDate dataAtual = LocalDate.parse(dataCheck.toString(),dtf);
                stringData = dataAtual.toString();
            }
        }

        if (stringData.isEmpty()) {
            Toast.makeText(this, "Data Vazia", Toast.LENGTH_LONG).show();
            return ;
        }else if (stringLocal.isEmpty()){
            Toast.makeText(this, "Local Vazio", Toast.LENGTH_LONG).show();
            return;
        }else if(stringKilometragem.isEmpty()){
            Toast.makeText(this, "Km Vazia", Toast.LENGTH_LONG).show();
            return;
        }


        try {
            Viagem viagem = new Viagem();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataLocalDate = LocalDate.parse(stringData);
            LocalDate dataAtual = LocalDate.now();
            LocalDate dataFinal = LocalDate.of(2200, 1, 1);
            if (dataLocalDate.isAfter(dataAtual)) {
                new AlertDialog.Builder(this)
                        .setTitle("Confirmação")
                        .setMessage("Você está iniciando uma viagem em uma data futura. Deseja continuar?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            viagem.setNome(stringNome);
                            viagem.setDataDeInicio(dataLocalDate);
                            viagem.setLocalDePartida(stringLocal);
                            viagem.setKilometragemInicial(Double.parseDouble(stringKilometragem));
                            viagem.setDataDeTermino(dataFinal);
                            viagem.setDestinoFinal(stringLocalDestino);
                            viagem.setKilometragemParcial(viagem.getKilometragemInicial());
                            viagem.setKilometragemFinal(null);
                            viagem.setGastoParcial(0.0);
                            viagem.setGastoTotal(0.0);
                            viagem.setStatus(true);

                            long id = dao.addViagem(viagem);

                            Toast.makeText(getApplicationContext(), "Viagem inserida com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MenuDeViagens.class);
                            startActivity(intent);
                            finish();

                        })
                        .setNegativeButton("Não", (dialog, which) -> {
                            Toast.makeText(getApplicationContext(), "Operação cancelada", Toast.LENGTH_SHORT).show();
                        })
                        .create()
                        .show();
            } else {
                viagem.setNome(stringNome);
                viagem.setDataDeInicio(dataLocalDate);
                viagem.setLocalDePartida(stringLocal);
                viagem.setKilometragemInicial(Double.parseDouble(stringKilometragem));
                viagem.setDataDeTermino(dataFinal);
                viagem.setDestinoFinal(stringLocalDestino);
                viagem.setKilometragemParcial(0.0);
                viagem.setKilometragemFinal(null);
                viagem.setGastoParcial(0.0);
                viagem.setGastoTotal(0.0);
                viagem.setStatus(true);

                long id = dao.addViagem(viagem);

                Toast.makeText(getApplicationContext(), "Viagem inserida com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuDeViagens.class);
                startActivity(intent);
                finish();
            }
        } catch (DateTimeParseException e) {
            Toast.makeText(getApplicationContext(), "Erro ao adicionar viagem. Data inválida.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Erro ao adicionar viagem", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }






}