package br.com.agoracomecouaviagem.controleioverland.ViewsMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.agoracomecouaviagem.controleioverland.DAO.ReadDAO;
import br.com.agoracomecouaviagem.controleioverland.Insert.insertViagemView;
import br.com.agoracomecouaviagem.controleioverland.R;
import br.com.agoracomecouaviagem.controleioverland.RecycleViews.adapter.AdapterViagem;
import br.com.agoracomecouaviagem.controleioverland.Entities.Viagem;
import br.com.agoracomecouaviagem.controleioverland.Utils.ClickListener;

public class MenuDeViagens extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnAdicionarViagem;
    private List<Viagem> lstViagem = new ArrayList<>();
    private ReadDAO read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_de_viagens);

        read = new ReadDAO(this);
        lstViagem = read.readViagem();
        btnAdicionarViagem = findViewById(R.id.btnAdicionarViagem);


        if(lstViagem.isEmpty()){
            Intent intent = new Intent(getApplicationContext(), MenuPrimeiroAcesso.class);
            startActivity(intent);
        }



        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.rcrView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        AdapterViagem adapterViagem = new AdapterViagem(this,lstViagem);
        recyclerView.setAdapter(adapterViagem);

        recyclerView.addOnItemTouchListener(new ClickListener(this, recyclerView, new ClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onItemClick(View view, int position) {

                Viagem viagemSelecionada = lstViagem.get(position);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Intent intent = new Intent(getApplicationContext(),MenuDadosIcones.class);
                    intent.putExtra("idViagemAtiva", viagemSelecionada.getId());
                    intent.putExtra("nomeViagemAtiva", viagemSelecionada.getNome());
                    intent.putExtra("fragment_id",1);
                    startActivity(intent);
                }

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


        btnAdicionarViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    intent = new Intent(MenuDeViagens.this, insertViagemView.class);
                }
                startActivity(intent);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
