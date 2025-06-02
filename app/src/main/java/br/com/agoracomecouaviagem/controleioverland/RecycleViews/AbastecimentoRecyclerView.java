package br.com.agoracomecouaviagem.controleioverland.RecycleViews;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.agoracomecouaviagem.controleioverland.Edits.EditAbastecimentoView;
import br.com.agoracomecouaviagem.controleioverland.Insert.InsertAbastecimentoView;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterAbastecimento;
import br.com.agoracomecouaviagem.controleioverland.Entities.Abastecimento;
import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.Utils.ClickListener;
import br.com.agoracomecouaviagem.controleioverland.ViewsMenu.MenuDadosIcones;

public class AbastecimentoRecyclerView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView total;
    private List<Abastecimento> lstAbastecimento = new ArrayList<>();
    private ReadDAO read ;
    private Integer idViagemAtiva;
    private FloatingActionButton btnAdd;
    private Button btnVoltar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.read_abastecimento_recycle);

        read = new ReadDAO(this);
        total = findViewById(R.id.editTotal);
        Bundle bundle = getIntent().getExtras();
        idViagemAtiva = bundle.getInt("idViagemAtiva");

        btnAdd = findViewById(R.id.addButton);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbastecimentoRecyclerView.this, InsertAbastecimentoView.class);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                startActivity(intent);
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbastecimentoRecyclerView.this, MenuDadosIcones.class);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                startActivity(intent);
                finish();
            }
        });



        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.rcrView);

        lstAbastecimento = read.readAbastecimentoByIdViagem(idViagemAtiva);

        // Configurar adapter
        AdapterAbastecimento adapter = new AdapterAbastecimento(lstAbastecimento);
        recyclerView.setAdapter(adapter);

        // Exibir o total de abastecimento
        Double valorTotal = 0.0;
        for(Abastecimento a : lstAbastecimento){
            valorTotal += a.getValor();
        }

        total.setText(String.format("%.2f", valorTotal));



        // Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(new ClickListener(getApplicationContext(),recyclerView,new ClickListener.OnItemClickListener() {
                      @Override
                      public void onItemClick(View view, int position) {
                          Abastecimento abastecimentoSelecionado =   lstAbastecimento.get(position);

                          if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                              Intent intent = new Intent(getApplicationContext(), EditAbastecimentoView.class);
                              intent.putExtra("idAbastecimentoEditar", abastecimentoSelecionado.getId());
                              intent.putExtra("idViagemAtiva", idViagemAtiva);
                              startActivity(intent);

                          }
                      }

                      @Override
                      public void onLongItemClick(View view, int position) {
                          Abastecimento abastecimento = lstAbastecimento.get(position);

                          // Construa a mensagem para exibir na caixa de diálogo
                          StringBuilder message = new StringBuilder();
                          message.append("Data: ").append(AdapterAbastecimento.formatDate(abastecimento.getData())).append("\n");
                          message.append("Valor R$: ").append(String.format("%.2f", abastecimento.getValor())).append("\n");
                          message.append("Local: ").append(abastecimento.getLocal()).append("\n");
                          message.append("Metodo de Pagamento: ").append(abastecimento.getMetodoPagamento()).append("\n");
                          message.append("Quantidade de Combustivel: ").append(String.format("%.2f",abastecimento.getQuantidadeCombustivel())).append(" Litros \n");
                          message.append("Valor por Litro R$: ").append(String.format("%.2f", abastecimento.getValorPorLitro())).append("\n");
                          message.append("Descrição: ").append(abastecimento.getDescricao()).append("\n");

                          // Adicione outras informações do item conforme necessário

                          // Mostrar uma caixa de diálogo com as informações do item
                          AlertDialog.Builder builder = new AlertDialog.Builder(AbastecimentoRecyclerView.this);
                          builder.setTitle("Detalhes do Abastecimento");
                          builder.setMessage(message.toString());
                          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.dismiss();
                              }
                          });
                          builder.show();

                      }

                      @Override
                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                      }
                  }));

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                Intent intent = new Intent(AbastecimentoRecyclerView.this, MenuDadosIcones.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                startActivity(intent);
                finish();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



}

