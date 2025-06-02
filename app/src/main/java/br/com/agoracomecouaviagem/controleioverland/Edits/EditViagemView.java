package br.com.agoracomecouaviagem.controleioverland.Edits;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.List;

import br.com.agoracomecouaviagem.controleioverland.DAO.DeleteDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.InsertDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.DAO.UpdateDAO;
import br.com.agoracomecouaviagem.controleioverland.Entities.Abastecimento;
import br.com.agoracomecouaviagem.controleioverland.Entities.Alimentacao;
import br.com.agoracomecouaviagem.controleioverland.Entities.Despesa;
import br.com.agoracomecouaviagem.controleioverland.Entities.Diario;
import br.com.agoracomecouaviagem.controleioverland.Entities.Estacionamento;
import br.com.agoracomecouaviagem.controleioverland.Entities.Hospedagem;
import br.com.agoracomecouaviagem.controleioverland.Entities.Manutencao;
import br.com.agoracomecouaviagem.controleioverland.Entities.Passeio;
import br.com.agoracomecouaviagem.controleioverland.Entities.Pedagio;
import br.com.agoracomecouaviagem.controleioverland.Entities.Viagem;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.Utils.DataTextWatcher;
import br.com.agoracomecouaviagem.controleioverland.Utils.GerarPDF;
import br.com.agoracomecouaviagem.controleioverland.Utils.Relatorio;
import br.com.agoracomecouaviagem.controleioverland.ViewsMenu.MenuDeViagens;
import br.com.agoracomecouaviagem.controleioverland.ViewsMenu.MenuEncerrarViagem;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EditViagemView extends AppCompatActivity {

    private EditText editDataViagem;
    private EditText editLocalPartida;
    private EditText editLocalDestino;
    private EditText editKilometragemAtual;
    private EditText editKilometragemInicial;
    private EditText editNomeViagem;
    private Button btnSalvar;
    private Button btnExcluir;
    private Button btnEncerrarViagem;
    private Button btnPDF;
    private InsertDAO insert;
    private ReadDAO read;
    private UpdateDAO update;
    private DeleteDAO delete;
    private int idViagemAtiva;
    private GerarPDF gerarPDF;
    private Boolean changingText = false;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.edit_viagem_main);

        editNomeViagem = findViewById(R.id.editNomeViagem);
        editDataViagem = findViewById(R.id.editDataViagem);
        editLocalPartida = findViewById(R.id.editLocalPartida);
        editLocalDestino = findViewById(R.id.editLocalDestino);
        editKilometragemAtual = findViewById(R.id.editarKilometragemAtual);
        editKilometragemInicial = findViewById(R.id.editarKilometragemInicial);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnExcluir = findViewById(R.id.btnExcluir);
        btnEncerrarViagem = findViewById(R.id.btnEncerrarViagem);


        read = new ReadDAO(this);
        insert = new InsertDAO(this);
        update = new UpdateDAO(this);
        delete = new DeleteDAO(this);
        gerarPDF = new GerarPDF(this);

        btnExcluir.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();

        idViagemAtiva = bundle.getInt("idViagemAtiva");

        Viagem viagemEdit = read.readViagemById(idViagemAtiva);



        if (viagemEdit != null && viagemEdit.getDataDeInicio() != null) {
            editNomeViagem.setText(viagemEdit.getNome());


            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            try {

                LocalDate dataLocalDate = LocalDate.parse(viagemEdit.getDataDeInicio().toString(), inputFormatter);

                String dataFormatada = dataLocalDate.format(outputFormatter);

                editDataViagem.setText(dataFormatada);
            } catch (DateTimeParseException e) {

                Log.e("Error", "Erro ao fazer parsing da data: " + e.getMessage());

            }

            editLocalPartida.setText(viagemEdit.getLocalDePartida());
            editLocalDestino.setText(viagemEdit.getDestinoFinal());
            editKilometragemInicial.setText(String.valueOf(viagemEdit.getKilometragemInicial()));

            btnSalvar.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditViagem(idViagemAtiva);
                            Intent intent = new Intent(getApplicationContext(), MenuDeViagens.class);
                            startActivity(intent);
                        }
                    }
            );

            btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditViagemView.this);
                    builder.setTitle("Confirmar Exclusão").setMessage("Gostaria realmente de excluir esta viagem? Este processo não pode ser desfeito");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showConfirmationDialog(EditViagemView.this);
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

            if(!viagemEdit.getStatus()){
                btnEncerrarViagem.setVisibility(View.GONE);
            }

            btnEncerrarViagem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), MenuEncerrarViagem.class);
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);
                }
            });

