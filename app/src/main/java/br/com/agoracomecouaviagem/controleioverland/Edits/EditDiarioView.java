package br.com.agoracomecouaviagem.controleioverland.Edits;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import br.com.agoracomecouaviagem.controleioverland.DAO.DeleteDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.InsertDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.UpdateDAO;
import br.com.agoracomecouaviagem.controleioverland.Entities.Diario;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.DiarioRecyclerView;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;

public class EditDiarioView extends AppCompatActivity {

  private Switch switchData, switchFavorito;
  private Boolean favorito = false;
    private EditText data;
    private EditText local;
    private EditText comentario;
    private Button btnSalvar;
    private Button btnAtualizar;
    private Button btnExcluir;
    private InsertDAO insert;
    private int idViagemAtiva;
    private String nomeViagemAtiva;
    private int idDiarioEditar;
    private ReadDAO read;
    private UpdateDAO update;
    private DeleteDAO delete;

    private boolean changingText = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.insertdiario_main);

        insert = new InsertDAO(this);
        read = new ReadDAO(this);
        update = new UpdateDAO(this);
        delete = new DeleteDAO(this);


        data = findViewById(R.id.editData);
        local = findViewById(R.id.editLocal);
        comentario = findViewById(R.id.editDescricao);


        btnSalvar = findViewById(R.id.btnSalvar);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnExcluir = findViewById(R.id.btnExcluir);
        switchData = findViewById(R.id.switchHoje);
        switchFavorito = findViewById(R.id.switchFavorito);

        data.setVisibility(View.VISIBLE);
        switchData.setVisibility(View.GONE);
        btnSalvar.setVisibility(View.GONE);
        btnAtualizar.setVisibility(View.VISIBLE);
        btnExcluir.setVisibility(View.VISIBLE);

        DataTextWatcher dataTextWatcher = new DataTextWatcher(data, changingText);
        data.addTextChangedListener(dataTextWatcher);

        Bundle bundle = getIntent().getExtras();
        idViagemAtiva = bundle.getInt("idViagemAtiva");
        idDiarioEditar = bundle.getInt("idDiarioEditar");
        nomeViagemAtiva = bundle.getString("nomeViagemAtiva");



       Diario diarioEditar = read.readDiarioById(idDiarioEditar);    // Aqui

        if(diarioEditar.getFavorito()){
            switchFavorito.setChecked(true);
        }else {
            switchFavorito.setChecked(false);
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate dataLocalDate = LocalDate.parse(diarioEditar.getData().toString(), inputFormatter);

            String dataFormatada = dataLocalDate.format(outputFormatter);
            data.setText(dataFormatada);
            local.setText(diarioEditar.getLocal());
            comentario.setText(diarioEditar.getComentario());

        }


        switchFavorito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                   favorito = false;
                }else{
                    favorito = true;
                }
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDiarioView.this);
                builder.setTitle("Confirmar Exclusão").setMessage("Gostaria realmente de excluir este registro? Este processo não pode ser desfeito");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showConfirmationDialog(EditDiarioView.this);
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


    public void atualizarDiario(View view) {

        String stringData = data.getText().toString();
        String stringComentario = comentario.getText().toString();
        String stringLocal = local.getText().toString();



        if (stringData.isEmpty() || stringComentario.isEmpty() || stringLocal.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
           Diario diario = new Diario();                                     // Aqui
            DateTimeFormatter dtf = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataLocalDate = LocalDate.parse(DataTextWatcher.converterData(stringData));
                LocalDate dataAtual = LocalDate.now();

                if (dataLocalDate.isAfter(dataAtual)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Confirmação")
                            .setMessage("Você está adicionando um diario em uma data futura. Deseja continuar?")
                            .setPositiveButton("Sim", (dialog, which) -> {

                                diario.setId(idDiarioEditar);           // AQUI
                                diario.setData(dataLocalDate); // Data
                                diario.setLocal(stringLocal); // Local
                                diario.setComentario(stringComentario);
                                diario.setId_viagem(idViagemAtiva);
                                diario.setFavorito(favorito);


                                long id = update.updateDiario(diario);

                                Toast.makeText(this, "Diario atualizado com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), DiarioRecyclerView.class);
                                intent.putExtra("idViagemAtiva", idViagemAtiva);
                                startActivity(intent);
                                finish();
                                finish();

                            })
                            .setNegativeButton("Não", (dialog, which) -> {
                                // O usuário cancelou a operação
                                Toast.makeText(this, "Operação cancelada", Toast.LENGTH_SHORT).show();
                            })
                            .create()
                            .show();
                } else {

                    diario.setId(idDiarioEditar);           // AQUI
                    diario.setData(dataLocalDate); // Data
                    diario.setLocal(stringLocal); // Local
                    diario.setComentario(stringComentario);
                    diario.setId_viagem(idViagemAtiva);
                    diario.setFavorito(favorito);

                    long id = update.updateDiario(diario);

                    Toast.makeText(this, "Diario atualizado com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), DiarioRecyclerView.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                    finish();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao atualizar diario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void showConfirmationDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmação")
                .setMessage("Você tem certeza de que deseja continuar?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete.deleteDiario(context,idDiarioEditar);
                        Intent intent = new Intent(getApplicationContext(), DiarioRecyclerView.class);
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