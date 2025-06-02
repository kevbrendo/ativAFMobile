package br.com.agoracomecouaviagem.controleioverland.ViewsMenu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.UpdateDAO;
import br.com.agoracomecouaviagem.controleioverland.Entities.Viagem;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;

public class MenuEncerrarViagem extends AppCompatActivity {

    private TextView textMaisMil;
    private TextView textMenosMil;
    private TextView textMaisDoisMil;
    private TextView textMaisCincoMil;
    private TextView textMaisDezMil;
    private TextView textMaisVinteMil;
    private TextView textEncerrar;
    private Button btnEncerrar;
    private Switch switchData;
    private EditText editData;
    private boolean changingText = false;
    private int idViagemAtiva;
    private Viagem viagemAtiva;
    private ReadDAO read;
    private UpdateDAO update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_encerrar_viagem);

        textMaisMil = findViewById(R.id.textMaisMil);
        textMenosMil = findViewById(R.id.textMenosMil);
        textMaisDoisMil = findViewById(R.id.textMaisDoisMil);
        textMaisCincoMil = findViewById(R.id.textMaisCincoMil);
        textMaisDezMil = findViewById(R.id.textMaisDezMil);
        textMaisVinteMil = findViewById(R.id.textMaisVinteMil);
        textEncerrar = findViewById(R.id.textEncerramentoViagem);

        // Definir todos os TextViews como invisíveis no início
        textMaisMil.setVisibility(View.INVISIBLE);
        textMenosMil.setVisibility(View.INVISIBLE);
        textMaisDoisMil.setVisibility(View.INVISIBLE);
        textMaisCincoMil.setVisibility(View.INVISIBLE);
        textMaisDezMil.setVisibility(View.INVISIBLE);
        textMaisVinteMil.setVisibility(View.INVISIBLE);

        btnEncerrar = findViewById(R.id.btnEncerrar);
        switchData = findViewById(R.id.switchData);
        editData = findViewById(R.id.editDataFinal);

        if (editData == null) {
            Log.e("MenuEncerrarViagem", "EditText editData é nulo");
            return;
        }

        DataTextWatcher dataTextWatcher = new DataTextWatcher(editData, changingText);
        editData.addTextChangedListener(dataTextWatcher);

        read = new ReadDAO(this);
        update = new UpdateDAO(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idViagemAtiva = bundle.getInt("idViagemAtiva");
        } else {
            Log.e("MenuEncerrarViagem", "Bundle é nulo");
            return;
        }

        viagemAtiva = read.readViagemById(idViagemAtiva);
        if (viagemAtiva == null) {
            Log.e("MenuEncerrarViagem", "viagemAtiva é nulo");
            return;
        }

        textEncerrar.setText("Encerrar viagem: " + viagemAtiva.getNome().toString());

        // Exibição de textos baseados na quilometragem
        Double kmParcial = viagemAtiva.getKilometragemParcial();
        Log.d("MenuEncerrarViagem", "Kilometragem parcial: " + kmParcial);

        if (kmParcial < 1000) {
            textMenosMil.setVisibility(View.VISIBLE);
            Log.d("MenuEncerrarViagem", "Menos de 1000 km");
        } else if (kmParcial >= 1000 && kmParcial < 2000) {
            textMaisMil.setVisibility(View.VISIBLE);
            Log.d("MenuEncerrarViagem", "Entre 1000 e 2000 km");
        } else if (kmParcial >= 2000 && kmParcial < 5000) {
            textMaisDoisMil.setVisibility(View.VISIBLE);
            Log.d("MenuEncerrarViagem", "Entre 2000 e 5000 km");
        } else if (kmParcial >= 5000 && kmParcial < 10000) {
            textMaisCincoMil.setVisibility(View.VISIBLE);
            Log.d("MenuEncerrarViagem", "Entre 5000 e 10000 km");
        } else if (kmParcial >= 10000 && kmParcial < 20000) {
            textMaisDezMil.setVisibility(View.VISIBLE);
            Log.d("MenuEncerrarViagem", "Entre 10000 e 20000 km");
        } else if (kmParcial >= 20000) {
            textMaisVinteMil.setVisibility(View.VISIBLE);
            Log.d("MenuEncerrarViagem", "Mais de 20000 km");
        }

        switchData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    editData.setVisibility(View.VISIBLE);
                    editData.requestFocus();
                    switchData.setVisibility(View.INVISIBLE);
                } else {
                    editData.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnEncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MenuEncerrarViagem.this)
                        .setTitle("Confirmação de encerramento.")
                        .setMessage("Você tem certeza que gostaria de encerrar esta viagem?")
                        .setPositiveButton("SIM", (dialog, which) -> {
                            if (!switchData.isChecked()) {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    viagemAtiva.setDataDeTermino(LocalDate.parse(dataTextWatcher.converterData(editData.getText().toString())));
                                }
                            } else {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    LocalDate dataFinal = LocalDate.now();
                                    viagemAtiva.setDataDeTermino(dataFinal);
                                }
                            }
                            viagemAtiva.setStatus(false);
                            update.updateViagem(viagemAtiva);
                        })
                        .setNegativeButton("NÃO", null)
                        .show();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