//            btnPDF.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Relatorio Relatorio = new Relatorio();
//                    Viagem viagem = viagemEscolhida();
//                    List<String> concate = new ArrayList<>();
//
//
//                    List<Abastecimento> lstAbastecimentoPDF = viagem.getAbastecimentos();
//                    List<Alimentacao> lstAlimentacaoPDF = viagem.getAlimentacoes();
//                    List<Despesa> lstDespesaPDF = viagem.getDespesas();
//                    List<Diario> lstDiarioPDF = viagem.getDiarios();
//                    List<Estacionamento> lstEstacionamentoPDF = viagem.getEstacionamentos();
//                    List<Hospedagem> lstHospedagemPDF = viagem.getHospedagens();
//                    List<Manutencao> lstManutencaoPDF = viagem.getManutencoes();
//                    List<Passeio> lstPasseioPDF = viagem.getPasseios();
//                    List<Pedagio> lstPedagioPDF = viagem.getPedagios();
//                    viagem.setGastoParcial(read.calcularGastoTotal(viagem.getId()));
//
//
//                    String cabecalho = Relatorio.criarCabecalho( viagem.getNome());
//                    concate.add(cabecalho);
//                    concate.add(viagem.getSumarioViagem(viagem.getStatus()));
//
//                    concate.add(new Relatorio().criarCabecalho("Abastecimento"));
//                    for(Abastecimento a : lstAbastecimentoPDF)
//                        concate.add(a.toString());
//
//                    concate.add(new Relatorio().criarCabecalho("Alimentação"));
//                    for(Alimentacao a : lstAlimentacaoPDF)
//                        concate.add(a.toString());
//
//                    concate.add(new Relatorio().criarCabecalho("Despesas"));
//                    for(Despesa d : lstDespesaPDF)
//                        concate.add(d.toString());
//
//                    concate.add(new Relatorio().criarCabecalho("Estacionamento"));
//                    for(Estacionamento e : lstEstacionamentoPDF)
//                        concate.add(e.toString());
//
//                    concate.add(new Relatorio().criarCabecalho("Hospedagem"));
//                    for(Hospedagem h : lstHospedagemPDF)
//                        concate.add(h.toString());
//
//                    concate.add(new Relatorio().criarCabecalho("Manutenção"));
//                    for(Manutencao  m : lstManutencaoPDF)
//                        concate.add(m.toString());
//
//                    concate.add(new Relatorio().criarCabecalho("Passeio"));
//                    for(Passeio  p : lstPasseioPDF)
//                        concate.add(p.toString());
//
//                    concate.add(new Relatorio().criarCabecalho("Pedagio"));
//                    for(Pedagio  p : lstPedagioPDF)
//                        concate.add(p.toString());
//
//
//                    gerarPDF.generatePDF(concate);
//
//                }
//            });



            DataTextWatcher dataTextWatcher = new DataTextWatcher(editDataViagem, changingText);
            editDataViagem.addTextChangedListener(dataTextWatcher);


        } else {
            Toast.makeText(getApplicationContext(), "Viagem não encontrada ou data de início não definida", Toast.LENGTH_LONG).show();
        }
    }

    public void EditViagem(int idViagemAtiva) {
        try {
            Viagem viagemEdit = (Viagem) read.readViagemById(idViagemAtiva);
            if (viagemEdit != null && viagemEdit.getDataDeInicio() != null) {

                String stringNome = editNomeViagem.getText().toString();
                String stringLocalPartida = editLocalPartida.getText().toString();
                String stringLocalDestino = editLocalDestino.getText().toString();
                Double stringKilometragemInicial = viagemEdit.getKilometragemInicial();
                String stringKilometragem = editKilometragemAtual.getText().toString();

                Double k1 = stringKilometragemInicial;
                Double k2 = Double.parseDouble(stringKilometragem);

                Double kilometragemAtual = (Double) k2 - k1;
                String stringDataPartida = editDataViagem.getText().toString();
                
                // Parse da data do formato 'dd/MM/yyyy' para 'yyyy-MM-dd'
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataLocalDate = LocalDate.parse(stringDataPartida, outputFormatter);

                // Formatar a data para 'yyyy-MM-dd' novamente antes de realizar o update
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String stringDataFormatada = dataLocalDate.format(dtf);
                LocalDate dataLocalDateFormatted = LocalDate.parse(stringDataFormatada, dtf);

                LocalDate dataAtual = LocalDate.now();

                if (dataLocalDate.isAfter(dataAtual)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Confirmação")
                            .setMessage("Você está iniciando uma viagem em uma data futura. Deseja continuar?")
                            .setPositiveButton("Sim", (dialog, which) -> {
                                Log.e("Data atual reconhecida", "positivo");
                                viagemEdit.setNome(stringNome);
                                viagemEdit.setDataDeInicio(dataLocalDateFormatted);
                                viagemEdit.setLocalDePartida(stringLocalPartida);
                                viagemEdit.setKilometragemInicial(stringKilometragemInicial);
                                viagemEdit.setDataDeTermino(viagemEdit.getDataDeTermino());
                                viagemEdit.setDestinoFinal(stringLocalDestino);
                                viagemEdit.setKilometragemParcial(Double.parseDouble(kilometragemAtual.toString()));
                                viagemEdit.setKilometragemFinal(null);
                                viagemEdit.setGastoParcial(viagemEdit.getGastoParcial());
                                viagemEdit.setGastoTotal(viagemEdit.getGastoTotal());
                                viagemEdit.setStatus(viagemEdit.getStatus());
                                Log.e("Instanciaçaõ feita", "positivo");
                                long id = update.updateViagem(viagemEdit);

                                Toast.makeText(getApplicationContext(), "Viagem editada com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(EditViagemView.this, MenuDeViagens.class);
                                startActivity(intent);
                                finish();

                            })
                            .setNegativeButton("Não", (dialog, which) -> {
                                Toast.makeText(getApplicationContext(), "Operação cancelada", Toast.LENGTH_SHORT).show();
                            })
                            .create()
                            .show();
                } else {
                    viagemEdit.setNome(stringNome);
                    viagemEdit.setDataDeInicio(dataLocalDateFormatted);
                    viagemEdit.setLocalDePartida(stringLocalPartida);
                    viagemEdit.setKilometragemInicial(stringKilometragemInicial);
                    viagemEdit.setDataDeTermino(viagemEdit.getDataDeTermino());
                    viagemEdit.setDestinoFinal(stringLocalDestino);
                    viagemEdit.setKilometragemParcial(Double.parseDouble(kilometragemAtual.toString()));
                    viagemEdit.setKilometragemFinal(null);
                    viagemEdit.setGastoParcial(viagemEdit.getGastoParcial());
                    viagemEdit.setGastoTotal(viagemEdit.getGastoTotal());
                    viagemEdit.setStatus(viagemEdit.getStatus());

                    long id = update.updateViagem(viagemEdit);

                    Toast.makeText(getApplicationContext(),"Km Parcial: " + viagemEdit.getKilometragemParcial().toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Viagem editada com sucesso com o ID: " + id, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditViagemView.this, MenuDeViagens.class);
                    startActivity(intent);
                    finish();

                }
            } else {
                Toast.makeText(getApplicationContext(), "Viagem não encontrada ou data de início não definida", Toast.LENGTH_LONG).show();
            }
        } catch (DateTimeParseException e) {
            Toast.makeText(getApplicationContext(), "Viagem não foi atualizada, Data inválida.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Viagem não foi atualizada", Toast.LENGTH_LONG).show();
            Log.e("Erro: ", e.getMessage());
        }
    }


    private void showConfirmationDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmação")
                .setMessage("Você tem certeza de que deseja continuar?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete.deleteViagem(context,idViagemAtiva);
                            Toast.makeText(EditViagemView.this, "Viagem Deletada", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditViagemView.this, MenuDeViagens.class);
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


    public Viagem viagemEscolhida(){

        Viagem viagem = read.readViagemById(idViagemAtiva);

        viagem.setAbastecimentos(read.readAbastecimentoByIdViagem(viagem.getId()));
        viagem.setAlimentacoes(read.readAlimentacaoByIdViagem(viagem.getId()));
        viagem.setDespesas(read.readDespesaByIdViagem(viagem.getId()));
        viagem.setDiarios(read.readDiarioByIdViagem(viagem.getId()));
        viagem.setEstacionamentos(read.readEstacionamentoByIdViagem(viagem.getId()));
        viagem.setHospedagens(read.readHospedagemByIdViagem(viagem.getId()));
        viagem.setManutencoes(read.readManutencaoByIdViagem(viagem.getId()));
        viagem.setPasseios(read.readPasseioByIdViagem(viagem.getId()));
        viagem.setPedagios(read.readPedagioByIdViagem(viagem.getId()));

        return viagem;
    }


}

