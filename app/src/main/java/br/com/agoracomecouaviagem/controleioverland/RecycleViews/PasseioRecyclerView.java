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

import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.Edits.EditPasseioView;
import br.com.agoracomecouaviagem.controleioverland.Insert.InsertPasseioView;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterAbastecimento;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterPasseio;
import br.com.agoracomecouaviagem.controleioverland.Entities.Passeio;
import br.com.agoracomecouaviagem.controleioverland.Utils.ClickListener;
import br.com.agoracomecouaviagem.controleioverland.ViewsMenu.MenuDadosIcones;

public class PasseioRecyclerView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView total;
    private List<Passeio> lstPasseio = new ArrayList<>();
    private ReadDAO read ;
    private Integer idViagemAtiva;
    private FloatingActionButton btnAdd;
    private Button btnVoltar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.read_passeio_recycle);

        read = new ReadDAO(this);
        total = findViewById(R.id.editTotal);
        Bundle bundle = getIntent().getExtras();
        idViagemAtiva = bundle.getInt("idViagemAtiva");

        btnAdd = findViewById(R.id.addButton);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasseioRecyclerView.this, InsertPasseioView.class);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                startActivity(intent);
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasseioRecyclerView.this, MenuDadosIcones.class);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                startActivity(intent);
                finish();
            }
        });


        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.rcrView);

        lstPasseio = read.readPasseioByIdViagem(idViagemAtiva);

        // Configurar adapter
        AdapterPasseio adapter = new AdapterPasseio(lstPasseio);
        recyclerView.setAdapter(adapter);

        //Total gasto
        Double valorTotal = 0.0;
        for(Passeio a : lstPasseio){
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
                          Passeio passeioSelecionado =   lstPasseio.get(position);

                          if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                              Intent intent = new Intent(getApplicationContext(), EditPasseioView.class);
                              intent.putExtra("idPasseioEditar", passeioSelecionado.getId());            /// AQUI EDITAR
                              intent.putExtra("idViagemAtiva", idViagemAtiva);
                              startActivity(intent);
                          }
                      }

                      @Override
                      public void onLongItemClick(View view, int position) {
                          Passeio passeio = lstPasseio.get(position);

                          // Construa a mensagem para exibir na caixa de diálogo

                          StringBuilder message = new StringBuilder();
                          message.append("Data: ").append(AdapterAbastecimento.formatDate(passeio.getData())).append("\n");
                          message.append("Valor R$: ").append(String.format("%.2f", passeio.getValor())).append("\n");
                          message.append("Local: ").append(passeio.getLocal()).append("\n");
                          message.append("Metodo de Pagamento: ").append(passeio.getMetodoPagamento()).append("\n");
                          message.append("Nome do passeio: ").append(passeio.getNomePasseio()).append("\n");
                          message.append("Descrição: ").append(passeio.getDescricao()).append("\n");

                          // Adicione outras informações do item conforme necessário

                          // Mostrar uma caixa de diálogo com as informações do item
                          AlertDialog.Builder builder = new AlertDialog.Builder(PasseioRecyclerView.this);
                          builder.setTitle("Detalhes do Passeio");
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

                Intent intent = new Intent(PasseioRecyclerView.this, MenuDadosIcones.class);
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

