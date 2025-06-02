package br.com.agoracomecouaviagem.controleioverland.RecycleViews;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.Edits.EditDiarioView;
import br.com.agoracomecouaviagem.controleioverland.Insert.InsertDiarioView;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterAbastecimento;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterDiario;
import br.com.agoracomecouaviagem.controleioverland.Entities.Diario;
import br.com.agoracomecouaviagem.controleioverland.Utils.ClickListener;
import br.com.agoracomecouaviagem.controleioverland.ViewsMenu.MenuDadosIcones;

public class DiarioRecyclerView extends AppCompatActivity {

    private SearchView searchDiario;
    private RecyclerView recyclerView;
    private Switch switchFavoritos;
    private List<Diario> lstDiario = new ArrayList<>();
    private List<Diario> lstDiarioFiltrado = new ArrayList<>();
    private List<Diario> lstDiarioSearch = new ArrayList<>();
    private ReadDAO read ;

    private Integer idViagemAtiva;
    private FloatingActionButton btnAdd;
    private Button btnVoltar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.read_diario_recycle);

        read = new ReadDAO(this);
        btnAdd = findViewById(R.id.addButton);
        btnVoltar = findViewById(R.id.btnVoltar);
        switchFavoritos = findViewById(R.id.switchFavoritos);
        searchDiario = findViewById(R.id.searchDiario);

        searchDiario.setIconified(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            searchDiario.getDefaultFocusHighlightEnabled();
            searchDiario.clearFocus();
            searchDiario.setQueryHint("Procure por local ou comentário...");
        }

        //Typeface typeface = ResourcesCompat.getFont(this, R.font.anton);
       // switchFavoritos.setTypeface(typeface);

        Bundle bundle = getIntent().getExtras();
        idViagemAtiva = bundle.getInt("idViagemAtiva");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiarioRecyclerView.this, InsertDiarioView.class);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                startActivity(intent);

            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiarioRecyclerView.this, MenuDadosIcones.class);
                intent.putExtra("idViagemAtiva", idViagemAtiva);
                startActivity(intent);
                finish();
            }
        });





        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.rcrView);

        lstDiario = read.readDiarioByIdViagem(idViagemAtiva);
        lstDiarioFiltrado = read.readDiarioFavoritoByIdViagem(idViagemAtiva);


        // Configurar adapter
        AdapterDiario adapter = new AdapterDiario(lstDiario);
        recyclerView.setAdapter(adapter);

        switchFavoritos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    AdapterDiario adapter = new AdapterDiario(lstDiario);
                    recyclerView.setAdapter(adapter);
                }else {
                    AdapterDiario adapter = new AdapterDiario(lstDiarioFiltrado);
                    recyclerView.setAdapter(adapter);
                }
            }
        });


        searchDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentFocus();
            }
        });

        searchDiario.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    AdapterDiario adapter = new AdapterDiario(lstDiario);
                    recyclerView.setAdapter(adapter);
                }else {
                    diarioFiltrado(newText);
                }

                return false;
            }
        });


        // Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(new ClickListener(getApplicationContext(),recyclerView,new ClickListener.OnItemClickListener() {
                      @Override
                      public void onItemClick(View view, int position) {
                          Diario diarioSelecionado =   lstDiario.get(position);

                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                              Intent intent = new Intent(getApplicationContext(), EditDiarioView.class);
                              intent.putExtra("idDiarioEditar", diarioSelecionado.getId());
                              intent.putExtra("idViagemAtiva", idViagemAtiva);
                              startActivity(intent);

                          }

                      }

                      @Override
                      public void onLongItemClick(View view, int position) {
                          Diario diario = lstDiario.get(position);

                          // Construa a mensagem para exibir na caixa de diálogo
                          StringBuilder message = new StringBuilder();
                          message.append("Data: ").append(AdapterAbastecimento.formatDate(diario.getData())).append("\n");
                          message.append("Local: ").append(diario.getLocal()).append("\n");
                          message.append("Comentário: ").append(diario.getComentario()).append("\n");

                          // Adicione outras informações do item conforme necessário

                          // Mostrar uma caixa de diálogo com as informações do item
                          AlertDialog.Builder builder = new AlertDialog.Builder(DiarioRecyclerView.this);
                          builder.setTitle("Exibição do Diário");
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

                Intent intent = new Intent(DiarioRecyclerView.this, MenuDadosIcones.class);
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


    public void diarioFiltrado(String textoBusca) {
        List<Diario> lstFilter = new ArrayList<>();
        String lowerCaseQuery = textoBusca.toLowerCase();

        for (Diario d : lstDiario) {
            String localLowerCase = d.getLocal().toLowerCase();
            String comentarioLowerCase = d.getComentario().toLowerCase();

            // Corrigido para verificar a presença do texto de busca no local ou no comentário
            if (localLowerCase.contains(lowerCaseQuery) || comentarioLowerCase.contains(lowerCaseQuery)) {
                lstFilter.add(d);
            }
        }

        AdapterDiario adapter = new AdapterDiario(lstFilter);
        recyclerView.setAdapter(adapter);
    }


}

