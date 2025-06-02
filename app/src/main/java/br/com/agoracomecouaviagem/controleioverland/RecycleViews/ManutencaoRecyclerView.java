package br.com.agoracomecouaviagem.controleioverland.RecycleViews;

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

import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.Edits.EditManutencaoView;
import br.com.agoracomecouaviagem.controleioverland.Insert.InsertManutencaoView;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterAbastecimento;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterManutencao;
import br.com.agoracomecouaviagem.controleioverland.Entities.Manutencao;
import br.com.agoracomecouaviagem.controleioverland.Utils.ClickListener;
import br.com.agoracomecouaviagem.controleioverland.ViewsMenu.MenuDadosIcones;

public class ManutencaoRecyclerView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView total;
    private List<Manutencao> lstManutencao = new ArrayList<>();

    private ReadDAO read;

    private Integer idViagemAtiva;
    private FloatingActionButton btnAdd;
    private Button btnVoltar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.read_manutencao_recycle);

        read = new ReadDAO(this);
        total = findViewById(R.id.editTotal);
        Bundle bundle = getIntent().getExtras();
        idViagemAtiva = bundle.getInt("idViagemAtiva");

        btnAdd = findViewById(R.id.addButton);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManutencaoRecyclerView.this, InsertManutencaoView.class);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                startActivity(intent);
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManutencaoRecyclerView.this, MenuDadosIcones.class);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                startActivity(intent);
                finish();
            }
        });

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.rcrView);

        lstManutencao = read.readManutencaoByIdViagem(idViagemAtiva);

        // Configurar adapter
        AdapterManutencao adapter = new AdapterManutencao(lstManutencao);
        recyclerView.setAdapter(adapter);

        Double valorTotal = 0.0;
        for(Manutencao a : lstManutencao){
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
                Manutencao manutencaoSelecionado =   lstManutencao.get(position);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Intent intent = new Intent(getApplicationContext(), EditManutencaoView.class);
                    intent.putExtra("idManutencaoEditar", manutencaoSelecionado.getId());            /// AQUI EDITAR
                    intent.putExtra("idViagemAtiva", idViagemAtiva);
                    startActivity(intent);

                }
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Manutencao manutencao = lstManutencao.get(position);

                StringBuilder message = new StringBuilder();
                message.append("Data: ").append(AdapterAbastecimento.formatDate(manutencao.getData())).append("\n");
                message.append("Valor R$: ").append(String.format("%.2f", manutencao.getValor())).append("\n");
                message.append("Local: ").append(manutencao.getLocal()).append("\n");
                message.append("Metodo de Pagamento: ").append(manutencao.getMetodoPagamento()).append("\n");
                message.append("Tipo de manutenção: ").append(manutencao.getTipoDeManutencao());
                message.append("Descrição: ").append(manutencao.getDescricao()).append("\n");



                AlertDialog.Builder builder = new AlertDialog.Builder(ManutencaoRecyclerView.this);
                builder.setTitle("Detalhes de Manutenção");
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

                Intent intent = new Intent(ManutencaoRecyclerView.this, MenuDadosIcones.class);
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
